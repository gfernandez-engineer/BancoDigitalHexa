package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.NotificationPort;

public class ConsoleNotificationAdapter implements NotificationPort {
  @Override
  public void notify(String cliendId, String message) {
    System.out.println("[NOTIFICACION] Cliente " + cliendId + ": " + message);
  }
}
