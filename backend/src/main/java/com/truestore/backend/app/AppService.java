package com.truestore.backend.app;

import com.truestore.backend.user.User;
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
    public App create(App app, User user) {
        Assert.notNull(app, "App must not be null");
        return appRepository.save(app, user);
    }

    public List<App> getAll() {
        return appRepository.getAll();
    }

    public App get(String appId) {
        return appRepository.get(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }

    @Transactional
    public App delete(String appId) {
        return appRepository.delete(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }

    @Transactional
    public App update(App app, User user) {
        return appRepository.save(app, user);
    }
}
