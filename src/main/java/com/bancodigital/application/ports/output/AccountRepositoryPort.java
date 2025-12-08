package com.bancodigital.application.ports.output;

import com.bancodigital.domain.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
  Optional<Account> findById(String cuentaId);

  Optional<Account> findByNumeroCuenta(String numeroCuenta);

  void save(Account account);

  List<Account> findAll();
}
