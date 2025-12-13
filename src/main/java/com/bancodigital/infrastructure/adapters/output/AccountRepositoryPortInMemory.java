package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.AccountRepositoryPort;
import com.bancodigital.domain.model.Account;
import java.util.*;

/**
 * AccountRepositoryPortInMemory → es adaptador del puerto AccountRepositoryPort.
 * * A esta clase le aplico singleton para que exista una sola instancia de
 * esta clase en toda la aplicación. Características principales: - Constructor privado (evita que
 * otros creen instancias). - Una variable estática que guarda la única instancia. - Un metodo
 * publico estático (getInstance()) que devuelve esa instancia.
 */
public class AccountRepositoryPortInMemory implements AccountRepositoryPort {
  private static AccountRepositoryPortInMemory instance; // única instancia
  private final Map<String, Account> cuentas = new HashMap<>();

  // Constructor privado: nadie puede hacer new desde fuera
  private AccountRepositoryPortInMemory() {}

  // Metodo de acceso global
  public static synchronized AccountRepositoryPortInMemory getInstance() {
    if (instance == null) {
      instance = new AccountRepositoryPortInMemory();
    }
    return instance;
  }

  @Override
  public Optional<Account> findById(String accountId) {
    return Optional.ofNullable(cuentas.get(accountId));
  }

  @Override
  public Optional<Account> findByNumeroCuenta(String numeroCuenta) {
    return cuentas.values().stream()
        .filter(account -> account.getNumeroCuenta().equals(numeroCuenta))
        .findFirst();
  }

  @Override
  public void save(Account account) {
    cuentas.put(account.getAccountId(), account);
  }

  @Override
  public List<Account> findAll() {
    return new ArrayList<>(cuentas.values()); // suponiendo que usas un Map interno
  }
}
