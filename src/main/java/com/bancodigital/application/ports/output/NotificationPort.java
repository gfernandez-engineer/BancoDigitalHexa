package com.bancodigital.application.ports.output;

/**
 * Puerto de salida para enviar notificaciones a los clientes. Permite desacoplar la lógica de
 * negocio del mecanismo de notificación.
 */
public interface NotificationPort {
  void notify(String cliendId, String message);
}
