package com.bancodigital.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
  private String clientId;
  private String nombre;
  private String email;
  private String telefono;
  private EstadoCuenta estado;
}
