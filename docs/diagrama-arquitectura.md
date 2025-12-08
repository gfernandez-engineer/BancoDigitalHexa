# Diagrama simple de arquitectura hexagonal

```text
          +-------------------+
          |   Input Adapter    |
          | ConsoleInputAdapter|
          +---------+---------+
                    |
                    v
          +-------------------+
          |   Application     |
          |  Casos de Uso     |
          | (CreateAccount,   |
          |  TransferMoney,   |
          |  CheckBalance)    |
          +---------+---------+
                    |
        -----------------------------
        |                           |
        v                           v
+-------------------+       +-------------------+
| Output Adapter    |       | Output Adapter    |
| AccountRepoInMem  |       | Notification      |
| AccountRepoJpa    |       | Console/Email     |
+-------------------+       +-------------------+
