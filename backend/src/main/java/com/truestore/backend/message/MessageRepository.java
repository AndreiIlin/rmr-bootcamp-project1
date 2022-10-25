package com.truestore.backend.message;

import com.truestore.backend.app.App;
import com.truestore.backend.contract.Contract;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Optional<Message> saveMessage(Message message);

    List<Message> getMessagesForContract(Contract contract);

    List<Message> getMessagesForApp(App app);
}
