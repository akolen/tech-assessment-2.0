package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByAccountId(String accountId);

}