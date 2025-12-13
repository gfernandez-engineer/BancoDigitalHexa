package com.bancodigital.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Por qué estas propiedades: Identidad: accountId, numeroCuenta facilitan lookup por sistemas
 * distintos.
 * Vínculo: clientId permite notificar al cliente y validar pertenencia.
 * Reglas: saldo, estado son invariantes usados por depositar/retirar/transferir.
 * Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor te quitan boilerplate y mantienen foco en
 * reglas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
  private String accountId; // PK de la cuenta
  private String clientId; // FK al cliente
  private String numeroCuenta; // número legible/único
  private double saldo; // Saldo actual
  private EstadoCuenta estado; // ACTIVO,CERRADO
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaActualizacion;

  public Account(
      String accountId, String clientId, String numeroCuenta, double saldo, EstadoCuenta estado) {
    this.accountId = accountId;
    this.clientId = clientId;
    this.numeroCuenta = numeroCuenta;
    this.saldo = saldo;
    this.estado = estado;
    this.fechaCreacion = java.time.LocalDateTime.now();
    this.fechaActualizacion = java.time.LocalDateTime.now();
  }

  public void depositar(double monto) {
    if (monto <= 0) {
      throw new IllegalArgumentException("Monto inválido");
    }
    this.saldo += monto;
    this.fechaActualizacion = LocalDateTime.now();
  }

  public void retirar(double monto) {
    if (monto <= 0) {
      throw new IllegalArgumentException("Monto inválido");
    }
    if (estado != EstadoCuenta.ACTIVO) {
      throw new IllegalStateException("La cuenta no está activa");
    }
    if (this.saldo < monto) {
      throw new IllegalStateException("Saldo insuficiente");
    }
    this.saldo -= monto;
    this.fechaActualizacion = LocalDateTime.now();
  }

  public void cambiarEstado(EstadoCuenta nuevoEstado) {
    this.estado = nuevoEstado;
    this.fechaActualizacion = LocalDateTime.now();
  }

  public Object getNumeroCuenta() {
    return this.numeroCuenta;
  }

  public String getAccountId() {
    return  this.accountId;
  }

  public String getClientId() {
    return  this.clientId;
  }

  public Double getSaldo() {
    return this.saldo;
  }
}
