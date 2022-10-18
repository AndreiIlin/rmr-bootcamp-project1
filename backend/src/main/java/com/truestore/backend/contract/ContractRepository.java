package com.truestore.backend.contract;


import com.truestore.backend.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository {
    Optional<Contract> getContractById(UUID contractId);

    Optional<Contract> saveContract(Contract contract);

    List<Contract> getContractsForUser(User user);
}
