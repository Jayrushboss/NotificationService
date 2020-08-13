// 
// Decompiled by Procyon v0.5.36
// 

package com.example.nibsskeyexchangtrannotification.utils.Nibss.processor;



import com.example.nibsskeyexchangtrannotification.utils.Nibss.models.transaction.*;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.network.ChannelSocketRequestManager;
import com.example.nibsskeyexchangtrannotification.models.Globals;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.MessageFactory;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.nibsskeyexchangtrannotification.utils.Nibss.utils.DataUtil.bytesToHex;


//import com.solab.iso8583.*;

public class IsoProcessor
{
   // static Logger logger;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(IsoProcessor.class);
    private static String NIBSS_IP;
    private static int NIBSS_PORT;
    public static String CONFIG_FILE;
    
    public static void setConnectionParameters(final String ipAddress, final int portNumber) {
        IsoProcessor.NIBSS_IP = ipAddress;
        IsoProcessor.NIBSS_PORT = portNumber;
    }


    public static GetMasterKeyResponse process(final GetMasterKeyRequest request) {
        GetMasterKeyResponse response = null;
        ChannelSocketRequestManager socketRequester = null;
        try {
            final IsoMessage ismsg = new IsoMessage();
            ismsg.setType(2048);
            final IsoValue<String> field3 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getProcessingCode(), 6);
            final IsoValue<String> field4 = (IsoValue<String>)new IsoValue(IsoType.DATE10, (Object)request.getTransmissionDateAndTime(), 10);
            final IsoValue<String> field5 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getSystemTraceAuditNumber(), 6);
            final IsoValue<String> field6 = (IsoValue<String>)new IsoValue(IsoType.TIME, (Object)request.getTimeLocalTransaction(), 6);
            final IsoValue<String> field7 = (IsoValue<String>)new IsoValue(IsoType.DATE4, (Object)request.getDateLocalTransaction(), 4);
            final IsoValue<String> field8 = (IsoValue<String>)new IsoValue(IsoType.ALPHA, (Object)request.getCardAcceptorTerminalId(), 8);
            final IsoValue<String> field9 = (IsoValue<String>)new IsoValue(IsoType.LLLVAR, (Object)"0100820390018");
            ismsg.setField(3, (IsoValue)field3);
            ismsg.setField(7, (IsoValue)field4);
            ismsg.setField(11, (IsoValue)field5);
            ismsg.setField(12, (IsoValue)field6);
            ismsg.setField(13, (IsoValue)field7);
            ismsg.setField(41, (IsoValue)field8);
            ismsg.setField(62, (IsoValue)field9);
            final byte[] messagepayload = ismsg.writeData();
            socketRequester = new ChannelSocketRequestManager(IsoProcessor.NIBSS_IP, IsoProcessor.NIBSS_PORT);

