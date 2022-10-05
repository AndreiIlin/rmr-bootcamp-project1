package com.truestore.backend.app;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAppRepository extends JpaRepository<App, String> {

    List<App> findByOwnerId(String ownerId);
}
