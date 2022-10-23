package com.truestore.backend.money;

import com.truestore.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface JpaMoneyRepository extends JpaRepository<Money, String> {

    List<Money> getAllByUser(User user);

    @Query("SELECT m.typeTransition, sum(m.amount) FROM Money as m WHERE m.user.id = :userId GROUP BY m.typeTransition")
    List<Object[]> getBalanceByUser(String userId);

}
