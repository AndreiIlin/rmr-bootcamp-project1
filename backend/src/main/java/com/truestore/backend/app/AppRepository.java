package com.truestore.backend.app;

import com.truestore.backend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface AppRepository {

    Optional<App> get(String appId);

    Optional<App> getByIdAndUserId(String appId, String userId);

    Page<App> getAll(String filter, PageRequest page);

    Optional<App> save(App app, User user);

    Optional<App> delete(String appId);

    Page<App> getAllByUserId(String userId, String filter, PageRequest page);

}
