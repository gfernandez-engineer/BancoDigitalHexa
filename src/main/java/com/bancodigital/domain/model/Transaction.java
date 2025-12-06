package com.bancodigital.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automaticamente getters, setters, toString, equals, hashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
  private String transaccionId;     // PK de la transacción (UUID)
  private String cuentaOrigenId; // FK
  private String cuentaDestinoId; // FK
  private double monto;
  private double comision;
  private TipoTransaccion tipoTransacion;  // Enum
  private EstadoTransaccion estadoTransaccion;          //Enum
  private String descripcion;     // Texto libre
  private LocalDateTime fechaCreacion;

  public void validar() {
    if (monto <= 0) {
      throw new IllegalArgumentException("El monto debe ser positivo");
    }
    if (comision < 0) {
      throw new IllegalArgumentException("La comisión no puede ser negativa");
    }
    if (tipoTransacion == null) {
      throw new IllegalArgumentException("Tipo de transacción requerido");
    }
    if (estadoTransaccion == null) {
      throw new IllegalArgumentException("Estado de transacción requerido");
    }
  }

  public void completar() {
    this.estadoTransaccion = EstadoTransaccion.COMPLETADA;
  }

  public void fallar(String motivo) {
    this.estadoTransaccion = EstadoTransaccion.FALLIDA;
    this.descripcion = motivo;
  }

  public boolean esPendiente() {
    return this.estadoTransaccion == EstadoTransaccion.PENDIENTE;
  }

  public double calcularMontoTotal() {
    return monto + comision;
  }
}
