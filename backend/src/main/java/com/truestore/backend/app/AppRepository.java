package com.truestore.backend.app;

import com.truestore.backend.user.User;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppRepository {

    Optional<App> getAppById(UUID appId);

    Optional<App> getAppByIdAndUserId(String appId, String userId);

    List<App> getAllAppUsingFilters(String filter, PageRequest page);

    Optional<App> saveAppForUser(App app, User user);

    Optional<App> saveApp(App app);

    Optional<App> deleteAppById(UUID appId);

    List<App> getAllAppByUserIdAnfUsingFilters(String userId, String filter, PageRequest page);

    List<App> getAppsByIds(List<String> appIds);
}
