package com.mvp.rxandroid.thirdparty.alimail;

import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.mvp.rxandroid.thirdparty.alimail.MsgProbuf.Msg;
import com.mvp.rxandroid.thirdparty.alimail.MsgProbuf.Msg.MsgHead;
import com.mvp.rxandroid.util.GZipData;

/**
 * Created by zhuMH on 16/9/1.
 */
public class AlimeiSDKManager {

    private static String testJson = "{" +"\"msgbody\":\"查询成功\"," + "\"msghead\":{" +
            "\"command\":\"test\"," +
            "\"extras\":\"中文行不行\"" +
            "}" +
            "}";

    private static String bigJson = "{\n" +
            "    \"msgbody\": \"查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功" +
            "查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功\",\n" +
            "    \"msghead\": {\n" +
            "        \"command\": \"test\",\n" +
            "        \"extras\": \"中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
            "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
            "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
            "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
            "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
            "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
            "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行\"\n" +
            "    }\n" +
            "}";

    private static String chatMsg = "{\"un\":\"祝敏华\",\"mi\":\"8eb9d760-1ab4-41aa-8d36-bbb0a4169aaf\",\"ct\":0,\"msg\":\"一二三四五六七八九十去我饿人他呀取玩哦怕啊是的发个好就看了在\"}";

    public static void test() {
        Msg.MsgHead.Builder msgHeadBuilder = Msg.MsgHead.newBuilder();
        msgHeadBuilder.setCommand("test");
        msgHeadBuilder.setExtras("中文行不行");
        MsgHead msgHead = msgHeadBuilder.build();

        Msg.Builder msgBuilder = Msg.newBuilder();
        msgBuilder.setMsghead(msgHead);
        msgBuilder.setMsgbody("查询成功");
        Msg msg = msgBuilder.build();

        long jsonLength = testJson.getBytes().length;
        long msgLength = msg.toByteArray().length;
        Log.e("elang","json  " + jsonLength + "   msg  " + msgLength);
        Log.e("elang","temp  " + new String(msg.toByteArray()));
        Msg.MsgHead.Builder headBuilder = Msg.MsgHead.newBuilder();
        headBuilder.setCommand("test");
        headBuilder.setExtras("中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
                "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
                "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
                "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
                "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
                "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行" +
                "中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行中文行不行");
        MsgHead head = headBuilder.build();

        Msg.Builder builder = Msg.newBuilder();
        builder.setMsghead(head);
        builder.setMsgbody("查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功" +
                "查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功查询成功");
        Msg bigMsg = builder.build();

        long bigJsonLength = bigJson.getBytes().length;
        byte[] temp = bigMsg.toByteArray();
        long bigMsgLength = temp.length;
        Log.e("elang","big json  " + bigJsonLength + "   msg  " + bigMsgLength);
        try {
            Msg desMsg = Msg.parseFrom(temp);
            Log.e("elang","temp  " + new String(temp));
            Log.e("elang","body  " + desMsg.getMsgbody() + "   extras  " + desMsg.getMsghead().getExtras());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        ChatMsg.ChatMessage.Builder chtaBuilder = ChatMsg.ChatMessage.newBuilder();
        chtaBuilder.setCt(0);
        chtaBuilder.setMi("8eb9d760-1ab4-41aa-8d36-bbb0a4169aaf");
        chtaBuilder.setUn("祝敏华");
        chtaBuilder.setMsg("一二三四五六七八九十去我饿人他呀取玩哦怕");
        ChatMsg.ChatMessage chatMessage = chtaBuilder.build();

        log(chatMessage,chatMsg);
    }


    public static void log(MessageLite messageLite, String json){
        long bigJsonLength = json.getBytes().length;
        byte[] temp = messageLite.toByteArray();
        long bigMsgLength = temp.length;
        Log.e("elang","log json  " + bigJsonLength + "   messageLite  " + bigMsgLength);
        try {
            byte[] zip = GZipData.jzlib(temp);
            Log.e("elang","  length  " + zip.length);
            byte[] gzip = GZipData.unjzlib(zip);
            Log.e("elang","gzip  " + gzip.length + " 13601926642  gzip  3100307700061" + new String(gzip));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
