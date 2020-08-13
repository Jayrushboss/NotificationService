package com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.processor;


import com.example.nibsskeyexchangtrannotification.models.Terminals;
import com.example.nibsskeyexchangtrannotification.models.keyGen;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.model.terminalKeyManagement;
import com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.repository.terminalKeysRepo;
import com.example.nibsskeyexchangtrannotification.repository.TerminalRepository;
import com.example.nibsskeyexchangtrannotification.repository.keygenRepository;
import com.example.nibsskeyexchangtrannotification.server.processNibssKeyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author JoshuaO
 */
@Component
@EnableScheduling
public class nibssKeyExchangeService {
    final
    TerminalRepository terminalRepository;
    final
    com.example.nibsskeyexchangtrannotification.nibssKeyManagementServer.keymanagement.repository.terminalKeysRepo terminalKeysRepo;
    final
    com.example.nibsskeyexchangtrannotification.repository.keygenRepository keygenRepository;;

    @Autowired
    public nibssKeyExchangeService(TerminalRepository terminalRepository, terminalKeysRepo terminalKeysRepo, keygenRepository keygenRepository) {
        this.terminalRepository = terminalRepository;
        this.terminalKeysRepo = terminalKeysRepo;
        this.keygenRepository = keygenRepository;
    }

    @Scheduled(cron = "0 1 1 * * *")
//    @Scheduled(fixedDelay = 1000)
    public void start(){
        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        processNibssKeyExchange clientHandler = new processNibssKeyExchange(s, dis,dos);
        List<Terminals> terminalsList = terminalRepository.findByIsNibssKeyMgt(true);
        for (int i = 0; i<terminalsList.size(); i++)
        {
            terminalKeyManagement key = clientHandler.keyManagement(terminalsList.get(i));
            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(key.getTerminalID());
            if (Objects.nonNull(terminalKeyManagement)){
                terminalKeyManagement.setParameterDownloaded(key.getParameterDownloaded());
                terminalKeyManagement.setMasterKey(key.getMasterKey());
                terminalKeyManagement.setSessionKey(key.getSessionKey());
                terminalKeyManagement.setPinKey(key.getPinKey());
                terminalKeyManagement.setLastExchangeDateTime(key.getLastExchangeDateTime());
                terminalKeysRepo.save(terminalKeyManagement);
            }else {
                terminalKeysRepo.save(key);
            }
        }

        processFailedParameterDownload(clientHandler);
        processFailedMasterKey(clientHandler);
        processFailedSessionKey(clientHandler);
        processFailedPinkey(clientHandler);
    }

