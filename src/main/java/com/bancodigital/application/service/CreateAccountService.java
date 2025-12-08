package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.CreateAccountUseCase;
import com.bancodigital.application.ports.output.AccountRepositoryPort;
import com.bancodigital.application.ports.output.NotificationPort;
import com.bancodigital.domain.factory.AccountFactory;
import com.bancodigital.domain.model.Account;

/**
 * CreateAccountService Usa AccountFactory (Factory Method) para delegar la construcción de cuentas.
 */
public class CreateAccountService implements CreateAccountUseCase {
  private final AccountRepositoryPort accountRepositoryPort;
  private final NotificationPort notificationPort;

  public CreateAccountService(
      AccountRepositoryPort accountRepositoryPort, NotificationPort notificationPort) {
    this.accountRepositoryPort = accountRepositoryPort;
    this.notificationPort = notificationPort;
  }

  @Override
  public String crear(String clientId, String numeroCuenta, double saldoInicial) {
    // Validación de uinicidad
    accountRepositoryPort
        .findByNumeroCuenta(numeroCuenta)
        .ifPresent(
            account -> {
              throw new IllegalArgumentException("El número de cuenta ya existe");
            });
    // Delegar la creación a la fabrica
    Account nueva = AccountFactory.createActiveAccount(clientId, numeroCuenta, saldoInicial);

    // Persistir
    accountRepositoryPort.save(nueva);

    // Notificar
    notificationPort.notify(
        clientId, "Cuenta creada: " + numeroCuenta + " con saldo " + saldoInicial);
    return nueva.getAccountId();
  }
}
