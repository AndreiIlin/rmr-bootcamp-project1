package com.truestore.backend.message;

import com.truestore.backend.app.App;
import com.truestore.backend.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMessageRepository extends JpaRepository<Message, String> {

    List<Message> findAllByContractOrderByCreatedDesc(Contract contract);

    List<Message> findAllByContractAppOrderByCreatedDesc(App app);
}
