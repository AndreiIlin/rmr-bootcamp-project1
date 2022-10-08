package com.truestore.backend.app;

import com.truestore.backend.app.dto.AppDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
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

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void getAppDto() {
        when(appRepository.getAppById(Mockito.anyString())).thenReturn(Optional.of(APP_1));
        AppDto appDto = appService.getAppDtoById(APP_UUID_1);
        assertEquals(appDto.getAppName(), APP_DTO_1.getAppName());
    }

    @Test
    void getAppDtoNotFound() {
        when(appRepository.getAppById(APP_UUID_NOT_FOUND)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> appService.getAppDtoById(APP_UUID_NOT_FOUND));
    }

    @Test
    void saveNewApp() {
        when(appRepository.saveAppForUser(any(), any())).thenReturn(Optional.of(APP_1));
        AppDto appTo = appService.saveAppForUser(APP_DTO_NEW, USER_1);
        assertEquals(appTo.getAppName(), APP_DTO_NEW.getAppName());
    }

    @Test
    void updatedApp() {
        when(appRepository.saveAppForUser(any(), any())).thenReturn(Optional.of(APP_1));
        when(appRepository.getAppById(Mockito.anyString())).thenReturn(Optional.of(APP_1));
        AppDto appTo = appService.saveAppForUser(APP_DTO_1, USER_1);
        assertEquals(appTo.getAppName(), APP_DTO_1.getAppName());
    }

    @Test
    void saveAppInvalid() {
        AppDto invalid = new AppDto(null, null,null, USER_1_UUID, 0.0, 0.0, true,"", "");
        when(appRepository.saveAppForUser(any(), any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        assertThrows(ResponseStatusException.class, () -> appService.saveAppForUser(invalid, USER_1));
    }

    @Test
    void deleteApp() {
        when(appRepository.deleteAppById(Mockito.anyString())).thenReturn(Optional.of(APP_1));
        AppDto appTo = appService.deleteAppById(APP_UUID_1);
        assertEquals(appTo.getAppName(), APP_DTO_1.getAppName());
    }


    @Test
    void deleteAppNotFound() {
        when(appRepository.deleteAppById(APP_UUID_NOT_FOUND)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> appService.deleteAppById(APP_UUID_NOT_FOUND));
    }

    @Test
    void getAllAppToWithoutFilters() {
        when(appRepository.getAllAppUsingFilters(Mockito.anyString(), any())).thenReturn(List.of(APP_1, APP_2));
        List<AppDto> appsTo = appService.getAllAppsUsingFilters(Mockito.anyString(), any());
        assertEquals(2, appsTo.size());
    }

    @Test
    void getAllAppToWithFilters() {
        when(appRepository.getAllAppUsingFilters(Mockito.anyString(), any())).thenReturn(Collections.singletonList(APP_2));
        List<AppDto> appsTo = appService.getAllAppsUsingFilters("2", PageRequest.of(0, 1));
        assertEquals(1, appsTo.size());
    }
}