            String hextoSend = bytesToHex(messagepayload);
            //todo view the hex to be sent
            //System.out.println( Hex.encodeHexString( bytes ) );
            final byte[] responseBytes = socketRequester.sendAndRecieveData(messagepayload);
            //System.out.println("Response Bytes Gotten"+responseBytes);
            final MessageFactory<IsoMessage> responseMessageFactory = (MessageFactory<IsoMessage>)new MessageFactory();
            responseMessageFactory.addMessageTemplate(ismsg);
            responseMessageFactory.setAssignDate(true);
            responseMessageFactory.setUseBinaryBitmap(false);
            responseMessageFactory.setUseBinaryMessages(false);
            responseMessageFactory.setEtx(-1);
            responseMessageFactory.setIgnoreLastMissingField(false);
            responseMessageFactory.setConfigPath(IsoProcessor.CONFIG_FILE);
            final IsoMessage responseMessage = responseMessageFactory.parseMessage(responseBytes, 0);
            if (responseMessage != null) {
                response = new GetMasterKeyResponse();
                if (responseMessage.hasField(39)) {
                    response.setField39(responseMessage.getObjectValue(39).toString());
                }
                if (responseMessage.hasField(53)) {
                    response.setEncryptedMasterKey(responseMessage.getObjectValue(53).toString());
                }
            }
            System.out.println("Get masterkey response: {}"+ (Object)response);
            logger.info("Get masterkey response: {}"+ (Object)response);
        }
        catch (IOException e) {
            response = new GetMasterKeyResponse();
            response.setField39("-1");
            System.out.println("Failed to get master key due to IO exception"+ (Throwable)e);
            logger.info("Failed to get master key due to IO exception"+ (Throwable)e);
        }
        catch (Exception e2) {
            response = new GetMasterKeyResponse();
            response.setField39("-1");
            System.out.println("Failed to get pin key"+ (Throwable)e2);
            logger.info("Failed to get pin key"+ (Throwable)e2);
        }
        finally {
            if (socketRequester != null) {
                try {
                    socketRequester.disconnect();
                }
                catch (IOException ex) {
                    System.out.println("Failed to disconnect socket ");
                    logger.info("Failed to disconnect socket ");
                }
            }
        }
        return response;
    }
    
    public static GetSessionKeyResponse process(final GetSessionKeyRequest request) {
        GetSessionKeyResponse response = null;
        ChannelSocketRequestManager socketRequester = null;
        try {
            final IsoMessage ismsg = new IsoMessage();
            ismsg.setType(2048);
            final IsoValue<String> field3 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getProcessingCode(), 6);
            final IsoValue<String> field4 = (IsoValue<String>)new IsoValue(IsoType.DATE10, (Object)request.getTransmissionDateAndTime(), 10);
            final IsoValue<String> field5 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getSystemTraceAuditNumber(), 6);
            final IsoValue<String> field6 = (IsoValue<String>)new IsoValue(IsoType.TIME, (Object)request.getTimeLocalTransaction(), 6);
            final IsoValue<String> field7 = (IsoValue<String>)new IsoValue(IsoType.DATE4, (Object)request.getDateLocalTransaction(), 4);
            final IsoValue<String> field8 = (IsoValue<String>)new IsoValue(IsoType.ALPHA, (Object)request.getCardAcceptorTerminalId(), 8);
            final IsoValue<String> field9 = (IsoValue<String>)new IsoValue(IsoType.LLLVAR, (Object)"0100820390018");
            ismsg.setField(3, (IsoValue)field3);
            ismsg.setField(7, (IsoValue)field4);
            ismsg.setField(11, (IsoValue)field5);
            ismsg.setField(12, (IsoValue)field6);
            ismsg.setField(13, (IsoValue)field7);
            ismsg.setField(41, (IsoValue)field8);
            ismsg.setField(62, (IsoValue)field9);
            final byte[] messagepayload = ismsg.writeData();
            socketRequester = new ChannelSocketRequestManager(IsoProcessor.NIBSS_IP, IsoProcessor.NIBSS_PORT);
            final byte[] responseBytes = socketRequester.sendAndRecieveData(messagepayload);
            final MessageFactory responseMessageFactory = new MessageFactory();
            responseMessageFactory.addMessageTemplate(ismsg);
            responseMessageFactory.setAssignDate(true);
            responseMessageFactory.setUseBinaryBitmap(false);
            responseMessageFactory.setUseBinaryMessages(false);
            responseMessageFactory.setEtx(-1);
            responseMessageFactory.setIgnoreLastMissingField(false);
            responseMessageFactory.setConfigPath(IsoProcessor.CONFIG_FILE);
            final IsoMessage responseMessage = (IsoMessage) responseMessageFactory.parseMessage(responseBytes, 0);
            if (responseMessage != null) {
                response = new GetSessionKeyResponse();
                if (responseMessage.hasField(53)) {
                    response.setEncryptedSessionKey(responseMessage.getObjectValue(53).toString());
                }
            }
        }
        catch (IOException e) {
            response = new GetSessionKeyResponse();
            System.out.println("Failed to get session key due to IO exception"+ (Throwable)e);
        }
        catch (Exception e2) {
            response = new GetSessionKeyResponse();
            System.out.println("Failed to get session key"+ (Throwable)e2);
        }
        finally {
            if (socketRequester != null) {
                try {
                    socketRequester.disconnect();
                }
                catch (IOException ex) {
                    System.out.println("Failed to disconnect socket" );
                }
            }
        }
        return response;
    }
    
    public static GetPinKeyResponse process(final GetPinKeyRequest request) {
        GetPinKeyResponse response = null;
        ChannelSocketRequestManager socketRequester = null;
        try {
            final IsoMessage ismsg = new IsoMessage();
            ismsg.setType(2048);
            final IsoValue<String> field3 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getProcessingCode(), 6);
            final IsoValue<String> field4 = (IsoValue<String>)new IsoValue(IsoType.DATE10, (Object)request.getTransmissionDateAndTime(), 10);
            final IsoValue<String> field5 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getSystemTraceAuditNumber(), 6);
            final IsoValue<String> field6 = (IsoValue<String>)new IsoValue(IsoType.TIME, (Object)request.getTimeLocalTransaction(), 6);
            final IsoValue<String> field7 = (IsoValue<String>)new IsoValue(IsoType.DATE4, (Object)request.getDateLocalTransaction(), 4);
            final IsoValue<String> field8 = (IsoValue<String>)new IsoValue(IsoType.ALPHA, (Object)request.getCardAcceptorTerminalId(), 8);
            final IsoValue<String> field9 = (IsoValue<String>)new IsoValue(IsoType.LLLVAR, (Object)"0100820390018");
            ismsg.setField(3, (IsoValue)field3);
            ismsg.setField(7, (IsoValue)field4);
            ismsg.setField(11, (IsoValue)field5);
            ismsg.setField(12, (IsoValue)field6);
            ismsg.setField(13, (IsoValue)field7);
            ismsg.setField(41, (IsoValue)field8);
            ismsg.setField(62, (IsoValue)field9);
            final byte[] messagepayload = ismsg.writeData();
            socketRequester = new ChannelSocketRequestManager(IsoProcessor.NIBSS_IP, IsoProcessor.NIBSS_PORT);
            final byte[] responseBytes = socketRequester.sendAndRecieveData(messagepayload);
            final MessageFactory responseMessageFactory = new MessageFactory();
            responseMessageFactory.addMessageTemplate(ismsg);
            responseMessageFactory.setAssignDate(true);
            responseMessageFactory.setUseBinaryBitmap(false);
            responseMessageFactory.setUseBinaryMessages(false);
            responseMessageFactory.setEtx(-1);
            responseMessageFactory.setIgnoreLastMissingField(false);
            responseMessageFactory.setConfigPath(IsoProcessor.CONFIG_FILE);
            final IsoMessage responseMessage = (IsoMessage) responseMessageFactory.parseMessage(responseBytes, 0);
            if (responseMessage != null) {
                response = new GetPinKeyResponse();
                if (responseMessage.hasField(53)) {
                    response.setEncryptedPinKey(responseMessage.getObjectValue(53).toString());
                }
            }
        }
        catch (IOException e) {
            response = new GetPinKeyResponse();
            System.out.println("Failed to get pin key due to IO exception"+ (Throwable)e);
        }
        catch (Exception e2) {
            response = new GetPinKeyResponse();
            System.out.println("Failed to get pin key"+ (Throwable)e2);
        }
        finally {
            if (socketRequester != null) {
                try {
                    socketRequester.disconnect();
                }
                catch (IOException ex) {
                    System.out.println("Failed to disconnect socket" );
                }
            }
        }
        return response;
    }
    
    public static GetParameterResponse process(final GetParameterRequest request, final byte[] sessionKey) {
        GetParameterResponse response = null;
        ChannelSocketRequestManager socketRequester = null;
        try {
            final IsoMessage ismsg = new IsoMessage();
            ismsg.setType(2048);
            final IsoValue<String> field3 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getProcessingCode(), 6);
            final IsoValue<String> field4 = (IsoValue<String>)new IsoValue(IsoType.DATE10, (Object)request.getTransmissionDateAndTime(), 10);
            final IsoValue<String> field5 = (IsoValue<String>)new IsoValue(IsoType.NUMERIC, (Object)request.getSystemTraceAuditNumber(), 6);
            final IsoValue<String> field6 = (IsoValue<String>)new IsoValue(IsoType.TIME, (Object)request.getTimeLocalTransaction(), 6);
            final IsoValue<String> field7 = (IsoValue<String>)new IsoValue(IsoType.DATE4, (Object)request.getDateLocalTransaction(), 4);
            final IsoValue<String> field8 = (IsoValue<String>)new IsoValue(IsoType.ALPHA, (Object)request.getCardAcceptorTerminalId(), 8);
            final IsoValue<String> field9 = (IsoValue<String>)new IsoValue(IsoType.LLLVAR, (Object)"0100820390018");
            final IsoValue<String> field10 = (IsoValue<String>)new IsoValue(IsoType.ALPHA, (Object)new String(new byte[] { 0 }), 64);
            ismsg.setField(3, (IsoValue)field3);
            ismsg.setField(7, (IsoValue)field4);
            ismsg.setField(11, (IsoValue)field5);
            ismsg.setField(12, (IsoValue)field6);
            ismsg.setField(13, (IsoValue)field7);
            ismsg.setField(41, (IsoValue)field8);
            ismsg.setField(62, (IsoValue)field9);
            ismsg.setField(64, (IsoValue)field10);
            final byte[] bites = ismsg.writeData();
            System.out.println("Get Params bytes {}"+ (Object)new String(bites));
            final int length = bites.length;
            final byte[] temp = new byte[length - 64];
            if (length >= 64) {
                System.arraycopy(bites, 0, temp, 0, length - 64);
            }
            final String hashHex = generateHash256Value(temp, sessionKey);
            ismsg.setField(64, new IsoValue(IsoType.ALPHA, (Object)hashHex, 64));
            final byte[] messagepayload = ismsg.writeData();
            socketRequester = new ChannelSocketRequestManager(IsoProcessor.NIBSS_IP, IsoProcessor.NIBSS_PORT);
            final byte[] responseBytes = socketRequester.sendAndRecieveData(messagepayload);
            System.out.println("Get params response bytes {}"+ (Object)new String(responseBytes));
            final MessageFactory<IsoMessage> responseMessageFactory = (MessageFactory<IsoMessage>)new MessageFactory();
            responseMessageFactory.addMessageTemplate(ismsg);
            responseMessageFactory.setAssignDate(true);
            responseMessageFactory.setUseBinaryBitmap(false);
            responseMessageFactory.setUseBinaryMessages(false);
            responseMessageFactory.setEtx(-1);
            responseMessageFactory.setIgnoreLastMissingField(false);
            responseMessageFactory.setConfigPath(IsoProcessor.CONFIG_FILE);
            final IsoMessage responseMessage = responseMessageFactory.parseMessage(responseBytes, 0);
            if (responseMessage != null) {
                response = new GetParameterResponse();
                if (responseMessage.hasField(39)) {
                    response.setField39(responseMessage.getObjectValue(39).toString());
                }
                if (responseMessage.hasField(62)) {
                    response.setField62(responseMessage.getObjectValue(62).toString());
                }
            }
        }
        catch (Exception e) {
            System.out.println("Failed to get master key"+ (Throwable)e);
            if (socketRequester != null) {
                try {
                    socketRequester.disconnect();
                }
                catch (IOException ex) {
                    System.out.println("Failed to disconnect socket" );
                }
            }
        }
        finally {
            if (socketRequester != null) {
                try {
                    socketRequester.disconnect();
                }
                catch (IOException ex2) {
                    System.out.println("Failed to disconnect socket"+ 2);
                }
            }
        }
        return response;
    }

    public static String generateHash256Value(final byte[] iso, final byte[] key) {
        String hashText = null;
        try {
            final MessageDigest m = MessageDigest.getInstance("SHA-256");
            m.update(key, 0, key.length);
            m.update(iso, 0, iso.length);
            hashText = bytesToHex(m.digest());
            hashText = hashText.replace(" ", "");
        }
        catch (NoSuchAlgorithmException ex) {
            System.out.println("Hashing " );
        }
        if (hashText.length() < 64) {
            final int numberOfZeroes = 64 - hashText.length();
            String zeroes = "";
            String temp = hashText.toString();
            for (int i = 0; i < numberOfZeroes; ++i) {
                zeroes += "0";
            }
            temp = zeroes + temp;
            System.out.println("Utility :: generateHash256Value :: HashValue with zeroes: {}"+ (Object)temp);
            return temp;
        }
        return hashText;
    }
    

    

    
    static {
        IsoProcessor.NIBSS_IP = null;
        IsoProcessor.NIBSS_PORT = 0;
        IsoProcessor.CONFIG_FILE = Globals.ISO_CONFIG_FILE;
    }
}
