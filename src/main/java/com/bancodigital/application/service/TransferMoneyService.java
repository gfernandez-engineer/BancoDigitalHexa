package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.TransferMoneyUseCase;
import com.bancodigital.application.ports.output.AccountRepositoryPort;
import com.bancodigital.application.ports.output.TransactionRepositoryPort;
import com.bancodigital.domain.model.Account;
import com.bancodigital.domain.model.EstadoTransaccion;
import com.bancodigital.domain.model.TipoTransaccion;
import com.bancodigital.domain.model.Transaction;
import com.bancodigital.domain.strategy.CommissionStrategy;

public class TransferMoneyService implements TransferMoneyUseCase {

  private final AccountRepositoryPort accountRepositoryPort;
  private final TransactionRepositoryPort transactionRepositoryPort;
  private final CommissionStrategy commissionStrategy; // estrategia inyectada

  public TransferMoneyService(
      AccountRepositoryPort accountRepositoryPort,
      TransactionRepositoryPort transactionRepositoryPort,
      CommissionStrategy commissionStrategy) {
    this.accountRepositoryPort = accountRepositoryPort;
    this.transactionRepositoryPort = transactionRepositoryPort;
    this.commissionStrategy = commissionStrategy;
  }

  @Override
  public void transferir(
      String cuentaOrigenId,
      String cuentaDestinoId,
      double monto,
      double comision,
      String descripcion) {

    // cargar entidades
    // validamos cuenta origen
    Account origen =
        accountRepositoryPort
            .findByNumeroCuenta(cuentaOrigenId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Cuenta origen '" + cuentaOrigenId + "' no encontrada"));

    // validamos cuenta destino
    Account destino =
        accountRepositoryPort
            .findByNumeroCuenta(cuentaDestinoId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Cuenta origen '" + cuentaDestinoId + "' no encontrada"));

    // validaciones de la aplicación
    if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
    if (cuentaOrigenId.equals(cuentaDestinoId))
      throw new IllegalArgumentException("La cuenta origen y destino deben ser diferentes");

    // Usar la estrategasia para calcular comisión
    double comisionCalculada = commissionStrategy.calcularComision(monto);

    // regla en entidades
    origen.retirar(monto + comisionCalculada); // protege contra saldo negativo
    destino.depositar(monto);

    // persistir efectos
    accountRepositoryPort.save(origen);
    accountRepositoryPort.save(destino);

    // registrar transaccion
    Transaction transaction =
        Transaction.builder()
            .transaccionId(java.util.UUID.randomUUID().toString())
            .cuentaOrigenId(cuentaOrigenId)
            .cuentaDestinoId(cuentaDestinoId)
            .monto(monto)
            .comision(comision)
            .tipoTransacion(TipoTransaccion.TRANSFERENCIA)
            .estadoTransaccion(EstadoTransaccion.COMPLETADA)
            .descripcion(descripcion)
            .fechaCreacion(java.time.LocalDateTime.now())
            .build();

    transactionRepositoryPort.save(transaction);
  }
}
