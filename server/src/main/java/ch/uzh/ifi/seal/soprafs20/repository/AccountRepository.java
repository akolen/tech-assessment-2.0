package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findById(String AccountId);

}
