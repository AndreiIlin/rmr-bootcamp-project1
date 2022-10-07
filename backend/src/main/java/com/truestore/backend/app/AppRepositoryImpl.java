package com.truestore.backend.app;

import com.truestore.backend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppRepositoryImpl implements AppRepository {

    private final JpaAppRepository jpaAppRepository;

    public AppRepositoryImpl(JpaAppRepository jpaAppRepository) {
        this.jpaAppRepository = jpaAppRepository;
    }

    @Override
    public Optional<App> save(App app, User user) {
        if (app.getId() != null && getByIdAndUserId(app.getId(), user.getId()).isEmpty()) {
            return Optional.empty();
        }
        app.setOwner(user);
        return Optional.of(jpaAppRepository.save(app));
    }

    @Override
    public Optional<App> delete(String appId) {
        Optional<App> app = jpaAppRepository.findById(appId);
        app.ifPresent(a -> jpaAppRepository.deleteById(a.getId()));
        return app;
    }

    @Override
    public Optional<App> getByIdAndUserId(String appId, String userId) {
        return jpaAppRepository.findById(appId)
                .filter(app -> app.getOwner().getId().equals(userId));
    }

    @Override
    public Optional<App> get(String appId) {
        return jpaAppRepository.findById(appId);
    }

    @Override
    public List<App> getAll(String filter, PageRequest page) {
        if (filter.isEmpty()) {
            return jpaAppRepository.findAll(page).toList();
        } else {
            return jpaAppRepository.findAll(specification(filter), page).toList();
        }
    }

    @Override
    public Page<App> getAllByUserId(String userId, String filter, PageRequest page) {
        return jpaAppRepository.findAllByOwnerId(userId, filter, page);
    }

    private Specification<App> specification(String filter) {
        Specification<App> spec = Specification.where(null);
        if (filter.isEmpty()) {
            return spec;
        } else {
            return spec.and(AppFilterSpecs.appNameContains(filter))
//                    .and(AppFilterSpecs.appDescription(filter))
                    ;
        }
    }

    private static class AppFilterSpecs {
        public static Specification<App> appNameContains(String name) {
            return (Specification<App>) (root, cq, cb) -> cb.like(root.get("appName"), "%" + name + "%");
        }
    }
}

