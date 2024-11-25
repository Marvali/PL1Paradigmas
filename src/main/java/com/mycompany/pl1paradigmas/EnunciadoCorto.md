# **Simulación de una fábrica de galletas**

## **Programación Concurrente**

Se desea modelar el funcionamiento de una fábrica de galletas. La fábrica comienza con **cinco reposteros**, **tres hornos** y **tres empaquetadores**. Los **reposteros** se encargan de producir **tandas de galletas**, mientras que los **hornos** hornean las galletas cuando alcanzan su capacidad máxima. Los **empaquetadores** son responsables de llevar las **galletas horneadas al almacén**.

---

### **Roles y Tiempos**

#### **Reposteros**
- **Tareas**:
    - Producir tandas de galletas (**entre 37 y 45 galletas**).
    - Depositar las galletas en un horno.
    - Parar a tomar café después de producir **entre 3 y 5 tandas**.
- **Tiempos**:
    - Producción de una tanda: **entre 2 y 4 segundos**.
    - Preparar café: **2 segundos** (solo un repostero puede usar la cafetera al mismo tiempo).
    - Descanso tras el café: **entre 3 y 6 segundos**.

---

#### **Hornos**
- **Tareas**:
    - Recibir galletas de los reposteros.
    - Hornear galletas cuando estén llenos.
- **Capacidad máxima**: **200 galletas**.
- **Tiempo de horneado**: **8 segundos**.
- **Condiciones**:
    - Los hornos solo hornean cuando están **completamente llenos**.
    - Antes de que los reposteros puedan volver a usar el horno, este debe estar **vacío por completo**.

---

#### **Empaquetadores**
- **Tareas**:
    - Recoger galletas en lotes de **20**.
    - Empaquetar lotes de **100 galletas** y llevarlos al almacén.
- **Tiempos**:
    - Recoger un lote de 20: **entre 0,5 y 1 segundo**.
    - Transportar al almacén: **entre 2 y 4 segundos**.
- **Condiciones**:
    - Cada empaquetador recoge galletas de un **horno específico** (e.g., empaquetador 1 recoge del horno 1).
    - El flujo de traslado se detiene si el **almacén está lleno**.

---

### **Almacén**
- **Capacidad máxima**: **1000 galletas**.
- **Interacción del usuario**:
    - El usuario podrá "comer" galletas pulsando un botón.
    - Cada pulsación del botón consume **100 galletas**.
- **Condiciones**:
    - Mientras el almacén esté lleno, los empaquetadores no podrán depositar más galletas.
    - El usuario debe vaciar regularmente el almacén para permitir nuevas entregas.

---

### **Requisitos Adicionales**
- Todos los roles (**reposteros**, **hornos** y **empaquetadores**) deben ser modelados como **hilos**.
- La fábrica tendrá, en total:
    - **5 reposteros**
    - **3 hornos**
    - **3 empaquetadores**
- **Identificadores únicos**:
    - Cada entidad tendrá un identificador único (e.g., "Repostero1", "Horno1", "Empaquetador1").
- **Reglas de los hornos**:
    - Los reposteros llenarán los hornos en orden. Si un horno no tiene espacio, probarán con el siguiente.
    - Si no hay hornos disponibles, los reposteros esperarán.
    - Los hornos hornean solo cuando están completamente llenos.
    - Los hornos deben vaciarse completamente antes de aceptar más galletas.
- **Lógica de desperdicio**:
    - Si el repostero intenta meter más galletas de las que faltan para completar la capacidad del horno, las galletas sobrantes se **desperdician** y se registran en el log.

---

### **Log de Eventos**
- Todos los eventos se registrarán en un archivo de texto llamado `evolucionGalletas.txt`.
- Cada línea del log debe incluir:
    - Una **marca de tiempo** (fecha, hora, minuto y segundo).
    - Una descripción del evento (e.g., "Repostero1 deja 40 galletas en el Horno1", "Repostero2 desecha 25 galletas").
- Ejemplos de eventos registrados:
    - "Repostero1 deja 40 galletas en el Horno1".
    - "Empaquetador1 recoge 20 galletas del Horno1".
    - "Empaquetador1 esperando".

---

## **Programación Distribuida**

Basándose en la aplicación concurrente descrita, se incluirá un nuevo módulo para **visualizar de forma remota** el contenido de la fábrica mediante **programación concurrente distribuida**.

### **Requisitos del Sistema Distribuido**
1. **Servidor**:
    - Basado en el programa de programación concurrente desarrollado anteriormente.
    - Contará con una **interfaz gráfica** que refleje el estado de la fábrica.
    - Implementará funcionalidad para enviar el estado actual al cliente.
2. **Cliente**:
    - Permitirá al usuario **visualizar remotamente** el estado de la fábrica.
    - La interfaz mostrará:
        - Número de galletas generadas por los reposteros.
        - Número de galletas desperdiciadas.
        - Galletas horneadas.
        - Estado de los hornos (si están en proceso de horneado o no).
        - Cantidad de galletas en el almacén.
        - Galletas consumidas por el usuario.
    - Funcionalidad adicional:
        - **Pausar o reanudar** individualmente a cada repostero.
    - Actualización automática del estado del sistema cada **1 segundo**.

---

### **Ejemplo de Interfaces Gráficas**
#### **Interfaz del Servidor**
- Mostrar el estado de la fábrica (hornos, almacén, reposteros y empaquetadores).
- Botones para iniciar y detener procesos.

#### **Interfaz del Cliente**
- Visualización del estado en tiempo real.
- Botones para pausar o reanudar reposteros.
- Indicador de cantidad de galletas consumidas.

---
