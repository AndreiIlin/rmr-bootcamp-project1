package com.truestore.backend.app;

import com.truestore.backend.AbstractControllerTest;
import com.truestore.backend.app.dto.CreateAppDto;
import com.truestore.backend.app.dto.UpdateAppDto;
import com.truestore.backend.security.SecurityUser;
import com.truestore.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.truestore.backend.app.AppTestData.*;
import static com.truestore.backend.user.UserTestData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AppController.REST_URL + '/';

    @MockBean
    private AppService appService;

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
    void getAppById() throws Exception {
        when(appService.getAppById(any(UUID.class))).thenReturn(APP_1);
        perform(get(REST_URL + APP_UUID_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.featurePrice").value(100.0));
    }

    @Test
    void getAppByIdNotFound() throws Exception {
        when(appService.getAppById(any(UUID.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        perform(get(REST_URL + APP_UUID_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createApp() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(appService.saveAppForUser(any(App.class), any(User.class))).thenReturn(APP_NEW_WITH_ID);
        perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CREATE_APP_DTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.appName").value(APP_NEW_WITH_ID.getAppName()));
    }

    @Test
    void createAppInvalidNullAppName() throws Exception {
        INVALID_APP_DTO.setAppName(null);
        Set<ConstraintViolation<CreateAppDto>> violations = validator.validate(INVALID_APP_DTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createAppInvalidNullAppDescription() throws Exception {
        INVALID_APP_DTO.setAppDescription(null);
        Set<ConstraintViolation<CreateAppDto>> violations = validator.validate(INVALID_APP_DTO);
        assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "Ap"})
    void createAppInvalidBlankAppName() throws Exception {
        INVALID_APP_DTO.setAppName("");
        Set<ConstraintViolation<CreateAppDto>> violations = validator.validate(INVALID_APP_DTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createAppInvalidBlankAppDescription() throws Exception {
        INVALID_APP_DTO.setAppDescription("");
        Set<ConstraintViolation<CreateAppDto>> violations = validator.validate(INVALID_APP_DTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createAppDuplicate() throws Exception {
        CreateAppDto duplicate = new CreateAppDto(APP_1.getAppName(), "AppDescription", 0F, 0F, true, "icon", "link");
        when(principal.getUser()).thenReturn(USER_1);
        when(appService.saveAppForUser(any(App.class), any(User.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(duplicate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateApp() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(appService.getAppById(any(UUID.class))).thenReturn(APP_1);
        when(appService.updateAppById(any(UUID.class), any(UpdateAppDto.class))).thenReturn(APP_1);
        perform(patch(REST_URL + APP_UUID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(APP_DTO_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(APP_UUID_1));
    }

    @Test
    void deleteAppById() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(appService.getAppById(any(UUID.class))).thenReturn(APP_1);
        when(appService.deleteAppById(any(UUID.class))).thenReturn(APP_1);
        perform(delete(REST_URL + APP_UUID_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(APP_UUID_1));
    }

    @Test
    void deleteAppByIdNotFound() throws Exception {
        when(principal.getUser()).thenReturn(USER_2);
        when(appService.getAppById(any(UUID.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        perform(delete(REST_URL + APP_UUID_1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAppByIdNotMyOwn() throws Exception {
        when(principal.getUser()).thenReturn(USER_2);
        when(appService.getAppById(any(UUID.class))).thenReturn(APP_1);
        perform(delete(REST_URL + APP_UUID_1))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllMyAppsWithoutFilters() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(appService.getAllMyAppsUsingFilters(Mockito.anyString(), eq(""), any())).thenReturn(Collections.singletonList(APP_1));
        perform(get(REST_URL + "my"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllMyAppsWithFilters() throws Exception {
        when(principal.getUser()).thenReturn(USER_2);
        when(appService.getAllMyAppsUsingFilters(Mockito.anyString(), eq("1"), any())).thenReturn(List.of());
        perform(get(REST_URL + "?filter=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getAllAppsWithFilters() throws Exception {
        when(principal.getUser()).thenReturn(USER_1);
        when(appService.getAllAppsUsingFilters(eq("1"), any())).thenReturn(Collections.singletonList(APP_1));
        perform(get(REST_URL + "?filter=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllAppsWithoutFilters() throws Exception {
        when(appService.getAllAppsUsingFilters(eq(""), any())).thenReturn(List.of(APP_1, APP_2));
        perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
