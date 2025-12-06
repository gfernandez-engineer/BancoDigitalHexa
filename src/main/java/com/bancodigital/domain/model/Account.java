package com.bancodigital.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Por qué estas propiedades: Identidad: accountId, numeroCuenta facilitan lookup por sistemas
 * distintos. Vínculo: clientId permite notificar al cliente y validar pertenencia. Reglas: saldo,
 * estado son invariantes usados por depositar/retirar/transferir.
 * Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor te quitan boilerplate y mantienen foco en
 * reglas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  private String cuentaId; // PK de la cuenta
  private String clienteId; // FK al cliente
  private String numeroCuenta; // número legible/único
  private double saldo; // Saldo actual
  private String estado; // ACTIVO,CERRADO
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaActualizacion;

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
    if (!"ACTIVO".equalsIgnoreCase(this.estado)) {
      throw new IllegalStateException("La cuenta no está activa");
    }
    if (this.saldo < monto) {
      throw new IllegalStateException("Saldo insuficiente");
    }
    this.saldo -= monto;
    this.fechaActualizacion = LocalDateTime.now();
  }

  public void cambiarEstado(String nuevoEstado) {
    this.estado = nuevoEstado;
    this.fechaActualizacion = LocalDateTime.now();
  }
}
