package com.bancodigital.application.ports.output;

import com.bancodigital.domain.model.Account;
import java.util.Optional;

public interface AccountRepository {
  Optional<Account> findById(String cuentaId);

  void save(Account account);
}
