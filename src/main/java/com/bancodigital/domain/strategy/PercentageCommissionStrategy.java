package com.bancodigital.domain.strategy;

public class PercentageCommissionStrategy implements CommissionStrategy {
  private final double porcentaje;

  public PercentageCommissionStrategy(double porcentaje) {
    this.porcentaje = porcentaje;
  }

  @Override
  public double calcularComision(double monto) {
    return monto * porcentaje;
  }
}
