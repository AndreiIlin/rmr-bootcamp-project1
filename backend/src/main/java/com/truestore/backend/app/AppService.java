package com.truestore.backend.app;

import com.truestore.backend.app.dto.AppDto;
import com.truestore.backend.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AppService {

    private final AppRepository appRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Transactional
    public AppDto saveAppForUser(AppDto appDto, User user) {
        Assert.notNull(appDto, "App must not be null");
        App app;
        if (appDto.getId() != null) {
            getAppById(appDto.getId());
        }
        app = modelMapper.map(appDto, App.class);
        app = appRepository.saveAppForUser(app, user).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "App not saved")
        );
        return modelMapper.map(app, AppDto.class);
    }

    public List<AppDto> getAllAppsUsingFilters(String filter, PageRequest page) {
        return appRepository.getAllAppUsingFilters(filter, page).stream()
                .map(app -> modelMapper.map(app, AppDto.class))
                .collect(Collectors.toList());
    }

    public List<AppDto> getAllMyAppsUsingFilters(String userId, String filter, PageRequest page) {
        return appRepository.getAllAppByUserIdAnfUsingFilters(userId, filter, page).stream()
                .map(app -> modelMapper.map(app, AppDto.class))
                .collect(Collectors.toList());
    }

    public AppDto getAppDtoById(String id) {
        return modelMapper.map(getAppById(id), AppDto.class);
    }

    @Transactional
    public AppDto deleteAppById(String appId) {
        App deleted = appRepository.deleteAppById(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
        return modelMapper.map(deleted, AppDto.class);
    }

    private App getAppById(String id) {
        return appRepository.getAppById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }
}
