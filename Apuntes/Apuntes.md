# Patrón Strategy

## 🔹 Interfaz
> **CommissionStrategy**  
> Define el contrato para calcular la comisión.

---

## 🔹 Implementaciones concretas
> **FixedCommissionStrategy**  
> Estrategia con comisión fija.
>
> **PercentageCommissionStrategy**  
> Estrategia con comisión porcentual.

---

## 🔹 Servicio
> **TransferMoneyService**  
> Recibe la estrategia como dependencia y la usa en `transferir(...)`  
> para calcular la comisión sin acoplarse a un algoritmo específico.
