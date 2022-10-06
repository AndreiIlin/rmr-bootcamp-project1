package com.truestore.backend.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface JpaAppRepository extends PagingAndSortingRepository<App, String>,
        JpaSpecificationExecutor<App> {

    @Query("SELECT a FROM App a WHERE a.owner.id=:ownerId and a.appName LIKE %:appName% ORDER BY a.created desc ")
    Page<App> findAllByOwnerId(String ownerId, String appName, Pageable page);

    Optional<App> findAppByAppName(String s);
}
