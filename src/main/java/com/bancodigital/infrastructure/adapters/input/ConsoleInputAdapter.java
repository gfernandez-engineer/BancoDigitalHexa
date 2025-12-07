package com.bancodigital.infrastructure.adapters.input;

import com.bancodigital.application.ports.input.CheckBalanceUseCase;
import com.bancodigital.application.ports.input.CreateAccountUseCase;
import java.util.Scanner;

public class ConsoleInputAdapter {

  private final CreateAccountUseCase createAccountUseCase;
  private final CheckBalanceUseCase checkBalanceUseCase;

  public ConsoleInputAdapter(CreateAccountUseCase createAccountUseCase,
                             CheckBalanceUseCase checkBalanceUseCase) {
    this.createAccountUseCase = createAccountUseCase;
    this.checkBalanceUseCase = checkBalanceUseCase;
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("=== BANCO DIGITAL ===");

    // Crear cuenta
    System.out.print("Ingrese clientId: ");
    String clientId = scanner.nextLine();

    System.out.print("Ingrese número de cuenta: ");
    String numeroCuenta = scanner.nextLine();

    System.out.print("Ingrese saldo inicial: ");
    double saldoInicial = scanner.nextDouble();

    String accountId = createAccountUseCase.crear(clientId, numeroCuenta, saldoInicial);
    System.out.println("Cuenta creada con ID: " + accountId);

    // Consultar saldo
    scanner.nextLine(); // limpiar buffer
    System.out.print("Ingrese número de cuenta para consultar saldo: ");
    String consultaCuenta = scanner.nextLine();

    try {
      double saldo = checkBalanceUseCase.consultarSaldoPorNumeroCuenta(consultaCuenta);
      System.out.println("Saldo actual: " + saldo);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }

    System.out.println("=== FIN ===");
    scanner.close();
  }
}
