package com.mvp.rxandroid.activity.contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mvp.rxandroid.R;
import com.mvp.rxandroid.app.MvpApp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuMH on 16/10/18.
 */

public class ContactPresenter {

    // ----------------得到本地联系人信息-------------------------------------
    public List<ContactInfo> getLocalContactsInfos(List<ContactInfo> contactInfos,String key) {
        if (contactInfos == null){
            contactInfos = new ArrayList<>();
        }
        ContentResolver cr = MvpApp.mvpApp.getContentResolver();
        String str[] = { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
                Phone.PHOTO_ID };
        Cursor cur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
                null, ContactsContract.PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,
//                ContactsContract.PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        if (cur != null) {
            while (cur.moveToNext()) {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.phone = cur.getString(cur
                        .getColumnIndex(Phone.NUMBER));// 得到手机号码
                contactInfo.name = cur.getString(cur
                        .getColumnIndex(Phone.DISPLAY_NAME));
                // contactsInfo.setContactsPhotoId(cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID)));
                long contactid = cur.getLong(cur
                        .getColumnIndex(Phone.CONTACT_ID));
                long photoid = cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID));
                // 如果photoid 大于0 表示联系人有头像 ，如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts
                            .openContactPhotoInputStream(cr, uri);
//                    contactsInfo.setBitmap(BitmapFactory.decodeStream(input));
                } else {
//                    contactsInfo.setBitmap(BitmapFactory.decodeResource(
//                            context.getResources(), R.drawable.ic_launcher));
                }

                Log.e("---------联系人电话--", contactInfo.phone + "   " + contactInfo.name);
                contactInfos.add(contactInfo);

            }
        }
        cur.close();
        Log.e("elang","size  " + contactInfos.size());
        return contactInfos;

    }

    public List<ContactInfo> getSIMContactsInfos(List<ContactInfo> contactInfos) {
//        TelephonyManager mTelephonyManager = (TelephonyManager) MvpApp.mvpApp
//                .getSystemService(Context.TELEPHONY_SERVICE);

        System.out.println("---------SIM--------");
        ContentResolver cr = MvpApp.mvpApp.getContentResolver();
        final String SIM_URI_ADN = "content://icc/adn";// SIM卡


        Uri uri = Uri.parse(SIM_URI_ADN);
        Cursor cursor = cr.query(uri, null, null, null, null);
        while (cursor.moveToFirst()) {
            ContactInfo SIMContactsInfo = new ContactInfo();
            SIMContactsInfo.name = cursor.getString(cursor
                    .getColumnIndex("name"));
            SIMContactsInfo.phone = cursor.getString(cursor
                            .getColumnIndex("number"));
//            SIMContactsInfo
//                    .setBitmap(BitmapFactory.decodeResource(
//                            context.getResources(),
//                            R.drawable.ic_launcher));
            contactInfos.add(SIMContactsInfo);
        }
        cursor.close();





        return contactInfos;
    }

}
