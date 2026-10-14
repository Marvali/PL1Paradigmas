# 🍪 PL1Paradigmas — Simulación de una fábrica de galletas

Práctica 1 de la asignatura **Paradigmas de Programación**. Simula el funcionamiento de una fábrica de galletas combinando **programación concurrente** (hilos en Java) y **programación distribuida** (cliente/servidor con Java RMI), con interfaces gráficas en Swing.

El enunciado completo está en [`enunciado.md`](enunciado.md).

---

## 📋 Descripción

La fábrica cuenta con:

| Rol | Cantidad | Qué hace |
|---|---|---|
| **Reposteros** | 5 | Producen tandas de 37–45 galletas (2–4 s por tanda), las depositan en un horno y paran a tomar café cada 3–5 tandas (la cafetera solo la puede usar uno a la vez). |
| **Hornos** | 3 | Reciben galletas hasta llenarse (200) y hornean durante 8 s. No admiten más galletas hasta quedar completamente vacíos. Las galletas sobrantes se desperdician. |
| **Empaquetadores** | 3 | Cada uno atiende un horno concreto: recoge lotes de 20 galletas y los lleva al almacén en paquetes de 100. |
| **Almacén** | 1 | Capacidad de 1000 galletas. Si se llena, los empaquetadores esperan. El usuario puede "comer" 100 galletas con un botón. |

Todos los roles (reposteros, hornos y empaquetadores) están modelados como **hilos** con un identificador único (`Repostero1`, `Horno1`, `Empaquetador1`…). Todos los eventos se registran con marca de tiempo en un archivo de log (`evolucionGalletas.txt`).

### Parte distribuida

- **Servidor**: ejecuta la simulación, muestra una interfaz gráfica con el estado de la fábrica y expone un objeto remoto vía **RMI** (`rmi://localhost:1099/objetoRMI`).
- **Cliente**: se conecta de forma remota y muestra, actualizándose cada **1 segundo**: galletas generadas, desperdiciadas y horneadas, estado de los hornos, galletas en el almacén y galletas consumidas. Permite **pausar y reanudar** a cada repostero individualmente.

---

## 🗂️ Estructura del proyecto

```
src/main/java/
├── LogicaServidor/     # Lógica concurrente y servidor RMI
│   ├── Main.java            # Punto de entrada del servidor
│   ├── Fabrica.java         # Estado global de la fábrica
│   ├── Repostero.java       # Hilo repostero
│   ├── Horno.java           # Hilo horno
│   ├── Empaquetador.java    # Hilo empaquetador
│   ├── Almacen.java         # Almacén compartido
│   ├── Cafetera.java        # Recurso compartido (exclusión mutua)
│   ├── Log.java             # Registro de eventos
│   ├── InterfazRemota.java  # Interfaz RMI
│   └── ObjetoRemoto.java    # Implementación del objeto remoto
├── InterfazServer/     # GUI del servidor (Swing)
├── ClienteLogic/       # Punto de entrada y lógica del cliente (RMI)
└── InterfazClient/     # GUI del cliente (Swing)
```

---

## 🛠️ Tecnologías

- **Java 23**
- **Maven**
- **Swing** (interfaces gráficas)
- **Java RMI** (comunicación cliente-servidor)
- Hilos y mecanismos de sincronización de Java

---

## ▶️ Cómo ejecutarlo

### Requisitos

- JDK 23 o superior
- Maven

### Pasos

```bash
git clone https://github.com/Marvali/PL1Paradigmas.git
cd PL1Paradigmas
mvn clean compile
```

1. **Arrancar el servidor** (clase `LogicaServidor.Main`). Se abre la GUI del servidor y se publica el objeto remoto en el puerto `1099`.
2. **Arrancar el cliente** (clase `ClienteLogic.Cliente`) en la misma máquina (o ajustando la dirección en el código para conectarse a otra).

Se pueden ejecutar desde el IDE (el proyecto incluye configuración de IntelliJ IDEA / NetBeans) o con `mvn exec:java -Dexec.mainClass=...`.

---

## 👥 Autores

- **Isaac Marval** — [@Marvali](https://github.com/Marvali)
- **Fernando Andradas** — [@FernandoAndradas](https://github.com/FernandoAndradas)

---

## 📄 Licencia

Distribuido bajo la licencia **GNU GPL v3**. Consulta el archivo [`LICENSE`](LICENSE) para más información.
