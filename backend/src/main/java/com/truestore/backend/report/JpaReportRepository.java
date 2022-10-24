package com.truestore.backend.report;

import com.truestore.backend.app.App;
import com.truestore.backend.contract.Contract;
import com.truestore.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReportRepository extends JpaRepository<Report, String> {

    List<Report> findAllByContractQaOrderByCreatedDesc(User qa);

    List<Report> findAllByContractQaAndContractOrderByCreatedDesc(User qa, Contract contract);

    List<Report> findAllByContractAppOrderByCreatedDesc(App app);
}
