package com.truestore.backend.money;

import com.truestore.backend.AbstractControllerTest;
import com.truestore.backend.money.dto.CreateMoneyDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MoneyControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MoneyController.REST_URL + '/';
    @MockBean
    private MoneyService moneyService;
    @Autowired
    private WebApplicationContext context;
    @Mock
    private SecurityUser principal;

    public static final User USER_1 = new User(String.valueOf(UUID.randomUUID()), "admin@gmail.com", "password", "ROLE_USER");
    public static final Money MONEY_REPLENISHMENT = new Money(String.valueOf(UUID.randomUUID()), USER_1, 345F, LocalDateTime.now(), TypeTransition.REPLENISHMENT);
    public static final Money MONEY_WITHDRAWAL = new Money(String.valueOf(UUID.randomUUID()), USER_1, 200F, LocalDateTime.now(), TypeTransition.WITHDRAWAL);
    public static final CreateMoneyDto CREATE_MONEY_REPLENISHMENT_DTO = new CreateMoneyDto(UUID.randomUUID(), 345F);
    public static final CreateMoneyDto CREATE_MONEY_WITHDRAWAL_DTO = new CreateMoneyDto(UUID.randomUUID(), 200F);

    @BeforeEach
    public void beforeEach() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void createReplenishment() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(moneyService.replenishmentBalanceForUser(any(CreateMoneyDto.class), any(User.class))).thenReturn(MONEY_REPLENISHMENT);
        perform(post(REST_URL + "replenishment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CREATE_MONEY_REPLENISHMENT_DTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.amount").value(MONEY_REPLENISHMENT.getAmount()));

    }

    @Test
    void createWithdrawal() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(moneyService.withdrawalBalanceForUser(any(CreateMoneyDto.class), any(User.class))).thenReturn(MONEY_WITHDRAWAL);
        perform(post(REST_URL + "withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CREATE_MONEY_WITHDRAWAL_DTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.amount").value(MONEY_WITHDRAWAL.getAmount()));
    }

    @Test
    void getMoneyTransitionsForCurrentUser() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(moneyService.getMoneyTransitionsForUser(any(User.class))).thenReturn(Collections.singletonList(MONEY_REPLENISHMENT));
        perform(get(REST_URL + "my"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void getBalanceForCurrentUser() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(moneyService.getBalanceByUser(any(User.class))).thenReturn(400F);
        perform(get(REST_URL + "balance"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("400.0"));
    }
}
