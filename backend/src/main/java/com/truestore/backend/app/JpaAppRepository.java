package com.truestore.backend.app;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JpaAppRepository extends PagingAndSortingRepository<App, String>,
        JpaSpecificationExecutor<App> {

    List<App> findAllByOwnerIdAndAppNameContainingIgnoreCase(String ownerId, String appName, Pageable page);

}
