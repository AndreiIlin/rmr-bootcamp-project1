package com.truestore.backend.app;

import com.truestore.backend.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AppRepositoryImpl implements AppRepository {

    private final JpaAppRepository jpaAppRepository;

    public AppRepositoryImpl(JpaAppRepository jpaAppRepository) {
        this.jpaAppRepository = jpaAppRepository;
    }

    @Override
    public Optional<App> saveAppForUser(App app, User user) {
        if (app.getId() != null && getAppByIdAndUserId(app.getId(), user.getId()).isEmpty()) {
            return Optional.empty();
        }
        app.setOwner(user);
        return Optional.of(jpaAppRepository.save(app));
    }

    @Override
    public Optional<App> saveApp(App app) {
        try {
            return Optional.of(jpaAppRepository.save(app));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<App> deleteAppById(UUID appId) {
        Optional<App> app = jpaAppRepository.findById(String.valueOf(appId));
        app.ifPresent(a -> jpaAppRepository.deleteById(a.getId()));
        return app;
    }

    @Override
    public Optional<App> getAppByIdAndUserId(String appId, String userId) {
        return jpaAppRepository.findById(appId)
                .filter(app -> app.getOwner().getId().equals(userId));
    }

    @Override
    public Optional<App> getAppById(UUID appId) {
        return jpaAppRepository.findById(String.valueOf(appId));
    }

    @Override
    public List<App> getAllAppUsingFilters(String filter, PageRequest page) {
        if (filter.isEmpty()) {
            return jpaAppRepository.findAll(page).toList();
        } else {
            return jpaAppRepository.findAll(specification(filter), page).toList();
        }
    }

    @Override
    public List<App> getAllAppByUserIdAnfUsingFilters(String userId, String filter, PageRequest page) {
        return jpaAppRepository.findAllByOwnerIdAndAppNameContainingIgnoreCase(userId, filter, page);
    }

    @Override
    public List<App> getAppsByIds(List<String> appIds) {
        return jpaAppRepository.findByIdIn(appIds);
    }

    private Specification<App> specification(String filter) {
        Specification<App> spec = Specification.where(null);
        if (filter.isEmpty()) {
            return spec;
        } else {
            return spec.and(AppFilterSpecs.appNameContains(filter));
        }
    }

    private static class AppFilterSpecs {
        public static Specification<App> appNameContains(String name) {
            return (Specification<App>) (root, cq, cb) -> cb.like(cb.lower(root.get("appName")), "%" + name.toLowerCase() + "%");
        }
    }
}

