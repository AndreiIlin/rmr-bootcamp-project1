package com.truestore.backend.user;

import com.truestore.backend.AbstractControllerTest;
import com.truestore.backend.security.JWTToken;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.dto.PasswordDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import static com.truestore.backend.user.UserTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class UserControllerAuthTest  extends AbstractControllerTest {
    private static final String REST_URL = UserController.REST_URL + '/';

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Mock
    private SecurityUser principal;

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
    void changeUserPassword() throws Exception {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("old_password");
        passwordDto.setNewPassword("new_password");
        JWTToken token = new JWTToken(USER_1_UUID, VALID_TOKEN);
        when(userService.checkIfValidOldPassword(any(), Mockito.anyString())).thenReturn(true);
        when(userService.changeUserPassword(any(), Mockito.anyString())).thenReturn(token);
        perform(patch(REST_URL + "me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passwordDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.access_token").value(VALID_TOKEN));
    }

     @Test
     void changeUserPasswordInvalidOldPassword() throws Exception {
         PasswordDto passwordDto = new PasswordDto();
         passwordDto.setOldPassword("old_password");
         passwordDto.setNewPassword("new_password");
         JWTToken token = new JWTToken(USER_1_UUID, VALID_TOKEN);
         when(userService.checkIfValidOldPassword(any(User.class), Mockito.anyString())).thenReturn(false);
         when(userService.changeUserPassword(any(User.class), Mockito.anyString())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
         perform(patch(REST_URL + "me")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(mapper.writeValueAsString(passwordDto)))
                 .andExpect(status().isBadRequest())
                 .andDo(print());
     }

}
