package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.TransactionRepositoryPort;
import com.bancodigital.domain.model.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** TransactionRepositoryPortInMemory → adaptador del puerto TransactionRepositoryPort */
public class TransactionRepositoryPortInMemory implements TransactionRepositoryPort {

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
