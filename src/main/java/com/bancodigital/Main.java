package com.bancodigital;

import com.bancodigital.application.ports.input.CheckBalanceUseCase;
import com.bancodigital.application.ports.input.CreateAccountUseCase;
import com.bancodigital.application.service.CheckBalanceService;
import com.bancodigital.application.service.CreateAccountService;
import com.bancodigital.infrastructure.adapters.input.ConsoleInputAdapter;
import com.bancodigital.infrastructure.adapters.output.AccountRepositoryInMemory;
import com.bancodigital.infrastructure.adapters.output.ConsoleNotificationAdapter;

public class Main {
  public static void main(String[] args) {
    // Adaptadores de salida
    AccountRepositoryInMemory accountRepo = new AccountRepositoryInMemory();
    ConsoleNotificationAdapter notifier = new ConsoleNotificationAdapter();

    // Servicios (casos de uso)
    CreateAccountUseCase createAccountService = new CreateAccountService(accountRepo, notifier);
    CheckBalanceUseCase checkBalanceService = new CheckBalanceService(accountRepo);

    // Adaptador de entrada
    ConsoleInputAdapter consoleAdapter = new ConsoleInputAdapter(createAccountService, checkBalanceService);
    consoleAdapter.start();
  }
}