    private void processFailedParameterDownload(processNibssKeyExchange clientHandler) {
        List<terminalKeyManagement> failedterminals = terminalKeysRepo.findnullparameters();
        List<Terminals> terminals = new ArrayList<>();
        for (int a = 0; a<failedterminals.size();a++){
            terminals.add(a,terminalRepository.findByTerminalID(failedterminals.get(a).getTerminalID()));
        }
        for (int i = 0; i<terminals.size(); i++)
        {
            terminalKeyManagement key = clientHandler.keyManagement(terminals.get(i));
            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(key.getTerminalID());
            if (Objects.nonNull(terminalKeyManagement)){
                terminalKeyManagement.setParameterDownloaded(key.getParameterDownloaded());
                terminalKeyManagement.setMasterKey(key.getMasterKey());
                terminalKeyManagement.setSessionKey(key.getSessionKey());
                terminalKeyManagement.setPinKey(key.getPinKey());
                terminalKeyManagement.setLastExchangeDateTime(key.getLastExchangeDateTime());
                terminalKeysRepo.save(terminalKeyManagement);
            }else {
                terminalKeysRepo.save(key);
            }
        }
    }
    private void processFailedMasterKey(processNibssKeyExchange clientHandler) {
        List<terminalKeyManagement> failedterminals = terminalKeysRepo.findnullmasterkey();
        List<Terminals> terminals = new ArrayList<>();
        for (int a = 0; a<failedterminals.size();a++){
            terminals.add(a,terminalRepository.findByTerminalID(failedterminals.get(a).getTerminalID()));
        }
        for (int i = 0; i<terminals.size(); i++)
        {
            terminalKeyManagement key = clientHandler.keyManagement(terminals.get(i));
            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(key.getTerminalID());
            if (Objects.nonNull(terminalKeyManagement)){
                terminalKeyManagement.setParameterDownloaded(key.getParameterDownloaded());
                terminalKeyManagement.setMasterKey(key.getMasterKey());
                terminalKeyManagement.setSessionKey(key.getSessionKey());
                terminalKeyManagement.setPinKey(key.getPinKey());
                terminalKeyManagement.setLastExchangeDateTime(key.getLastExchangeDateTime());
                terminalKeysRepo.save(terminalKeyManagement);
            }else {
                terminalKeysRepo.save(key);
            }
        }
    }
    private void processFailedSessionKey(processNibssKeyExchange clientHandler) {
        List<terminalKeyManagement> failedterminals = terminalKeysRepo.findnullsessionkey();
        List<Terminals> terminals = new ArrayList<>();
        for (int a = 0; a<failedterminals.size();a++){
            terminals.add(a,terminalRepository.findByTerminalID(failedterminals.get(a).getTerminalID()));
        }
        for (int i = 0; i<terminals.size(); i++)
        {
            terminalKeyManagement key = clientHandler.keyManagement(terminals.get(i));
            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(key.getTerminalID());
            if (Objects.nonNull(terminalKeyManagement)){
                terminalKeyManagement.setParameterDownloaded(key.getParameterDownloaded());
                terminalKeyManagement.setMasterKey(key.getMasterKey());
                terminalKeyManagement.setSessionKey(key.getSessionKey());
                terminalKeyManagement.setPinKey(key.getPinKey());
                terminalKeyManagement.setLastExchangeDateTime(key.getLastExchangeDateTime());
                terminalKeysRepo.save(terminalKeyManagement);
            }else {
                terminalKeysRepo.save(key);
            }
        }
    }
    private void processFailedPinkey(processNibssKeyExchange clientHandler) {
        List<terminalKeyManagement> failedterminals = terminalKeysRepo.findnullpinkey();
        List<Terminals> terminals = new ArrayList<>();
        for (int a = 0; a<failedterminals.size();a++){
            terminals.add(a,terminalRepository.findByTerminalID(failedterminals.get(a).getTerminalID()));
        }
        for (int i = 0; i<terminals.size(); i++)
        {
            terminalKeyManagement key = clientHandler.keyManagement(terminals.get(i));
            terminalKeyManagement terminalKeyManagement = terminalKeysRepo.findByTerminalID(key.getTerminalID());
            if (Objects.nonNull(terminalKeyManagement)){
                terminalKeyManagement.setParameterDownloaded(key.getParameterDownloaded());
                terminalKeyManagement.setMasterKey(key.getMasterKey());
                terminalKeyManagement.setSessionKey(key.getSessionKey());
                terminalKeyManagement.setPinKey(key.getPinKey());
                terminalKeyManagement.setLastExchangeDateTime(key.getLastExchangeDateTime());
                terminalKeysRepo.save(terminalKeyManagement);
            }else {
                terminalKeysRepo.save(key);
            }
        }
    }



/*
//    @Scheduled(fixedDelay = 1000)
    public void populatekeygenTable(){
        List<Terminals> terminalsList = terminalRepository.findAll();

        for (int i = 0;i<terminalsList.size();i++){
            if (terminalsList.get(i).getIsNibssKeyMgt().equals(true)){
                keyGen keyGen = keygenRepository.findByTerminalID(terminalsList.get(i).getTerminalID());
                if (Objects.isNull(keyGen))
                {
                    keyGen keyGen1 = new keyGen();
                    keyGen1.setMasterkey("27869791275814834349579874090163");
                    keyGen1.setPinkey("28300518865986737073478883921518");
                    keyGen1.setSessionkey("27869791275814834349579874090163");
                    keyGen1.setEncrypted_masterkey("");
                    keyGen1.setEncrypted_sessionkey("");
                    keyGen1.setEncrypted_pinkey("9621cb5570986ada76d390275356b69c");
                    keyGen1.setTerminalID(terminalsList.get(i).getTerminalID());
                    keyGen1.setParameters("Static Parameters");
                    keyGen1.setInstitution(terminalsList.get(i).getInstitution());
                    keygenRepository.save(keyGen1);
                }
            }
        }
    }*/
}
