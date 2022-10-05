package com.truestore.backend.app;

import com.truestore.backend.user.User;

import java.util.List;
import java.util.Optional;

public interface AppRepository {

    Optional<App> get(String appId);

    Optional<App> getByIdAndUserId(String appId, String userId);

    List<App> getAll();

    App save(App app, User user);

    Optional<App> delete(String appId);

    List<App> getAllByUserId(String userId);

}
