package com.mvp.rxandroid.activity.NoteBook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mvp.rxandroid.R;

import java.util.List;

/**
 * Created by elang on 16/6/8.
 */
public class NoteAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<String> lists;
    private NotePersenter persenter;

    public NoteAdapter(Context context,List<String> lists,NotePersenter persenter){
        mContext = context;
        this.lists = lists;
        this.persenter = persenter;
    }

    public void setData(List<String> lists){
        this.lists = lists;
    }

    /**
     * 加载item的布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent,false);
        return new NoteHolder(view);
    }

    /**
     * item数据绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteHolder noteHolder = (NoteHolder) holder;
        noteHolder.position = position;
        noteHolder.noteMessage_bt.setText(lists.get(position));
        Log.e("elang","bind  " + lists.get(position) + "  " + position);
    }

    @Override
    public int getItemCount() {
        if (lists == null) {
            return 0;
        }
        return lists.size();
    }

    /**
     * 缓存item
     */
    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        public View rootView;
        public Button noteMessage_bt;
        public int position;

        public NoteHolder(View itemView){
            super(itemView);
            rootView = itemView;
            noteMessage_bt = (Button)rootView.findViewById(R.id.noteMessage_bt);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final EditText editText = new EditText(mContext);
            editText.setText(lists.get(position));
            new AlertDialog.Builder(mContext)
                    .setTitle("编辑记录")
                    .setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            lists.set(position,editText.getText().toString());
                            persenter.refreshNote(lists);
                        }
                    }).create().show();
        }

        @Override
        public boolean onLongClick(View view) {
            persenter.removeNote(position,lists);
            return true;
        }
    }
}
