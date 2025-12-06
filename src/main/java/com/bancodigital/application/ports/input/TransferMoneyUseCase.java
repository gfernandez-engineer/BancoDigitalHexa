package com.bancodigital.application.ports.input;

public interface TransferMoneyUseCase {
  void transferir(
      String cuentaOrigenId,
      String cuentaDestinoId,
      double monto,
      double comision,
      String descripcion);
}
