package com.truestore.backend.app;

import com.truestore.backend.contract.ContractRepository;
import com.truestore.backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.truestore.backend.app.AppTestData.*;
import static com.truestore.backend.contract.ContractTestData.CONTRACT_1;
import static com.truestore.backend.user.UserTestData.USER_1;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AppServiceTest {

    @InjectMocks
    private AppService appService;
    @Mock
    private AppRepository appRepository;
    @Mock
    private ContractRepository contractRepository;

    @Test
    void getAppById() {
        when(appRepository.getAppById(any(UUID.class))).thenReturn(Optional.of(APP_1));
        when(contractRepository.getContractForAppAndUser(any(App.class), any(User.class))).thenReturn(Optional.of(CONTRACT_1));
        App app = appService.getAppById(UUID.fromString(APP_UUID_1), USER_1);
        assertEquals(app.getId(), APP_1.getId());
    }

    @Test
    void getAppNotFound() {
        when(appRepository.getAppById(UUID.fromString(APP_UUID_NOT_FOUND))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> appService.getAppById(UUID.fromString(APP_UUID_NOT_FOUND), USER_1));
    }

    @Test
    void createNewApp() {
        when(appRepository.saveAppForUser(any(), any())).thenReturn(Optional.of(APP_NEW_WITH_ID));
        App app = appService.saveAppForUser(APP_NEW_WITHOUT_ID, USER_1);
        assertEquals(app.getId(), APP_NEW_WITH_ID.getId());
    }

    @Test
    void updatedApp() {
        when(appRepository.saveAppForUser(any(), any())).thenReturn(Optional.of(APP_1));
        when(appRepository.getAppById(any(UUID.class))).thenReturn(Optional.of(APP_1));
        App app = appService.saveAppForUser(APP_1, USER_1);
        assertEquals(app.getAppName(), APP_1.getAppName());
    }

    @Test
    void saveAppInvalid() {
        App invalid = new App(null, null, null, USER_1, 0F, 0F, true,"", "", null, null);
        when(appRepository.saveAppForUser(any(), any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        assertThrows(ResponseStatusException.class, () -> appService.saveAppForUser(invalid, USER_1));
    }

    @Test
    void deleteApp() {
        when(appRepository.deleteAppById(any(UUID.class))).thenReturn(Optional.of(APP_1));
        App app = appService.deleteAppById(UUID.fromString(APP_UUID_1));
        assertEquals(app.getAppName(), APP_1.getAppName());
    }


    @Test
    void deleteAppNotFound() {
        when(appRepository.deleteAppById(UUID.fromString(APP_UUID_NOT_FOUND))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> appService.deleteAppById(UUID.fromString(APP_UUID_NOT_FOUND)));
    }

    @Test
    void getAllAppToWithoutFilters() {
        when(appRepository.getAllAppUsingFilters(Mockito.anyString(), any())).thenReturn(List.of(APP_1, APP_2));
        List<App> appsTo = appService.getAllAppsUsingFilters(Mockito.anyString(), any());
        assertEquals(2, appsTo.size());
    }

    @Test
    void getAllAppToWithFilters() {
        when(appRepository.getAllAppUsingFilters(Mockito.anyString(), any())).thenReturn(Collections.singletonList(APP_2));
        List<App> appsTo = appService.getAllAppsUsingFilters("2", PageRequest.of(0, 1));
        assertEquals(1, appsTo.size());
    }
}
