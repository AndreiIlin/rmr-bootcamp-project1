package com.truestore.backend.contract;

import com.truestore.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaContractRepository extends JpaRepository<Contract, String> {

    List<Contract> findAllByQaOrderByCreatedDesc(User qa);
}
