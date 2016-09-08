package com.mvp.rxandroid.util;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;

/**
 * Created by zhuMH on 16/9/5.
 */
public class GZipData {

    /**
     * 将数据进行gzip压缩
     * @param bContent 需要压缩的数据
     * @return 返回压缩后的数据
     */
    public static byte[] unZip(byte[] bContent)
    {
        byte[] b = null;
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(bContent);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null)
            {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1)
                {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return b;
    }

    public static byte[] unGZip(byte[] bContent)
    {

        byte[] data = new byte[1024];
        try
        {
            ByteArrayInputStream in = new ByteArrayInputStream(bContent);
            GZIPInputStream pIn = new GZIPInputStream(in);
            DataInputStream objIn = new DataInputStream(pIn);

            int len = 0;
            int count = 0;
            while ((count = objIn.read(data, len, len + data.length)) != -1)
            {
                len = len + count;
            }

            byte[] trueData = new byte[len];
            System.arraycopy(data, 0, trueData, 0, len);

            objIn.close();
            pIn.close();
            in.close();

            return trueData;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数据进行gzip压缩
     * @param bContent 需要压缩的数据
     * @return 返回压缩后的数据
     */
    public static byte[] unAZip(byte[] bContent)
    {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(bContent);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 压缩数据
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] jzlib(byte[] object) {
        byte[] data = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ZOutputStream zOut = new ZOutputStream(out,
                    JZlib.Z_DEFAULT_COMPRESSION);
            DataOutputStream objOut = new DataOutputStream(zOut);
            objOut.write(object);
            objOut.flush();
            zOut.close();
            data = out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 解压被压缩的数据
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] unjzlib(byte[] object) {
        byte[] data = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(object);
            ZInputStream zIn = new ZInputStream(in);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = zIn.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            data = baos.toByteArray();
            baos.flush();
            baos.close();
            zIn.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
