package geo.service;

import geo.domain.Bank;

import java.util.List;

/**
 * Created by ehm on 25.03.2017.
 */
public interface BankService {

    Bank addBank(Bank bank);

    void delete(long id);

    Bank getByName(String name);

    Bank editBank(Bank bank);

    List<Bank> getAll();
}
