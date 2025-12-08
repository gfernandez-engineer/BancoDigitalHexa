package com.bancodigital.domain.factory;

import com.bancodigital.domain.model.Account;
import com.bancodigital.domain.model.EstadoCuenta;
import java.util.UUID;

/**
 * AccountFactory Implementa el patrón Factory Method. Centraliza la creación de objetos Account con
 * diferentes estados (ACTIVO, BLOQUEADO, CERRADO).
 */
public class AccountFactory {
  // Crear cuenta activa con saldo inicial
  public static Account createActiveAccount(
      String clientId, String numeroCuenta, double saldoInicial) {
    validarSaldo(saldoInicial);
    return new Account(
        UUID.randomUUID().toString(), clientId, numeroCuenta, saldoInicial, EstadoCuenta.ACTIVO);
  }

  // Crear cuenta bloqueada (saldo inicial = 0)
  public static Account createBlockedAccount(String clientId, String numeroCuenta) {
    return new Account(
        UUID.randomUUID().toString(), clientId, numeroCuenta, 0.0, EstadoCuenta.BLOQUEADO);
  }

  // Crear cuenta cerrada (saldo inicial = 0)
  public static Account createClosedAccount(String clientId, String numeroCuenta) {
    return new Account(
        UUID.randomUUID().toString(), clientId, numeroCuenta, 0.0, EstadoCuenta.CERRADO);
  }

  // Metodo privado para validar reglas comunes
  private static void validarSaldo(double saldoInicial) {
    if (saldoInicial < 0) {
      throw new IllegalArgumentException("Saldo inicial no puede ser negativo");
    }
  }
}
