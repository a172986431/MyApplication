package com.mvp.rxandroid.http;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by liyang on 16/3/14.
 */
public class QiChatSSLContext {

    private static final String KEY_STORE_CLIENT_PATH = "rajax_https.cer";//客户端要给服务器端认证的证书

    /**
     * 获取SSLContext
     * @param context 上下文
     * @return SSLContext
     */
    public static SSLContext getSSLContext(Context context) {
        try{
            InputStream ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
           //From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(ksIn);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
//                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

           // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
