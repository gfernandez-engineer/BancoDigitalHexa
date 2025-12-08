package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.AccountRepositoryPort;
import com.bancodigital.domain.model.Account;
import java.util.List;
import java.util.Optional;

/** AccountRepositoryJpa → es adaptador del puerto AccountRepositoryPort. */
public class AccountRepositoryJpa implements AccountRepositoryPort {

  @Override
  public Optional<Account> findById(String cuentaId) {
    return Optional.empty();
  }

  @Override
  public Optional<Account> findByNumeroCuenta(String numeroCuenta) {
    return Optional.empty();
  }

  @Override
  public void save(Account account) {}

  @Override
  public List<Account> findAll() {
    return List.of();
  }
}
