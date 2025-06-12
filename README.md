# MiniProyecto4
Miguel Angel Sanclemente Mejia - 202459488

## Explicación de las estructuras y funcionalidades que utilicé

### **Excepciones personalizadas**

-   **`PokemonDebilitadoException`**  
    Creé esta excepción para evitar que se pueda usar un Pokémon con HP ≤ 0. De esta manera, me aseguro de que un Pokémon debilitado no pueda atacar ni ser seleccionado durante la batalla.
    
-   **`AtaqueNoDisponibleException`**  
    Esta excepción se lanza cuando se intenta usar un ataque que no pertenece a la lista de ataques del Pokémon. 
    

----------

### **Estructuras de datos avanzadas**

-   **Pila (`Stack<String>`)**  
    Utilicé una pila en la clase `Batalla` para registrar el historial de movimientos.  
    Cada vez que ocurre un evento importante (como un ataque, un cambio de Pokémon o una derrota), agrego un mensaje a la pila.  
    Esto me permite mostrar el historial en la interfaz gráfica y también guardarlo para que se pueda recuperar al cargar una partida.
    
-   **Lista enlazada (`LinkedList<Pokemon>`)**  
    En la clase `Batalla`, usé una lista enlazada para manejar el orden de turnos de los Pokémon según su velocidad.  
    Antes de cada turno, ordeno los Pokémon activos para determinar cuál actúa primero, lo que mejora la lógica de combate.
    
-   **Tabla hash (`HashMap`)**  
    En la clase `Pokedex`, implementé un `HashMap` para almacenar y buscar Pokémon de manera eficiente, ya sea por nombre o tipo.  
    Esto facilita las búsquedas cuando se cargan partidas o se consulta información de los Pokémon.
    

----------

### **Persistencia**

-   **Guardar/cargar partidas en archivos `.txt`**  
    Programé el guardado del estado del juego (entrenadores, equipos y el historial de batalla) en archivos de texto como `entrenador1.txt`, `entrenador2.txt` y `batalla.txt`.  
    Esto permite que al cerrar la aplicación se guarde el progreso, y al iniciar, el usuario pueda continuar desde donde lo dejó con todos los datos restaurados correctamente.
    

----------

### **Vista (Interfaz Gráfica)**

-   **Panel para mostrar el historial de movimientos**  
    Implementé un panel lateral en la interfaz que muestra el historial de la batalla, actualizándolo después de cada turno.  
    Esta información proviene directamente de la pila que mantiene los movimientos, lo que mejora la experiencia del usuario.
    
-   **Opción para guardar/cargar partida**  
    Desde el menú principal, incluí la opción de cargar una partida previamente guardada.  
    Además, al finalizar una batalla, se puede volver al inicio y eliminar los archivos de guardado para comenzar una nueva partida desde cero.
