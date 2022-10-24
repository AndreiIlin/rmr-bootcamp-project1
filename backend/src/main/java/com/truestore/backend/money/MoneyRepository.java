package com.truestore.backend.money;

import com.truestore.backend.user.User;

import java.util.List;
import java.util.Optional;

public interface MoneyRepository {
    List<Money> getMoneyTransitionsForUser(User user);

    Float getBalanceByUser(User user);

    Optional<Money> saveMoneyTransitionForUser(Money money);
}
