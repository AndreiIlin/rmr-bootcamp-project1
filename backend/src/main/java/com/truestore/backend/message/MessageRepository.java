package com.truestore.backend.message;

import java.util.Optional;

public interface MessageRepository {

    Optional<Message> saveMessage(Message message);

}
