package com.truestore.backend.contract;

import com.truestore.backend.app.App;
import com.truestore.backend.app.AppRepository;
import com.truestore.backend.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final AppRepository appRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContractService(ContractRepository contractRepository, AppRepository appRepository, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.appRepository = appRepository;
        this.modelMapper = modelMapper;
    }

    public Contract createContractForUser(String appId, User user) {
        App app = appRepository.getAppById(appId).orElseThrow(
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
}
