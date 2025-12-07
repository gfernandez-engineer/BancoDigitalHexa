package com.bancodigital.application.service;

import com.bancodigital.application.ports.input.TransferMoneyUseCase;
import com.bancodigital.application.ports.output.AccountRepository;
import com.bancodigital.application.ports.output.TransactionRepository;
import com.bancodigital.domain.model.Account;
import com.bancodigital.domain.model.EstadoTransaccion;
import com.bancodigital.domain.model.TipoTransaccion;
import com.bancodigital.domain.model.Transaction;

public class TransferMoneyService implements TransferMoneyUseCase {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public TransferMoneyService(
      AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public void transferir(
      String cuentaOrigenId,
      String cuentaDestinoId,
      double monto,
      double comision,
      String descripcion) {
    // cargar entidades
    Account origen =
        accountRepository
            .findById(cuentaOrigenId)
            .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));
    Account destino =
        accountRepository
            .findById(cuentaDestinoId)
            .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));

    // validaciones de la aplicación
    if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
    if (cuentaOrigenId.equals(cuentaDestinoId))
      throw new IllegalArgumentException("La cuenta origen y detino deben ser diferentes");

    // regla en entidades
    origen.retirar(monto + comision); // protege contra saldo negativo
    destino.depositar(monto);

    // persistir efectos
    accountRepository.save(origen);
    accountRepository.save(destino);

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

    transactionRepository.save(transaction);
  }
}
