package com.bancodigital.application.ports.input;

import com.bancodigital.domain.model.Account;
import java.util.List;

public interface ListAccountsUseCase {
  List<Account> listarCuentas();
}
