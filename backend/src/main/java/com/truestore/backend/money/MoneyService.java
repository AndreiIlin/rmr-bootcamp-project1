package com.truestore.backend.money;

import com.truestore.backend.money.dto.CreateMoneyDto;
import com.truestore.backend.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MoneyService {

    private final MoneyRepository moneyRepository;

    public MoneyService(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    public List<Money> getMoneyTransitionsForUser(User user) {
        return moneyRepository.getMoneyTransitionsForUser(user);
    }

    public Float getBalanceByUser(User user) {
        return moneyRepository.getBalanceByUser(user);
    }

    @Transactional
    public Money replenishmentBalanceForUser(CreateMoneyDto createMoneyDto, User user) {
        if (!String.valueOf(createMoneyDto.getUserId()).equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The user can only top up his own balance");
        }
        Money money = new Money();
        money.setUser(user);
        money.setAmount(createMoneyDto.getAmount());
        money.setTypeTransition(TypeTransition.REPLENISHMENT);
        return moneyRepository.saveMoneyTransitionForUser(money).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save transaction")
        );
    }

    @Transactional
    public Money withdrawalBalanceForUser(CreateMoneyDto createMoneyDto, User user) {
        if (!String.valueOf(createMoneyDto.getUserId()).equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The user can only withdraw from his own balance");
        }
        Money money = new Money();
        money.setUser(user);
        money.setAmount(createMoneyDto.getAmount());
        money.setTypeTransition(TypeTransition.WITHDRAWAL);
        return moneyRepository.saveMoneyTransitionForUser(money).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.CONFLICT, "Unable to save transaction")
        );
    }
}
