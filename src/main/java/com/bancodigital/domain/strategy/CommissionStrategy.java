package com.bancodigital.domain.strategy;

/**
 * CommissionStrategy Define el contrato para calcular la comisión de una transferencia. Permite
 * intercambiar algoritmos sin modificar el servicio.
 */
public interface CommissionStrategy {
  double calcularComision(double monto);
}
