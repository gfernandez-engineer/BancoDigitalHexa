package com.bancodigital.domain.strategy;

/**
 * FixedCommissionStrategy Calcula una comisión fija independiente del monto. Útil para operaciones
 * simples o tarifas planas.
 */
public class FixedCommissionStrategy implements CommissionStrategy {
  private final double comisionFija;

  public FixedCommissionStrategy(double comisionFija) {
    this.comisionFija = comisionFija;
  }

  @Override
  public double calcularComision(double monto) {
    return comisionFija;
  }
}
