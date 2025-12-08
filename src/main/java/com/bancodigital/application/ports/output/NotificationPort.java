package com.bancodigital.application.ports.output;

/**
 * Puerto de salida para enviar notificaciones a los clientes. Permite desacoplar la lógica de
 * negocio del mecanismo de notificación.
 *
 * <p>ports.input → casos de uso (qué puede pedir el usuario). ports.output → dependencias externas
 * (qué necesita el sistema).
 */
public interface NotificationPort {
  void notify(String cliendId, String message);
}
