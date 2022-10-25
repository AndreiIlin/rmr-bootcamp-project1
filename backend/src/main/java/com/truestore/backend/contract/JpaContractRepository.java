package com.truestore.backend.contract;

import com.truestore.backend.app.App;
import com.truestore.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaContractRepository extends JpaRepository<Contract, String> {

    List<Contract> findAllByQaOrderByCreatedDesc(User qa);

    Optional<Contract> findFirstByAppAndQa(App app, User qa);

    List<Contract> findAllByAppOrderByCreatedDesc(App app);
}
