package com.truestore.backend.contract;

import com.truestore.backend.app.App;
import com.truestore.backend.app.AppRepository;
import com.truestore.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final AppRepository appRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository, AppRepository appRepository) {
        this.contractRepository = contractRepository;
        this.appRepository = appRepository;
    }

    public Contract createContractForUser(UUID appId, User user) {
        App app = appRepository.getAppById(String.valueOf(appId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find app")
        );
        if (app.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "App owner can't contract an own app");
        }
        Contract contract = new Contract();
        contract.setApp(app);
        contract.setQa(user);
        return contractRepository.saveContract(contract).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save contract")
        );
    }

    public List<Contract> getContractsForUser(User user) {
        return contractRepository.getContractsForUser(user);
    }

    public Contract getContractById(UUID contractId, User user) {
        Contract contract = contractRepository.getContractById(String.valueOf(contractId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find contract with such UUID")
        );
        if (!contract.getQa().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User can see only own contracts");
        }
        return contract;
    }
}
