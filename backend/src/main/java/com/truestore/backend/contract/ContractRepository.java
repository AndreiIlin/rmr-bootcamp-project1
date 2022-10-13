package com.truestore.backend.contract;


import java.util.Optional;

public interface ContractRepository {
    Optional<Contract> getContractById(String contractId);

    Optional<Contract> saveContract(Contract contract);
}
