package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.AccountRepository;
import com.bancodigital.domain.model.Account;
import java.util.*;

public class AccountRepositoryInMemory implements AccountRepository {
  private final Map<String, Account> cuentas = new HashMap<>();

  @Override
  public Optional<Account> findById(String accountId) {
    return Optional.ofNullable(cuentas.get(accountId));
  }

  @Override
  public Optional<Account> findByNumeroCuenta(String numeroCuenta) {
    return cuentas.values().stream()
        .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
        .findFirst();
  }

  @Override
  public void save(Account account) {
    cuentas.put(account.getAccountId(), account);
  }
}
