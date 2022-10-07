package com.truestore.backend.app;

import com.truestore.backend.user.User;
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

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Transactional
    public AppTo save(App app, User user) {
        Assert.notNull(app, "App must not be null");
        App saved = appRepository.save(app, user).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "App not saved")
        );
        return new AppTo(saved);
    }

    public List<AppTo> getAll(String filter, PageRequest page) {
        return appRepository.getAll(filter, page).stream()
                .map(AppTo::new)
                .collect(Collectors.toList());
    }

    public List<AppTo> getAllMy(String userId, String filter, PageRequest page) {
        return appRepository.getAllByUserId(userId, filter, page).stream()
                .map(AppTo::new)
                .collect(Collectors.toList());
    }

    public AppTo getAppTo(String id) {
        return new AppTo(get(id));
    }

    @Transactional
    public AppTo delete(String appId) {
        App deleted = appRepository.delete(appId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
        return new AppTo(deleted);
    }

    private App get(String id) {
        return appRepository.get(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find App")
        );
    }
}
