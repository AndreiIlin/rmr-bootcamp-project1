package com.truestore.backend.contract;

import com.truestore.backend.AbstractControllerTest;
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

import javax.validation.Validator;

import java.util.Collections;
import java.util.UUID;

import static com.truestore.backend.contract.ContractTestData.*;
import static com.truestore.backend.user.UserTestData.USER_1;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContractControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ContractController.REST_URL + '/';
    @MockBean
    private ContractService contractService;
    @Autowired
    private WebApplicationContext context;
    @Mock
    private SecurityUser principal;
    @Autowired
    private Validator validator;

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
    void createContract() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(contractService.createContractForUser(any(UUID.class), any(User.class))).thenReturn(CONTRACT_1);
        perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CREATE_CONTRACT_DTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.qaId").value(CONTRACT_1.getQa().getId()));
    }

    @Test
    void getContractsForCurrentUser() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(contractService.getContractsForUser(any(User.class))).thenReturn(Collections.singletonList(CONTRACT_1));
        perform(get(REST_URL + "my"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getContractById() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(contractService.getContractById(any(UUID.class), any(User.class))).thenReturn(CONTRACT_1);
        perform(get(REST_URL + CONTRACT_UUID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(CONTRACT_UUID));
    }
}