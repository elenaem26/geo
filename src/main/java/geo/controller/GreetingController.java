package geo.controller;

import geo.domain.Bank;
import geo.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by ehm on 25.03.2017.
 */
@Controller
public class GreetingController {

    @Autowired
    BankService bankService;

    @RequestMapping(value="/bank", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Bank greeting() {
        Bank bank = new Bank();
        bank.setName("baank!");
        return bankService.addBank(bank);
    }
}
