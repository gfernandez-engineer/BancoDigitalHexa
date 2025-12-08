package com.bancodigital.infrastructure.adapters.input;

import com.bancodigital.application.ports.input.CheckBalanceUseCase;
import com.bancodigital.application.ports.input.CreateAccountUseCase;
import com.bancodigital.application.ports.input.ListAccountsUseCase;
import com.bancodigital.application.ports.input.TransferMoneyUseCase;
import com.bancodigital.domain.model.Account;
import java.util.List;
import java.util.Scanner;

public class ConsoleInputAdapter {

  private final CreateAccountUseCase createAccountUseCase;
  private final CheckBalanceUseCase checkBalanceUseCase;
  private final TransferMoneyUseCase transferMoneyUseCase;
  private final ListAccountsUseCase listAccountsService;

  public ConsoleInputAdapter(
      CreateAccountUseCase createAccountUseCase,
      CheckBalanceUseCase checkBalanceUseCase,
      TransferMoneyUseCase transferMoneyUseCase,
      ListAccountsUseCase listAccountsService) {

    this.createAccountUseCase = createAccountUseCase;
    this.checkBalanceUseCase = checkBalanceUseCase;
    this.transferMoneyUseCase = transferMoneyUseCase;
    this.listAccountsService = listAccountsService;
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("=== BANCO DIGITAL ===");

    boolean salir = false;
    while (!salir) {
      System.out.println("\nSeleccione una opción:");
      System.out.println("1. Crear cuenta");
      System.out.println("2. Consultar saldo");
      System.out.println("3. Transferir dinero");
      System.out.println("4. Ver cuentas disponibles");
      System.out.println("0. Salir");

      System.out.print("Ingrese opción: ");
      if (!scanner.hasNextLine()) {
        System.out.println("No hay entrada disponible. Terminando programa.");
        break;
      }
      String opcionStr = scanner.nextLine().trim();


      int opcion;
      try {
        opcion = Integer.parseInt(opcionStr);
      } catch (NumberFormatException e) {
        System.out.println("Error: opción invalida.");
        continue; // vuelve al menú sin romper el programa
      }

      switch (opcion) {
        case 1 -> {
          System.out.print("Ingrese clientId: ");
          String clientId = scanner.nextLine();

          System.out.print("Ingrese numero de cuenta: ");
          String numeroCuenta = scanner.nextLine();

          System.out.print("Ingrese saldo inicial: ");
          double saldoInicial = scanner.nextDouble();
          scanner.nextLine();

          String accountId = createAccountUseCase.crear(clientId, numeroCuenta, saldoInicial);
          System.out.println("Cuenta creada con ID: " + accountId);
        }
        case 2 -> {
          System.out.print("Ingrese número de cuenta para consultar saldo: ");
          String consultaCuenta = scanner.nextLine();
          try {
            double saldo = checkBalanceUseCase.consultarSaldoPorNumeroCuenta(consultaCuenta);
            System.out.println("Saldo actual: " + saldo);
          } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
          }
        }
        case 3 -> {
          try {
            System.out.print("Ingrese cuenta origen: ");
            String origen = scanner.nextLine().trim();

            System.out.print("Ingrese cuenta destino: ");
            String destino = scanner.nextLine().trim();

            System.out.print("Ingrese monto: ");
            String montoStr = scanner.nextLine().trim();
            double monto = Double.parseDouble(montoStr);

            // Validación de saldo antes de transferir
            double saldoOrigen = checkBalanceUseCase.consultarSaldoPorNumeroCuenta(origen);
            if (saldoOrigen < monto) {
              System.out.println("Error: Saldo insuficiente en la cuenta origen.");
              return; // salimos del flujo de transferencia
            }

            System.out.print("Ingrese descripción: ");
            String descripcion = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            transferMoneyUseCase.transferir(origen, destino, monto, 0.0, descripcion);
            System.out.println("Transferencia realizada con exito");
          } catch (NumberFormatException e) {
            System.out.println("Error: El monto ingresado no es valido.");
          } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
          } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage()); // aquí capturamos "Saldo insuficiente"
          }
        }

        case 4 -> listarCuentas(); // ✅ aquí llamamos al método
        case 0 -> salir = true;
        default -> System.out.println("Opcion invalida");
      }
    }

    System.out.println("=== FIN ===");
    scanner.close();
  }

  private void listarCuentas() {
    List<Account> cuentas = listAccountsService.listarCuentas();
    cuentas.forEach(
        c ->
            System.out.println(
                "UID: "
                    + c.getAccountId()
                    + " | Cuenta: "
                    + c.getNumeroCuenta()
                    + " | Cliente: "
                    + c.getClientId()
                    + " | Saldo: "
                    + c.getSaldo()));
  }
}
