// 
// Decompiled by Procyon v0.5.36
// 

package com.example.nibsskeyexchangtrannotification.utils.Nibss.factory;

import com.example.nibsskeyexchangtrannotification.utils.Nibss.constants.Globals;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.models.transaction.*;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.processor.IsoProcessor;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.utils.DataUtil;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.utils.ParameterParser;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.utils.StringUtils;
import com.example.nibsskeyexchangtrannotification.models.DataStore;
import com.example.nibsskeyexchangtrannotification.models.host;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.repository.terminalKeysRepo;


import java.util.Date;
import java.util.Map;

public class NibssRequestsFactory
{

    terminalKeysRepo terminalKeysRepo;

    private static final String TAG;
    private static final String acquirerID = "111130";
   // static Logger logger;
    private DataStore dataStore;
    private String terminalId;
    String TerminalSessionKey = "";
    
    public NibssRequestsFactory(final DataStore dataStore, final String terminalId, final terminalKeysRepo terminalKeysRepo ) {
        this.dataStore = dataStore;
        this.terminalId = terminalId;
        this.terminalKeysRepo = terminalKeysRepo;
    }

    public String getMasterKey(final host host, String ctmkString) {
        try {
//            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(this.terminalId);
//            if (Objects.isNull(terminalKeyManagement)){
//                terminalKeyManagement = new terminalKeyManagement();
//            }
            final GetMasterKeyRequest rk = new GetMasterKeyRequest();
            rk.setCardAcceptorTerminalId(this.terminalId);
            rk.setProcessingCode("9A0000");
            rk.setDateLocalTransaction(DataUtil.dateLocalTransaction(new Date()));
            rk.setTimeLocalTransaction(DataUtil.timeLocalTransaction(new Date()));
            rk.setTransmissionDateAndTime(DataUtil.transmissionDateAndTime(new Date()));
            final int counter = this.dataStore.getInt(Globals.PREF_MASTER_KEY_STAN) + 1;
            this.dataStore.putInt(Globals.PREF_MASTER_KEY_STAN, counter);
            rk.setSystemTraceAuditNumber(DataUtil.leftZeroPad(counter));
            final String nibssIpPAddress = host.getHostIp();
            final int nibssPort = host.getHostPort();
            IsoProcessor.setConnectionParameters(nibssIpPAddress, nibssPort);
            System.out.println(rk.toString());
            final GetMasterKeyResponse rep = IsoProcessor.process(rk);
            this.dataStore.putString(Globals.PREF_TMK_ENC, rep.getEncryptedMasterKey());
//            final String ctmkString = ISOUtil.hexor(offlineCTMKManager.getComponentOne(), offlineCTMKManager.getComponentTwo());
            final byte[] ctmk = StringUtils.hexStringToByteArray(ctmkString);
            rep.decryptMasterKey(ctmk);
            //this holds the clear masterKey
            Globals.TMK = rep.getClearMasterKey();
            this.dataStore.putString(Globals.PREF_TMK, StringUtils.bytesToHex(rep.getClearMasterKey()));
//            Keys.eClearMasterKey = rep.getEncryptedMasterKey();
//            terminalKeyManagement.setTerminalID(terminalId);
//            terminalKeyManagement.setMasterKey(StringUtils.bytesToHex(rep.getClearMasterKey()));
//            terminalKeysRepo.save(terminalKeyManagement);
//            return true;
            return StringUtils.bytesToHex(rep.getClearMasterKey());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getSessionKey(final host host) {
        try {
            final GetSessionKeyRequest sk = new GetSessionKeyRequest();
            sk.setCardAcceptorTerminalId(this.terminalId);
            sk.setProcessingCode("9B0000");
            sk.setDateLocalTransaction(DataUtil.dateLocalTransaction(new Date()));
            sk.setTimeLocalTransaction(DataUtil.timeLocalTransaction(new Date()));
            sk.setTransmissionDateAndTime(DataUtil.transmissionDateAndTime(new Date()));
            final int counter = this.dataStore.getInt(Globals.PREF_SESSION_KEY_STAN) + 1;
            this.dataStore.putInt(Globals.PREF_SESSION_KEY_STAN, counter);
            sk.setSystemTraceAuditNumber(DataUtil.leftZeroPad(counter));
            final String nibssIpPAddress = host.getHostIp();
            final int nibssPort = host.getHostPort();
            IsoProcessor.setConnectionParameters(nibssIpPAddress, nibssPort);
            final GetSessionKeyResponse skResponse = IsoProcessor.process(sk);
            this.dataStore.putString(Globals.PREF_TSK_ENC, skResponse.getEncryptedSessionKey());
            skResponse.decryptSessionKey(Globals.TMK);
           // NibssRequestsFactory.logger.info(String.format("Session Key key -> %s", StringUtils.bytesToHex(skResponse.getClearSessionKey())));
            System.out.println(String.format("Session Key key -> %s", StringUtils.bytesToHex(skResponse.getClearSessionKey())));
            TerminalSessionKey = StringUtils.bytesToHex(skResponse.getClearSessionKey());
            this.dataStore.putString(Globals.PREF_TSK, StringUtils.bytesToHex(skResponse.getClearSessionKey()));
//            Keys.eClearSessionKey = skResponse.getEncryptedSessionKey();
//            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(sk.getCardAcceptorTerminalId());
//            terminalKeyManagement.setSessionKey(TerminalSessionKey);
//            terminalKeysRepo.save(terminalKeyManagement);
            return TerminalSessionKey;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getPinKey(host host) {
        try {
            final GetPinKeyRequest pk = new GetPinKeyRequest();
            pk.setCardAcceptorTerminalId(this.terminalId);
            pk.setProcessingCode("9G0000");
            pk.setDateLocalTransaction(DataUtil.dateLocalTransaction(new Date()));
            pk.setTimeLocalTransaction(DataUtil.timeLocalTransaction(new Date()));
            pk.setTransmissionDateAndTime(DataUtil.transmissionDateAndTime(new Date()));
            final int counter = this.dataStore.getInt(Globals.PREF_PIN_KEY_STAN) + 1;
            this.dataStore.putInt(Globals.PREF_PIN_KEY_STAN, counter);
            pk.setSystemTraceAuditNumber(DataUtil.leftZeroPad(counter));
            final String nibssIpPAddress = host.getHostIp();
            final int nibssPort = host.getHostPort();
            IsoProcessor.setConnectionParameters(nibssIpPAddress, nibssPort);
            final GetPinKeyResponse pkResponse = IsoProcessor.process(pk);
            this.dataStore.putString(Globals.PREF_TPK_ENC, pkResponse.getEncryptedPinKey());
            pkResponse.descryptPinKey(Globals.TMK);
            System.out.println(String.format("Pin key -> %s", StringUtils.bytesToHex(pkResponse.getClearPinKey())));
            this.dataStore.putString(Globals.PREF_TPK, StringUtils.bytesToHex(pkResponse.getClearPinKey()));
//            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(pk.getCardAcceptorTerminalId());
//            terminalKeyManagement.setPinKey(StringUtils.bytesToHex(pkResponse.getClearPinKey()));
//            terminalKeysRepo.save(terminalKeyManagement);
//            Keys.eClearPinKey = pkResponse.getEncryptedPinKey();
//            System.out.println("Jayrush Clear PinKey "+Keys.eClearPinKey);
            return StringUtils.bytesToHex(pkResponse.getClearPinKey());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getParameters(host host) {
        try {
            final GetParameterRequest parameterRequest = new GetParameterRequest();
            parameterRequest.setCardAcceptorTerminalId(this.terminalId);
            parameterRequest.setProcessingCode("9C0000");
            parameterRequest.setDateLocalTransaction(DataUtil.dateLocalTransaction(new Date()));
            parameterRequest.setTimeLocalTransaction(DataUtil.timeLocalTransaction(new Date()));
            parameterRequest.setTransmissionDateAndTime(DataUtil.transmissionDateAndTime(new Date()));
            final int counter = this.dataStore.getInt(Globals.PREF_GET_PARAMETER_STAN) + 1;
            this.dataStore.putInt(Globals.PREF_GET_PARAMETER_STAN, counter);
            parameterRequest.setSystemTraceAuditNumber(DataUtil.leftZeroPad(counter));
            final String nibssIpPAddress = host.getHostIp();
            final int nibssPort = host.getHostPort();
            IsoProcessor.setConnectionParameters(nibssIpPAddress, nibssPort);

            final String tskString = TerminalSessionKey;
            final GetParameterResponse getParameterResponse = IsoProcessor.process(parameterRequest, StringUtils.hexStringToByteArray(tskString));
            if (getParameterResponse != null) {
                System.out.println("Parameters Loaded");
                final Map<String, String> decodedParameters = ParameterParser.parseParameters(getParameterResponse.getField62());
                this.dataStore.putString(Globals.PREF_CARD_ACCEPTOR_ID, decodedParameters.get("03"));
                this.dataStore.putString(Globals.PREF_CARD_ACCEPTOR_LOC, decodedParameters.get("52"));
                this.dataStore.putString(Globals.PREF_CURRENCY_CODE, decodedParameters.get("05"));
                this.dataStore.putString(Globals.PREF_MERCHANT_TYPE, decodedParameters.get("08"));
//                terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(parameterRequest.getCardAcceptorTerminalId());
//                terminalKeyManagement.setParameterDownloaded("Success");
//                terminalKeysRepo.save(terminalKeyManagement);
                return "Parameters downloaded successfully";
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
    static {
        TAG = NibssRequestsFactory.class.getSimpleName().toUpperCase();
        //NibssRequestsFactory.logger = (Logger) LoggerFactory.getLogger(NibssRequestsFactory.class.toString());
    }
}
