package com.truestore.backend.app;

import com.truestore.backend.app.dto.UpdateAppDto;
import com.truestore.backend.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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
            getAppById(UUID.fromString(app.getId()));
        }
        return appRepository.saveAppForUser(app, user).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save app")
        );
    }

    public List<App> getAllAppsUsingFilters(String filter, PageRequest page) {
        return appRepository.getAllAppUsingFilters(filter, page);
    }

    public List<App> getAllMyAppsUsingFilters(String userId, String filter, PageRequest page) {
        return appRepository.getAllAppByUserIdAnfUsingFilters(userId, filter, page);
    }

    @Transactional
    public App deleteAppById(UUID appId) {
        return appRepository.deleteAppById(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }

    public App getAppById(UUID id) {
        return appRepository.getAppById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }

    public App updateAppById(UUID appId, UpdateAppDto updateAppDto) {
        App app = appRepository.getAppById(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find app")
        );
        if (updateAppDto.getAppName() != null) app.setAppName(updateAppDto.getAppName());
        if (updateAppDto.getAppDescription() != null) app.setAppDescription(updateAppDto.getAppDescription());
        if (updateAppDto.getFeaturePrice() != null) app.setFeaturePrice(updateAppDto.getFeaturePrice());
        if (updateAppDto.getBugPrice() != null) app.setBugPrice(updateAppDto.getBugPrice());
        if (updateAppDto.getAvailable() != null) app.setAvailable(updateAppDto.getAvailable());
        if (updateAppDto.getIconImage() != null) app.setIconImage(updateAppDto.getIconImage());
        if (updateAppDto.getDownloadLink() != null) app.setDownloadLink(updateAppDto.getDownloadLink());
        return appRepository.saveApp(app).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to update app")
        );
    }
}
