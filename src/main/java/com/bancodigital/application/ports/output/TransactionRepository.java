package com.bancodigital.application.ports.output;

import com.bancodigital.domain.model.Transaction;

public interface TransactionRepository {
  void save(Transaction transaction);
}
