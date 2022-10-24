package com.truestore.backend.money;

import com.truestore.backend.money.dto.CreateMoneyDto;
import com.truestore.backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyServiceTest {
    @InjectMocks
    private MoneyService moneyService;
    @Mock
    private MoneyRepository moneyRepository;

    public static final UUID USER_UUID = UUID.randomUUID();
    public static final User USER_1 = new User(String.valueOf(USER_UUID), "admin@gmail.com", "password", "ROLE_USER");
    public static final Money MONEY_REPLENISHMENT = new Money(String.valueOf(UUID.randomUUID()), USER_1, 345F, LocalDateTime.now(), TypeTransition.REPLENISHMENT);
    public static final Money MONEY_WITHDRAWAL = new Money(String.valueOf(UUID.randomUUID()), USER_1, 200F, LocalDateTime.now(), TypeTransition.WITHDRAWAL);
    public static final CreateMoneyDto CREATE_MONEY_REPLENISHMENT_DTO = new CreateMoneyDto(USER_UUID, 345F);
    public static final CreateMoneyDto CREATE_MONEY_WITHDRAWAL_DTO = new CreateMoneyDto(USER_UUID, 200F);

    @Test
    void getMoneyTransitionsForUser() {
        when(moneyRepository.getMoneyTransitionsForUser(any(User.class))).thenReturn(Collections.singletonList(MONEY_REPLENISHMENT));
        List<Money> transitions = moneyService.getMoneyTransitionsForUser(USER_1);
        assertEquals(MONEY_REPLENISHMENT.getAmount(), transitions.get(0).getAmount());
    }

    @Test
    void getBalanceByUser() {
        when(moneyRepository.getBalanceByUser(any(User.class))).thenReturn(400F);
        Float balance = moneyService.getBalanceByUser(USER_1);
        assertEquals(400F, balance);
    }

    @Test
    void replenishmentBalanceForUser() {
        when(moneyRepository.saveMoneyTransitionForUser(any(Money.class))).thenReturn(Optional.of(MONEY_REPLENISHMENT));
        Money money = moneyService.replenishmentBalanceForUser(CREATE_MONEY_REPLENISHMENT_DTO, USER_1);
        assertEquals(String.valueOf(CREATE_MONEY_REPLENISHMENT_DTO.getUserId()), money.getUser().getId());
    }

    @Test
    void withdrawalBalanceForUser() {
        when(moneyRepository.saveMoneyTransitionForUser(any(Money.class))).thenReturn(Optional.of(MONEY_WITHDRAWAL));
        Money money = moneyService.replenishmentBalanceForUser(CREATE_MONEY_WITHDRAWAL_DTO, USER_1);
        assertEquals(String.valueOf(CREATE_MONEY_WITHDRAWAL_DTO.getUserId()), money.getUser().getId());
    }
}
