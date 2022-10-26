package com.truestore.backend.money;

import com.truestore.backend.report.Report;
import com.truestore.backend.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    @Transactional
    public Boolean verificationAndApprovalWithBalanceChanges(Report report, User user) {
        Float price;
        switch (report.getReportType()) {   // FEATURE, BUG, CLAIM
            case BUG:
                price = report.getContract().getApp().getBugPrice();
                break;
            case FEATURE:
                price = report.getContract().getApp().getFeaturePrice();
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find such report type");
        }
        Float balance = getBalanceByUser(user);
        if (balance < price) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient funds to write off");
        }
        try {
            Money moneyPayment = new Money();
            moneyPayment.setAmount(price);
            moneyPayment.setUser(user);
            moneyPayment.setTypeTransition(TypeTransition.PAYMENT);
            jpaMoneyRepository.save(moneyPayment);
            Money moneyReceiving = new Money();
            moneyReceiving.setAmount(price);
            moneyReceiving.setUser(report.getContract().getQa());
            moneyReceiving.setTypeTransition(TypeTransition.RECEIVING);
            jpaMoneyRepository.save(moneyReceiving);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
}
