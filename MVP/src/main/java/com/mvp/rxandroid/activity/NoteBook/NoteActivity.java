package com.mvp.rxandroid.activity.NoteBook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.agera.Merger;
import com.google.android.agera.Observable;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;
import com.mvp.rxandroid.R;
import com.mvp.rxandroid.activity.BaseActivity;
import com.mvp.rxandroid.manager.ActivitysManager;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.agera.Reservoirs.reservoir;

/**
 * Created by elang on 16/6/6.
 */
public class NoteActivity extends BaseActivity implements Updatable {
    private Context mContext;
    private Repository<String> repository;
    private Observable testRepository;
    private Receiver<Object> receiver = reservoir();
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<String> notes;

    private NoteObservable observable;
    private NotePersenter persenter;
    private Updatable updatable = new Updatable() {
        @Override
        public void update() {
            Log.e("elang","   updatable");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mContext = this;
        observable = new NoteObservable();
        persenter = new NotePersenter(observable);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        observable.addUpdatable(this);
//        observable.addUpdatable(updatable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        observable.removeUpdatable(this);
    }

    @Override
    protected void initView() {
        super.initView();
        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(mContext,notes,persenter);
        recyclerView = (RecyclerView)findViewById(R.id.note_rv);
//        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
        repository = Repositories.repositoryWithInitialValue("测试数据")
                .observe()
                .onUpdatesPerLoop()
                .goLazy()
                .thenGetFrom(strSupplier)
//                .thenSkip()
                .onDeactivation(0)
                .compile();
    }

    private Supplier<String> strSupplier = new Supplier<String>() {
        @NonNull
        @Override
        public String get() {
            //主线程会堵塞
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "value";
        }
    };

    /**
     * 监听到事件后刷新
     */
    @Override
    public void update() {
//        Log.e("elang", repository.get());
        Log.e("elang", "ntoeOb  " + observable.getMessage().obj.toString());
        Message msg = observable.getMessage();
        if (msg.what == NotePersenter.ADD_NOTE){
            notes = (List<String>) msg.obj;
            noteAdapter.setData(notes);
            noteAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(notes.size() -1);
        }
        if (msg.what == NotePersenter.REMOVE_NOTE){
            notes = (List<String>) msg.obj;
            noteAdapter.setData(notes);
            noteAdapter.notifyDataSetChanged();
            ActivitysManager.getAppManager().finishAllActivity();
        }

    }

    /**
     * 添加按钮点击事件
     * @param view
     */
    public void addNote(View view) {
        final EditText editText = new EditText(mContext);
//        repository.addUpdatable(this);
        new AlertDialog.Builder(mContext)
                .setTitle("编辑记录")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        persenter.addNote(editText.getText().toString(),notes);
                    }
                }).create().show();
    }

    /**
     * 清除按钮点击事件
     * @param view
     */
    public void clearNote(View view) {
        persenter.timerRemove(notes);
    }
}
