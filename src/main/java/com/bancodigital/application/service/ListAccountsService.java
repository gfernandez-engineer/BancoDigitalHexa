package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.ListAccountsUseCase;
import com.bancodigital.application.ports.output.AccountRepositoryPort;
import com.bancodigital.domain.model.Account;
import java.util.List;

public class ListAccountsService implements ListAccountsUseCase {
  private final AccountRepositoryPort accountRepository;

  public ListAccountsService(AccountRepositoryPort accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public List<Account> listarCuentas() {
    // Delegamos al repositorio para obtener todas las cuentas
    return accountRepository.findAll();
  }
}
