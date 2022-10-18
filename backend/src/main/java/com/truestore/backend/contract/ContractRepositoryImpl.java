package com.truestore.backend.contract;

import com.truestore.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<Contract> getContractsForUser(User user) {
        return jpaContractRepository.findAllByQaOrderByCreatedDesc(user);
    }
}
