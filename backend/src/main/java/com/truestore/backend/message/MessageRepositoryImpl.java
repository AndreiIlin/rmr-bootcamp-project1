package com.truestore.backend.message;

import com.truestore.backend.app.App;
import com.truestore.backend.contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<Message> getMessagesForContract(Contract contract) {
        return jpaMessageRepository.findAllByContractOrderByCreatedDesc(contract);
    }

    @Override
    public List<Message> getMessagesForApp(App app) {
        return jpaMessageRepository.findAllByContractAppOrderByCreatedDesc(app);
    }
}
