package com.truestore.backend.app;

import com.truestore.backend.user.User;
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
    public App save(App app, User user) {
        if (app.getId() != null && getByIdAndUserId(app.getId(), user.getId()).isEmpty()) {
            return null;
        }
        app.setOwner(user);
        return jpaAppRepository.saveAndFlush(app);
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
    public List<App> getAll() {
        return jpaAppRepository.findAll();
    }

    @Override
    public Optional<App> delete(String appId) {
        Optional<App> app = jpaAppRepository.findById(appId);
        app.ifPresent(a -> jpaAppRepository.deleteById(a.getId()));
        return app;
    }

    @Override
    public List<App> getAllByUserId(String userId) {
        return jpaAppRepository.findByOwnerId(userId);
    }

}
