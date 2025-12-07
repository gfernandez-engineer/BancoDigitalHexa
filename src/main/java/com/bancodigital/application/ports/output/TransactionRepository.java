package com.bancodigital.application.ports.output;

import com.bancodigital.domain.model.Transaction;
import java.util.List;

public interface TransactionRepository {
  void save(Transaction transaction);

  List<Transaction> findAll();
}
