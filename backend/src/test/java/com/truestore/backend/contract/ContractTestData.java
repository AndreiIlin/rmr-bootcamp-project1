package com.truestore.backend.contract;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.truestore.backend.app.AppTestData.APP_1;
import static com.truestore.backend.user.UserTestData.USER_1;

public class ContractTestData {
    public static final String CONTRACT_UUID = String.valueOf(UUID.randomUUID());
    public static final Contract CONTRACT_1 = new Contract(CONTRACT_UUID, APP_1, USER_1, LocalDateTime.now());
}
