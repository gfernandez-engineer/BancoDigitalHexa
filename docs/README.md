
_---

##  `/docs/README.md`

```markdown
# Banco Digital - Documentación de Patrones

Este proyecto aplica varios patrones de diseño y arquitectura:

##  Arquitectura Hexagonal
- Separa el núcleo de negocio de los detalles tecnológicos.
- Usa **puertos** (interfaces) y **adaptadores** (implementaciones).
- Ejemplo: `NotificationPort` con adaptadores `ConsoleNotificationAdapter` y `EmailNotificationAdapter`.

##  Strategy
- Permite intercambiar algoritmos de cálculo de comisión.
- Ejemplo: `CommissionStrategy` con `FixedCommissionStrategy` y `PercentageCommissionStrategy`.

##  Singleton
- Garantiza una única instancia de repositorio en memoria.
- Ejemplo: `AccountRepositoryInMemory.getInstance()`.

##  Adapter
- Traduce interfaces incompatibles entre el dominio y servicios externos.
- Ejemplo: `EmailNotificationAdapter` adapta `NotificationPort` a `ExternalEmailService`.

##  Separación de paquetes
- `application`: lógica de negocio y casos de uso.
- `domain`: entidades y estrategias.
- `infrastructure.adapters`: entrada/salida.
- `external`: servicios externos simulados.

Este diseño facilita pruebas, mantenimiento y extensión del sistema._
