package com.truestore.backend.contract;


import com.truestore.backend.user.User;

import java.util.List;
import java.util.Optional;

public interface ContractRepository {
    Optional<Contract> getContractById(String contractId);

    Optional<Contract> saveContract(Contract contract);

    List<Contract> getContractsForUser(User user);
}
