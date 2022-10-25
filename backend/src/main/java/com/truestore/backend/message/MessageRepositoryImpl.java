package com.truestore.backend.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
    private final JpaMessageRepository jpaMessageRepository;

    @Autowired
    public MessageRepositoryImpl(JpaMessageRepository jpaMessageRepository) {
        this.jpaMessageRepository = jpaMessageRepository;
    }

    @Override
    public Optional<Message> saveMessage(Message message) {
        try {
            jpaMessageRepository.save(message);
            return Optional.of(message);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
