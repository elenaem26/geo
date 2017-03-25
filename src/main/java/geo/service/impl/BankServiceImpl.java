package geo.service.impl;

import geo.domain.Bank;
import geo.repository.BankRepository;
import geo.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehm on 25.03.2017.
 */
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public Bank addBank(Bank bank) {
        Bank savedBank = bankRepository.saveAndFlush(bank);
        return savedBank;
    }

    @Override
    public void delete(long id) {
        bankRepository.delete(id);
    }

    @Override
    public Bank getByName(String name) {
        return bankRepository.findByName(name);
    }

    @Override
    public Bank editBank(Bank bank) {
        return bankRepository.saveAndFlush(bank);
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }
}
