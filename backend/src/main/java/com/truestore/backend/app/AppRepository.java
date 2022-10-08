package com.truestore.backend.app;

import com.truestore.backend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface AppRepository {

    Optional<App> getAppById(String appId);

    Optional<App> getAppByIdAndUserId(String appId, String userId);

    List<App> getAllAppUsingFilters(String filter, PageRequest page);

    Optional<App> saveAppForUser(App app, User user);

    Optional<App> deleteAppById(String appId);

    Page<App> getAllAppByUserIdAnfUsingFilters(String userId, String filter, PageRequest page);

}
