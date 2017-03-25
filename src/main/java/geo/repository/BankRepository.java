package geo.repository;

import geo.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ehm on 25.03.2017.
 */
public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByName(String name);

}
