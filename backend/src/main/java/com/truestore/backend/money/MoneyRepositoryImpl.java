package com.truestore.backend.money;

import com.truestore.backend.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class MoneyRepositoryImpl implements MoneyRepository {

    private final JpaMoneyRepository jpaMoneyRepository;

    public MoneyRepositoryImpl(JpaMoneyRepository jpaMoneyRepository) {
        this.jpaMoneyRepository = jpaMoneyRepository;
    }

    @Override
    public List<Money> getMoneyTransitionsForUser(User user) {
        return jpaMoneyRepository.getAllByUser(user);
    }

    @Override
    public Float getBalanceByUser(User user) {
        AtomicReference<Float> result = new AtomicReference<>(0F);
        jpaMoneyRepository.getBalanceByUser(user.getId()).forEach(object -> {
            if (object[0] == TypeTransition.REPLENISHMENT || object[0] == TypeTransition.RECEIVING) {
                result.updateAndGet(v -> v + Float.parseFloat(object[1].toString()));
            } else {
                result.updateAndGet(v -> v - Float.parseFloat(object[1].toString()));
            }
        });
        return result.get();
    }

    @Override
    public Optional<Money> saveMoneyTransitionForUser(Money money) {
        try {
            return Optional.of(jpaMoneyRepository.save(money));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
