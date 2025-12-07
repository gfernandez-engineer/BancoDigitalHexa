package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.CheckBalanceUseCase;
import com.bancodigital.application.ports.output.AccountRepository;

public class CheckBalanceService implements CheckBalanceUseCase {

  private final AccountRepository accountRepository;

  // Constructor que inicializa la dependencia
  public CheckBalanceService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public double consultarSaldoPorNumeroCuenta(String numeroCuenta) {
    return accountRepository
        .findByNumeroCuenta(numeroCuenta)
        .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"))
        .getSaldo();
  }
}
