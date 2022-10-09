package com.truestore.backend.app;

import com.truestore.backend.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppService {

    private final AppRepository appRepository;

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Transactional
    public App saveAppForUser(App app, User user) {
        Assert.notNull(app, "App must not be null");
        if (app.getId() != null) {
            getAppById(app.getId());
        }
        return appRepository.saveAppForUser(app, user).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "App not saved")
        );
    }

    public List<App> getAllAppsUsingFilters(String filter, PageRequest page) {
        return appRepository.getAllAppUsingFilters(filter, page);
    }

    public List<App> getAllMyAppsUsingFilters(String userId, String filter, PageRequest page) {
        return appRepository.getAllAppByUserIdAnfUsingFilters(userId, filter, page);
    }

    @Transactional
    public App deleteAppById(String appId) {
        return appRepository.deleteAppById(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }

    public App getAppById(String id) {
        return appRepository.getAppById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }
}
