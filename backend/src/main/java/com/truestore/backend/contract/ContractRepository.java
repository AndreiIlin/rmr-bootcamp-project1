package com.truestore.backend.contract;


import com.truestore.backend.app.App;
import com.truestore.backend.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository {
    Optional<Contract> getContractById(UUID contractId);

    Optional<Contract> saveContract(Contract contract);

    List<Contract> getContractsForUser(User user);

    Optional<Contract> getContractForAppAndUser(App app, User user);

    List<Contract> getContractsForApp(App app);
}
