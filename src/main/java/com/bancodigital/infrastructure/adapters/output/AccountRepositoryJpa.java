package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.AccountRepository;
import com.bancodigital.domain.model.Account;
import java.util.Optional;

public class AccountRepositoryJpa implements AccountRepository {

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
}
