// 
// Decompiled by Procyon v0.5.36
// 

package com.example.nibsskeyexchangtrannotification.utils.Nibss.network;


import com.example.nibsskeyexchangtrannotification.utils.Nibss.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


@Component
public class ChannelSocketRequestManager
{
    private static Logger logger = LoggerFactory.getLogger(ChannelSocketRequestManager.class);

    private SSLSocket socket;

    public ChannelSocketRequestManager() {

    }

    public ChannelSocketRequestManager(final String endpoint, final int port) throws IOException, KeyManagementException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                final X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                return myTrustedAnchors;
            }

            public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
            }

            public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
            }
        } };
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        this.socket = (SSLSocket)sc.getSocketFactory().createSocket(endpoint, port);
    }

    public void disconnect() throws IOException {
        if (this.socket.isConnected()) {
            this.socket.close();
        }
    }

    public byte[] sendAndRecieveData(final byte[] data) throws IOException {
        if (this.socket.isConnected()) {
            final DataOutputStream os = new DataOutputStream(this.socket.getOutputStream());
            final DataInputStream is = new DataInputStream(this.socket.getInputStream());
            final short length = (short)data.length;
            final byte[] headerBytes = DataUtil.shortToBytes(length);
            final byte[] messagePayload = concat(headerBytes, data);
            os.write(messagePayload);
            os.flush();
            final byte[] lenBytes = new byte[2];
            is.readFully(lenBytes);
            final int contentLength = DataUtil.bytesToShort(lenBytes);
            final byte[] resp = new byte[contentLength];
            is.readFully(resp);
            String s = new String(resp);
            System.out.println(s);
            return resp;
        }
        throw new IOException("Socket not connected");
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }



    private String asciiResponseMessage(byte[] resp) {
        final String s = new String(resp);
        String mti = s.substring(0,4);
        switch (mti) {
            case "0810":
                logger.info("ISO Network Management ( 0810 )---> {}", s);
                return mti;
            case "0210":
                logger.info("Transaction Message ( 0210 )---> {}", s);
                return mti;
            case "0110":
                logger.info("Authorization Message ( 0110 )---> {}", s);
                return mti;
            case "0430":
                logger.info("Reversal Response Message ( 0430 )---> {}", s);
                return mti;
            default:
                logger.info("Rersponse ---->{}", s);
                return mti;
        }
    }



    public byte[] getData() throws IOException {
        final byte[] buffer = new byte[1024];
        int count = 0;
        final DataInputStream is = new DataInputStream(this.socket.getInputStream());
        final int avail = is.available();
        while (is.available() > 0) {
            buffer[count++] = is.readByte();
            if (count >= buffer.length - 1) {
                this.resize(buffer);
            }
        }
        final byte[] returnbuffer = new byte[count - 2];
        System.arraycopy(buffer, 2, returnbuffer, 0, count - 2);
        return returnbuffer;
    }

    public byte[] _getData() throws IOException {
        final byte[] buffer = new byte[1024];
        final DataInputStream is = new DataInputStream(this.socket.getInputStream());
        final int available = is.available();
        if (available > 0) {
            is.read(buffer);
        }
        return buffer;
    }

    private void resize(byte[] buffer) {
        final int m_Size = 2 * buffer.length;
        final int presentsize = buffer.length;
        final byte[] temp = buffer;
        buffer = new byte[m_Size];
        for (int i = 0; i <= presentsize - 1; ++i) {
            buffer[i] = temp[i];
        }
    }

    private static byte[] concat(final byte[] A, final byte[] B) {
        final int aLen = A.length;
        final int bLen = B.length;
        final byte[] C = new byte[aLen + bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }




}
