package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.CreateAccountUseCase;
import com.bancodigital.application.ports.output.AccountRepository;
import com.bancodigital.application.ports.output.NotificationPort;
import com.bancodigital.domain.model.Account;
import com.bancodigital.domain.model.EstadoCuenta;
import java.util.UUID;

public class CreateAccountService implements CreateAccountUseCase {
  private final AccountRepository accountRepository;
  private final NotificationPort notificationPort;

  public CreateAccountService(
      AccountRepository accountRepository, NotificationPort notificationPort) {
    this.accountRepository = accountRepository;
    this.notificationPort = notificationPort;
  }

  @Override
  public String crear(String clientId, String numeroCuenta, double saldoInicial) {
    accountRepository
        .findByNumeroCuenta(numeroCuenta)
        .ifPresent(
            account -> {
              throw new IllegalArgumentException("El número de cuenta ya existe");
            });

    if (saldoInicial < 0) throw new IllegalArgumentException("Saldo incial no puede ser negativo");
    String accountId = UUID.randomUUID().toString();
    Account nueva =
        new Account(accountId, clientId, numeroCuenta, saldoInicial, EstadoCuenta.ACTIVO);
    accountRepository.save(nueva);

    notificationPort.notify(
        clientId, "Cuenta creada: " + numeroCuenta + " con saldo " + saldoInicial);
    return accountId;
  }
}
