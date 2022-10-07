package com.truestore.backend.app;

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

import static com.truestore.backend.app.AppTestData.*;
import static com.truestore.backend.user.UserTestData.*;
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

    @Test
    void getAppTo() {
        when(appRepository.get(Mockito.anyString())).thenReturn(Optional.of(APP_1));
        AppTo appTo = appService.getAppTo(APP_UUID_1);
        assertEquals(appTo.getAppName(), APP_TO_1.getAppName());
    }

    @Test
    void getAppToNotFound() {
        when(appRepository.get(APP_UUID_NOT_FOUND)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> appService.getAppTo(APP_UUID_NOT_FOUND));
    }

    @Test
    void saveApp() {
        when(appRepository.save(any(), any())).thenReturn(Optional.of(APP_1));
        AppTo appTo = appService.save(APP_1, USER_1);
        assertEquals(appTo.getAppName(), APP_TO_1.getAppName());
    }


    @Test
    void saveAppInvalid() {
        App invalid = new App(null, null, USER_1, 0.0, 0.0, "", "");
        when(appRepository.save(any(), any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        assertThrows(ResponseStatusException.class, () -> appService.save(invalid, USER_1));
    }

    @Test
    void deleteApp() {
        when(appRepository.delete(Mockito.anyString())).thenReturn(Optional.of(APP_1));
        AppTo appTo = appService.delete(APP_UUID_1);
        assertEquals(appTo.getAppName(), APP_TO_1.getAppName());
    }


    @Test
    void deleteAppNotFound() {
        when(appRepository.delete(APP_UUID_NOT_FOUND)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> appService.delete(APP_UUID_NOT_FOUND));
    }

    @Test
    void getAllAppToWithoutFilters() {
        when(appRepository.getAll(Mockito.anyString(), any())).thenReturn(List.of(APP_1, APP_2));
        List<AppTo> appsTo = appService.getAll(Mockito.anyString(), any());
        assertEquals(2, appsTo.size());
    }

    @Test
    void getAllAppToWithFilters() {
        when(appRepository.getAll(Mockito.anyString(), any())).thenReturn(Collections.singletonList(APP_2));
        List<AppTo> appsTo = appService.getAll("2", PageRequest.of(0, 1));
        assertEquals(1, appsTo.size());
    }
}
