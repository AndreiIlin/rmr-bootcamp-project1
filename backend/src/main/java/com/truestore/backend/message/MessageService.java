package com.truestore.backend.message;

import com.truestore.backend.app.App;
import com.truestore.backend.app.AppRepository;
import com.truestore.backend.contract.Contract;
import com.truestore.backend.contract.ContractRepository;
import com.truestore.backend.message.dto.CreateMessageDto;
import com.truestore.backend.user.User;
import com.truestore.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final AppRepository appRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ContractRepository contractRepository, AppRepository appRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.contractRepository = contractRepository;
        this.appRepository = appRepository;
    }

    public Message createMessageFromUserToContract(CreateMessageDto createMessageDto, User user) {
        Contract contract = contractRepository.getContractById(createMessageDto.getContractId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find contract")
        );
        String qaId = contract.getQa().getId();
        String ownerId = contract.getApp().getOwner().getId();
        if (!qaId.equals(user.getId()) && !ownerId.equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only contract participants can message each other");
        }
        Message message = new Message();
        message.setContract(contract);
        message.setAuthor(user);
        message.setText(createMessageDto.getText());
        if (qaId.equals(user.getId())) {
            message.setRecipient(contract.getApp().getOwner());
        } else {
            message.setRecipient(contract.getQa());
        }
        return messageRepository.saveMessage(message).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save message")
        );
    }

    public List<Message> getMessagesForContractByUser(UUID contractId, User user) {
        Contract contract = contractRepository.getContractById(contractId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find contract")
        );
        String qaId = contract.getQa().getId();
        String ownerId = contract.getApp().getOwner().getId();
        if (!qaId.equals(user.getId()) && !ownerId.equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only contract participants can get messages");
        }
        return messageRepository.getMessagesForContract(contract);
    }

    public List<Message> getLastMessagesForAppByOwner(UUID appId, User user) {
        App app = appRepository.getAppById(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find app")
        );
        if (!app.getOwner().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only app owner can get last messages");
        }
        return messageRepository.getMessagesForApp(app);
    }
}
