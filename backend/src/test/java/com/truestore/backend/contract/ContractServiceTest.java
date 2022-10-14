package com.truestore.backend.contract;

import com.truestore.backend.app.AppRepository;
import com.truestore.backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.truestore.backend.app.AppTestData.APP_1;
import static com.truestore.backend.contract.ContractTestData.CONTRACT_1;
import static com.truestore.backend.contract.ContractTestData.CONTRACT_UUID;
import static com.truestore.backend.user.UserTestData.USER_1;
import static com.truestore.backend.user.UserTestData.USER_2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {
    @InjectMocks
    private ContractService contractService;
    @Mock
    private ContractRepository contractRepository;
    @Mock
    private AppRepository appRepository;

    @Test
    void createContractForUser() {
        when(appRepository.getAppById(Mockito.anyString())).thenReturn(Optional.of(APP_1));
        when(contractRepository.saveContract(any(Contract.class))).thenReturn(Optional.of(CONTRACT_1));
        Contract contract = contractService.createContractForUser(UUID.randomUUID(), USER_2);
        assertEquals(APP_1.getId(), contract.getApp().getId());
    }

    @Test
    void getContractsForUser() {
        when(contractRepository.getContractsForUser(any(User.class))).thenReturn(Collections.singletonList(CONTRACT_1));
        List<Contract> contracts = contractService.getContractsForUser(USER_1);
        assertEquals(CONTRACT_1.getQa(), contracts.get(0).getQa());
    }

    @Test
    void getContractById() {
        when(contractRepository.getContractById(Mockito.anyString())).thenReturn(Optional.of(CONTRACT_1));
        Contract contract = contractService.getContractById(UUID.fromString(CONTRACT_UUID), USER_1);
        assertEquals(contract.getQa().getId(), USER_1.getId());
    }
}