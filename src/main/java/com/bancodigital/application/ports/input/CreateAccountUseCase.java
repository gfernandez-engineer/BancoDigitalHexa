package com.bancodigital.application.ports.input;

public interface CreateAccountUseCase {
  String crear(String clientId, String numeroCuenta, double saldoInicial);
}
