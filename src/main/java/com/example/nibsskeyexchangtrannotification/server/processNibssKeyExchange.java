package com.example.nibsskeyexchangtrannotification.server;


import com.example.nibsskeyexchangtrannotification.utils.Nibss.factory.NibssRequestsFactory;
import com.example.nibsskeyexchangtrannotification.utils.Nibss.models.store.OfflineCTMK;
import com.example.nibsskeyexchangtrannotification.models.DataStore;
import com.example.nibsskeyexchangtrannotification.models.Terminals;
import com.example.nibsskeyexchangtrannotification.models.ThamesStoreKeys;
import com.example.nibsskeyexchangtrannotification.models.host;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.model.keys;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.model.terminalKeyManagement;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.repository.terminalKeysRepo;
import com.example.nibsskeyexchangtrannotification.repository.InstitutionRepository;
import com.example.nibsskeyexchangtrannotification.repository.TerminalRepository;
import com.example.nibsskeyexchangtrannotification.repository.keygenRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



@PropertySource("classpath:application.properties")
public class processNibssKeyExchange {
    private static Logger logger = LoggerFactory.getLogger(processNibssKeyExchange.class);
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    terminalKeysRepo terminalKeysRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    InstitutionRepository institutionRepository;


    @Autowired
    keygenRepository keygenRepository;



    // Constructor
    public processNibssKeyExchange(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;

    }



    public terminalKeyManagement keyManagement(Terminals terminals) {
        host host = new host();
        if (Objects.nonNull(terminals)) {
            host.setHostIp(terminals.getProfile().getProfileIP());
            host.setHostPort(terminals.getProfile().getPort());
        }
        DataStore dataStore1 = new DataStore() {
            @Override
            public void putString(String p0, String p1) {

            }

            @Override
            public void putInt(String p0, int p1) {

            }

            @Override
            public String getString(String p0) {
                return null;
            }

            @Override
            public int getInt(String p0) {
                return 0;
            }
        };
        dataStore1.putString(ThamesStoreKeys.THAMES_STRING_CONFIG_COMMUNICATION_HOST_ID, host.getHostIp());
        dataStore1.putString(ThamesStoreKeys.THAMES_STRING_CONFIG_COMMUNICATION_PORT_DETAILS, String.valueOf(host.getHostPort()));

        NibssRequestsFactory factory = new NibssRequestsFactory(dataStore1, terminals.getTerminalID(), terminalKeysRepo);
        terminalKeyManagement terminalKeys = new terminalKeyManagement();
        OfflineCTMK offlineCTMK = new OfflineCTMK();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        //switch based on profile name
        if (!terminals.getProfile().getProfileName().equals("ISW")){
            String zmk = terminals.getProfile().getZpk();
            try {
                keys keys = getKeys_Params(factory,host,zmk);
                terminalKeys.setId(terminals.getId());
                terminalKeys.setTerminalID(terminals.getTerminalID());
                terminalKeys.setMasterKey(keys.getMasterkey());
                terminalKeys.setSessionKey(keys.getSessionKey());
                terminalKeys.setPinKey(keys.getPinKey());
                terminalKeys.setParameterDownloaded(keys.getParameters());
                terminalKeys.setLastExchangeDateTime(date);
                return terminalKeys;
            } catch (Exception ex) {
                logger.info("Failed to fetch all keys ", ex);
            }
        }else {
            terminalKeys.setId(terminals.getId());
            terminalKeys.setTerminalID(terminals.getTerminalID());
            terminalKeys.setMasterKey("27869791275814834349579874090163");
            terminalKeys.setSessionKey("Static");
            terminalKeys.setPinKey("28300518865986737073478883921518");
            terminalKeys.setParameterDownloaded("Static");
            terminalKeys.setLastExchangeDateTime(date);
            return terminalKeys;
        }

        return terminalKeys;

    }

    private keys getKeys_Params(NibssRequestsFactory factory, host host, String zmk) {
        keys keys = new keys();
        String masterKey;
        String sessionKey;
        String pinKey;
        String parameters;

        masterKey = factory.getMasterKey(host, zmk);
        sessionKey = factory.getSessionKey(host);
        pinKey = factory.getPinKey(host);
        parameters = factory.getParameters(host);

        keys.setMasterkey(masterKey);
        keys.setSessionKey(sessionKey);
        keys.setPinKey(pinKey);
        keys.setParameters(parameters);
        return keys;


    }


}

