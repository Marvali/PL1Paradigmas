# **LABORATORIO DE PARADIGMAS DE LA PROGRAMACIÓN**

## **PARADIGMAS DE LA PROGRAMACIÓN**

### **Práctica de Laboratorio (E1)**
**Convocatoria Ordinaria – 2024/2025**  
Publicado el 05 de noviembre de 2024

### **Simulación de una fábrica de galletas**

#### **Programación Concurrente**

Se desea modelar el funcionamiento de una fábrica de galletas. La fábrica comienza con cinco reposteros, tres hornos y tres empaquetadores. Los reposteros se encargan de producir tandas de galletas, mientras que los hornos hornean las galletas cuando alcanzan su capacidad máxima. Los empaquetadores son responsables de llevar las galletas horneadas al almacén.

- Los reposteros tardan entre 2 y 4 segundos en producir una tanda de galletas, que puede ser entre 37 y 45 galletas, y luego las depositan en un horno.
- Una vez que completan de 3 a 5 tandas de producción, realizan una parada para el café. Durante este descanso, los reposteros se preparan café, pero solo hay una cafetera disponible, por lo que solo un repostero puede prepararse café a la vez.
    - En preparar café tardan 2 segundos, y, una vez se han preparado el café, descansan entre 3 y 6 segundos.
- Los hornos tienen una capacidad máxima de 200 galletas y solo comienzan a hornear cuando están llenos, tardando 8 segundos en hornear todas las galletas.
- Los empaquetadores recogerán las galletas en lotes de 20, lo cual les toma entre 0,5 y 1 segundos por cada lote, y, cuando llegan a 100 galletas, las empaquetarán.
    - No será hasta entonces cuando las lleven al almacén, lo que les toma entre 2 y 4 segundos.

El almacén tiene una capacidad máxima de 1000 galletas y contará con un botón que el usuario podrá pulsar para “comer” galletas. Cada pulsación del ratón debe consumir 100 galletas. Mientras el almacén esté lleno, los empaquetadores no podrán depositar más galletas, por lo que el usuario deberá vaciar el almacén regularmente para permitir nuevas entregas.

#### **Consideraciones adicionales:**
- Todos los roles (reposteros, hornos y empaquetadores) serán modelados obligatoriamente como hilos.
- La fábrica tendrá, en total:
    - 5 reposteros,
    - 3 hornos, y
    - 3 empaquetadores.
- Cada entidad tendrá un identificador único: “Repostero1”, “Empaquetador1”, “Horno1”, etc.
- Los reposteros irán llenando los hornos en orden.
    - Cuando en un horno no se puedan meter galletas, probarán con los siguientes hornos hasta que haya uno libre.
    - Si no hay hornos disponibles, los reposteros esperarán hasta que uno quede libre.
- Los hornos hornean solo cuando están completamente llenos.
- El flujo de traslado de galletas del horno al almacén solo se detiene cuando el almacén está lleno.
- Cada empaquetador solo puede recoger galletas de un horno específico.
    - El empaquetador 1 recoge galletas del horno 1, el empaquetador 2 del horno 2, y así sucesivamente.
- Antes de que los reposteros puedan volver a meter galletas después de un horneado, el horno se debe vaciar por completo.
- Si el repostero va a meter más galletas de las que faltan para completar la capacidad del horno, las galletas restantes se desperdician y se registran en el log.

Los tiempos de los reposteros, hornos y empaquetadores se generarán aleatoriamente mediante las funciones random de Java, y todo el comportamiento del sistema se guardará en un log (un fichero de texto llamado `evolucionGalletas.txt`), además de mostrarse gráficamente por pantalla, de forma que sea sencillo analizar lo sucedido.

El log guardará los eventos que van teniendo lugar, por ejemplo:
- “Repostero1 deja 40 galletas en el Horno1”,
- “Repostero2 desecha 25 galletas”,
- “Empaquetador1 recoge 20 galletas del Horno1”,
- “Empaquetador1 esperando”, etc.

En cada línea de dicho log deberá constar la marca de tiempo (incluyendo la fecha, la hora, el minuto y el segundo determinado en el que tuvo lugar el evento) y el evento en sí.

---

#### **Programación Distribuida**
Basándose en la aplicación concurrente descrita, se incluirá un nuevo módulo capaz de visualizar de forma remota el contenido de la fábrica (usando programación concurrente distribuida). Este módulo permitirá visualizar, mediante una interfaz gráfica, el estado del almacén, de los hornos y del personal.

**Se desarrollarán dos programas:**
1. **Servidor:**
    - Su código base será el programa desarrollado en el apartado “Programación Concurrente”.
    - Incluirá una interfaz gráfica y la funcionalidad correspondiente para dar soporte al módulo de visualización distribuida.

2. **Cliente (Módulo de Visualización):**
    - Permitirá al usuario visualizar el estado del almacén, los hornos y los reposteros de forma remota a través de una interfaz gráfica.
    - La interfaz mostrará:
        - Número de galletas generadas por los reposteros.
        - Número de galletas desperdiciadas.
        - Galletas horneadas.
        - Si cada horno está actualmente en proceso de horneado o no.
        - Cantidad de galletas en el almacén.
        - Galletas consumidas por el usuario.
    - Proporcionará también la capacidad de pausar o reanudar individualmente a cada uno de los reposteros.

La información mostrada será actualizada automáticamente en la interfaz del cliente con una periodicidad de 1 segundo, reflejando el estado real de la fábrica en todo momento.

---
