package com.bancodigital.external;

public class ExternalEmailService {
  public void sendEmail(String to, String subject, String body) {
    // Lógica de envío real (simulada aquí)
    System.out.println("Enviando correo a: " + to);
    System.out.println("Asunto: " + subject);
    System.out.println("Mensaje: " + body);
  }
}
