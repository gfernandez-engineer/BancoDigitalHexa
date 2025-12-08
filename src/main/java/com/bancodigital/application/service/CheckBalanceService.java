package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.CheckBalanceUseCase;
import com.bancodigital.application.ports.output.AccountRepositoryPort;

public class CheckBalanceService implements CheckBalanceUseCase {

  private final AccountRepositoryPort accountRepositoryPort;

  // Constructor que inicializa la dependencia
  public CheckBalanceService(AccountRepositoryPort accountRepositoryPort) {
    this.accountRepositoryPort = accountRepositoryPort;
  }

  @Override
  public double consultarSaldoPorNumeroCuenta(String numeroCuenta) {
    return accountRepositoryPort
        .findByNumeroCuenta(numeroCuenta)
        .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"))
        .getSaldo();
  }
}
