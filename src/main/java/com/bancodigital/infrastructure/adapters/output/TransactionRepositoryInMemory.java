package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.TransactionRepository;
import com.bancodigital.domain.model.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionRepositoryInMemory implements TransactionRepository {

  private final List<Transaction> transacciones = new ArrayList<>();

  @Override
  public void save(Transaction transaction) {
    transacciones.add(transaction);
  }

  @Override
  public List<Transaction> findAll() {
    return Collections.unmodifiableList(transacciones);
  }
}
