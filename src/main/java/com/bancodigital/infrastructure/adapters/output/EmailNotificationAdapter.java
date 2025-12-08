package com.bancodigital.infrastructure.adapters.output;

import com.bancodigital.application.ports.output.NotificationPort;
import com.bancodigital.external.ExternalEmailService;

public class EmailNotificationAdapter implements NotificationPort {
  private final ExternalEmailService emailService;

  public EmailNotificationAdapter(ExternalEmailService emailService) {
    this.emailService = emailService;
  }

  @Override
  public void notify(String clientId, String message) {
    // Adaptamos la llamada del dominio al servicio externo
    String to = clientId + "@banco.com";
    String subject = "Notificación bancaria";
    emailService.sendEmail(to, subject, message);
  }
}
