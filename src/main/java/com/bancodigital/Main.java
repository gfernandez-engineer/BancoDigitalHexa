package com.bancodigital;

import com.bancodigital.application.ports.input.CheckBalanceUseCase;
import com.bancodigital.application.ports.input.CreateAccountUseCase;
import com.bancodigital.application.ports.input.ListAccountsUseCase;
import com.bancodigital.application.ports.input.TransferMoneyUseCase;
import com.bancodigital.application.ports.output.NotificationPort;
import com.bancodigital.application.service.CheckBalanceService;
import com.bancodigital.application.service.CreateAccountService;
import com.bancodigital.application.service.ListAccountsService;
import com.bancodigital.application.service.TransferMoneyService;
import com.bancodigital.domain.strategy.CommissionStrategy;
import com.bancodigital.domain.strategy.FixedCommissionStrategy;
import com.bancodigital.external.ExternalEmailService;
import com.bancodigital.infrastructure.adapters.input.ConsoleInputAdapter;
import com.bancodigital.infrastructure.adapters.output.AccountRepositoryPortInMemory;
import com.bancodigital.infrastructure.adapters.output.ConsoleNotificationAdapter;
import com.bancodigital.infrastructure.adapters.output.EmailNotificationAdapter;
import com.bancodigital.infrastructure.adapters.output.TransactionRepositoryPortInMemory;

/**
 * Clase Main
 *
 * <p>Punto de entrada de la aplicación Banco Digital. Aquí se configuran los adaptadores de salida
 * (repositorios, notificaciones), los casos de uso (servicios de aplicación) y el adaptador de
 * entrada (consola).
 *
 * <p>Patrones aplicados: - Singleton: AccountRepositoryPortInMemory se instancia con getInstance()
 * para garantizar una única instancia compartida en toda la aplicación.
 *
 * <p>- Strategy: TransferMoneyService recibe una CommissionStrategy que define cómo calcular la
 * comisión de las transferencias. Esto permite intercambiar algoritmos de manera flexible (ej.
 * FixedCommissionStrategy, PercentageCommissionStrategy), cumpliendo el principio Open/Closed.
 *
 * <p>- Adapter: Se implementa el patrón Adapter tanto en entrada como en salida:
 *
 * <p>Adaptadores de entrada (input adapters): - ConsoleInputAdapter: traduce la interacción del
 * usuario en consola hacia los casos de uso. Implementa los puertos de entrada definidos en
 * application.ports.input. Permite que el sistema reciba comandos externos sin acoplarse a la
 * tecnología de entrada.
 *
 * <p>Adaptadores de salida (output adapters): - AccountRepositoryPortInMemory: implementa el puerto
 * de salida para persistencia de cuentas. Simula una base de datos en memoria, útil para pruebas y
 * entornos sin persistencia real.
 *
 * <p>- TransactionRepositoryPortInMemory: implementa el puerto de salida para persistencia de
 * transacciones.
 *
 * <p>- ConsoleNotificationAdapter: adapta el puerto NotificationPort para enviar mensajes por
 * consola. Útil para entornos locales o demostraciones.
 *
 * <p>- EmailNotificationAdapter: adapta el puerto NotificationPort para enviar correos electrónicos
 * usando un servicio externo (ExternalEmailService). Este adaptador traduce la interfaz del dominio
 * hacia una API incompatible, ejemplificando el patrón Adapter en su forma clásica.
 *
 * <p>Flujo de ejecución: 1. Se inicializan los adaptadores de salida (repositorios y
 * notificaciones). 2. Se selecciona una estrategia de comisión (Strategy Pattern). 3. Se crean los
 * servicios de aplicación (casos de uso). 4. Se conecta el adaptador de entrada (consola) con los
 * casos de uso. 5. Se inicia la interacción con el usuario mediante consola.
 *
 * <p>Flexibilidad: - Los adaptadores de entrada pueden ser reemplazados por otros (ej.
 * RESTInputAdapter, GUIInputAdapter). - Los adaptadores de salida pueden cambiarse sin modificar el
 * dominio (ej. pasar de consola a email). - La estrategia de comisión puede intercambiarse
 * dinámicamente.
 *
 * <p>Esta estructura permite mantener el núcleo de negocio aislado de detalles tecnológicos,
 * facilitando pruebas, mantenimiento y extensión del sistema.
 */
public class Main {
  public static void main(String[] args) {
    // Adaptadores de salida (repositorios)
    AccountRepositoryPortInMemory accountRepo = AccountRepositoryPortInMemory.getInstance();
    TransactionRepositoryPortInMemory transactionRepo = new TransactionRepositoryPortInMemory();

    // Servicio externo
    ExternalEmailService externalEmailService = new ExternalEmailService();

    // Adaptadores de notificación (Adapter Pattern)
    NotificationPort consoleNotifier = new ConsoleNotificationAdapter();
    NotificationPort emailNotifier = new EmailNotificationAdapter(externalEmailService);

    // Seleccionar estrategia de comisión
    CommissionStrategy commissionStrategy =
        new FixedCommissionStrategy(5.0); // comisión fija de 5 unidades

    // Servicios (casos de uso)
    CreateAccountUseCase createAccountService =
        new CreateAccountService(accountRepo, emailNotifier);
    CheckBalanceUseCase checkBalanceService = new CheckBalanceService(accountRepo);
    TransferMoneyUseCase transferMoneyService =
        new TransferMoneyService(accountRepo, transactionRepo, commissionStrategy);
    ListAccountsUseCase listAccountsService =
        new ListAccountsService(accountRepo); // nuevo caso de uso

    // Adaptador de entrada
    ConsoleInputAdapter consoleAdapter =
        new ConsoleInputAdapter(
            createAccountService, checkBalanceService, transferMoneyService, listAccountsService);

    // Iniciar aplicación
    consoleAdapter.start();
  }
}
