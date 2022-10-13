package com.truestore.backend.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContractRepositoryImpl implements ContractRepository {
    private final JpaContractRepository jpaContractRepository;

    @Autowired
    public ContractRepositoryImpl(JpaContractRepository jpaContractRepository) {
        this.jpaContractRepository = jpaContractRepository;
    }

    @Override
    public Optional<Contract> getContractById(String contractId) {
        return jpaContractRepository.findById(contractId);
    }

    @Override
    public Optional<Contract> saveContract(Contract contract) {
        try {
            return Optional.of(jpaContractRepository.save(contract));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
