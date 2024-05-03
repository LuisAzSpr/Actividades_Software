# Historias de usuario y Criterios de aceptacion

**1. Como nuevo usuario de damasSprite necesito poder crear una cuenta para iniciar sesion.**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 1.1 Registro exitoso de una cuenta de usuario

    Dado un nombre de usuario válido no registrado

    Y una direccion de correo existente no registrado

    Y una contraseña valida
    
    Y una contraseña de confirmacion que coincida con la  anterior

    Cuando me registro con esos campos

    Entonces la cuenta debe ser creada exitosamente

    Y el  sistema retorna un token de sesion valido

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 1.2 Registro fallido de una cuenta de usuario con un nombre de usuario registrado

    Dado un nombre de usuario valido registrado

    Y una direccion de correo existente no registrado

    Y una contraseña valida

    Y una contraseña de confirmacion que coincida con la anterior

    Cuando me registro con esos campos

    Entonces la cuenta no debe ser creada

    Y el sistema muestra un de error de nombre ya en uso

    -------------------------
    Criterio de aceptacion 3
    -------------------------

    AC 1.3 Registro fallido de una cuenta de usuario con una contraseña no valida

    Dado un nombre de usuario válido que no haya sido registrado

    Y una direccion de correo existente no registrado

    Y una contraseña no valida

    Y una contraseña de confirmacion que coincida con la contraseña anterior

    Cuando me registro con esos campos

    Entonces la cuenta no debe ser creada

    Y el sistema muestra un error contraseña no valida

    -------------------------
    Criterio de aceptacion 4
    -------------------------

    AC 1.4 Registro fallido de una cuenta de usuario con correo electronico ya registrado

    Dado un nombre de usuario válido que no haya sido registrado

    Y una direccion de correo existente ya registrado

    Y una contraseña valida

    Y una contraseña de confirmacion que coincida con la contraseña anterior

    Cuando me registro con esos campos

    Entonces la cuenta no debe ser creada

    Y el sistema muestra un error correo electronico ya  registrado

    ---------------------------
    Criterio de aceptacion 5
    ---------------------------

    AC 1.5 Registro fallido de una cuenta de usuario con contrasenas no coincidentes
    
    Dado un nombre de usuario válido que no haya sido registrado

    Y una direccion de correo existente no registrado

    Y una contraseña valida

    Y una contraseña de confirmacion que no coincida con la contraseña anterior

    Cuando me registro con esos campos

    Entonces la cuenta no debe ser creada
    
    Y el sistema muestra un de error contraseñas diferentes

***

**2. Como usuario necesito poder iniciar sesion con mi cuenta creada.**

    ---------------------------
    Criterio de aceptacion 1
    ---------------------------

    AC 2.1 Inicio de sesion exitoso de una cuenta de usuario

    Dado nombre de usuario registrado

    Y una contraseña correspondiente al usuario registrado

    Cuando inicio sesion con estas credenciales

    Entonces un token de acceso es enviado al cliente

    Y accede a la página de bienvenida

    --------------------------
    Criterio de aceptacion 2
    --------------------------

    AC 2.2 Inicio de sesion fallida con un nombre de usuario inexistente

    Dado nombre de usuario no registrado

    Y una contraseña valida

    Cuando se inicia sesion con esos campos

    Entonces el usuario no es logeado

    Y no tiene acceso a la página de bienvenida

    --------------------------
    Criterio de aceptacion 3
    --------------------------
    
    AC 2.3 Inicio de sesion fallido con una contrasena incorrecta

    Dado nombre de usuario registrado

    Dado una contraseña no asociada al nombre de usuario

    Cuando inicio sesion con estas credenciales

    Entonces el usuario no es logeado

    Y no tiene acceso a la pagina de bienvenida

***

**3. Como usuario de damasSprite necesito que se cierre mi sesión.**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 3.1 Log out por acción del usuario
    
    Dado un usuario que ha iniciado sesion

    Cuando un usuario hace logout

    Entonces el token de acceso es invalidado

    Y accede a la pagina de Inicio de Sesion

***
**4. Como usuario de DamasSprite necesito visualizar los movimientos válidos cuando hago click en una pieza**

    ---------------------------------
    Criterio de aceptacion 1 
    ---------------------------------

    AC 4.1 Visualizar los movimientos válidos

    Dado un tablero en juego

    Y es turno de un jugador

    Y el jugador tiene una ficha en una casilla

    Y el rival tiene una ficha en una casilla de posible captura.

    Cuando se selecciona la ficha

    Entonces se muestran las casillas de captura para la ficha

    -------------------------
    Criterio de aceptacion 2
    -------------------------
    
    AC 4.2 Visualizar los movimientos simples validos

    Dado un tablero en juego

    Y es turno de un jugador

    Y el jugador tiene una ficha en una casilla

    Y el jugador no tiene posibles capturas

    Cuando se selecciona la ficha

    Entonces se muestran las casillas de movimientos simples para la ficha

***
**5. Como usuario de DamasSprite necesito poder jugar con otro jugador en la misma máquina**

    -------------------------
    Criterio de aceptacion 1
    -------------------------
    
    AC 5.1 Cambiar turno luego de un movimiento simple

    Dado un tablero en juego

    Y es turno de un jugador

    Y el jugador tiene una ficha en una casilla

    Y no tiene movimientos de captura 

    Cuando realiza el movimiento
    
    Entonces el turno cambia

    -------------------------
    Criterio de aceptacion 2
    -------------------------
    
    AC 5.2 Captura múltiple

    Dado un tablero en juego

    Y es turno de un jugador

    Y el jugador tiene una ficha en una casilla

    Y tiene movimientos de captura

    Cuando realiza el movimiento

    Y tiene movimientos de captura posteriores

    Y el turno continua


***
**6. Como jugador de DamasSprite, necesito un tablero de 8x8 para un juego de damas**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 6.1 Tablero Inicializado

    Dado se inicia una nueva partida de damas

    Cuando se inicializa el tablero

    Entonces se colocan peones blancos en las casillas oscuras del tablero en el rango de filas 0 a 2

    Y se colocan peones negros en las casillas oscuras del tablero en el rango de filas de 5 a 7
    
    Y todas las casillas restantes se establecen como vacías

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 6.2 Referencia de casilla no válida

    Dado un tablero

    Cuando una casilla es referenciada con un índice de fila o columna menor que 0 o mayor que 7

    Entonces la referencia de casilla no es válida

**
**7. Como jugador de DamasSprite, necesito tener las reglas definididas para poder jugar**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 7.1 Captura de peon exitoso

    Dado que hay un tablero en juego

    Y que es turno de un jugador

    Y que un peón del jugador en una casilla inicial es seleccionado

    Y que en la casilla final está vacía

    Y que hay una pieza rival en una casilla entre la casilla inicial y final

    Cuando el jugador mueve su peón a la casilla final

    Entonces la ficha rival se retira del tablero

    Y el peón del jugador se mueve a la nueva posición en el tablero

    Y es turno de rival

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 7.2 Captura de peon fallido por una celda ocupada

    Dado que hay un tablero en juego

    Y que es turno del jugador correspondiente

    Y que un peón del jugador en una casilla inicial es seleccionado

    Y que la casilla final está ocupada por una pieza rival

    Y que hay una pieza rival en una casilla entre la casilla inicial y final

    Cuando el jugador mueve su peón a la casilla final

    Entonces no se realiza la captura

    Y no se realiza el movimiento

    Y es turno del jugador correspondiente

    -------------------------
    Criterio de aceptacion 3
    -------------------------

    AC 7.3 Movimiento basico de dama exitoso

    Dado que hay un tablero en juego

    Y que es turno del jugador correspondiente

    Y que una dama del jugador en una casilla inicial es seleccionada

    Y que la casilla final está vacía

    Y que la diagonal entre la casilla inicial y final está despejada

    Cuando el jugador mueve su dama a la casilla final

    Entonces la dama del jugador se mueve a la nueva posición en el tablero

    Y es turno del jugador rival


    -------------------------
    Criterio de aceptacion 4
    -------------------------

    AC 7.4 Movimiento basico de dama fallido por una celda final ocupada

    Dado hay un tablero en juego

    Y es turno de un jugador

    Y una dama del jugador en una casilla inicial es seleccionada

    Y la casilla final está ocupada por una pieza rival

    Y la diagonal entre la casilla inicial y final está vacía

    Cuando el jugador mueve su ficha a la casilla final

    Entonces no se realiza el movimiento

    Y es turno del mismo jugador

    -------------------------
    Criterio de aceptacion 5
    -------------------------

    AC 7.5 Movimiento básico de dama fallido por una pieza ocupando una celda intermedia del salto

    Dado hay un tablero en juego

    Y es turno de un jugador

    Y una dama del jugador en una casilla inicial es seleccionada

    Y la casilla final está vacía

    Y hay una pieza rival entre la diagonal de la casilla inicial y final

    Cuando el jugador mueve su ficha a la casilla final

    Entonces no se realiza el movimiento

    Y es turno del mismo jugador

    -------------------------
    Criterio de aceptacion 6
    -------------------------

    AC 7.6 Captura de dama exitoso

    Dado hay un tablero en juego

    Y es turno de un jugador

    Y una dama del jugador en una casilla inicial es seleccionada

    Y la casilla final está vacía

    Y hay una pieza rival en una celda intermedia antes de llegar a la casilla final

    Cuando el jugador mueve su ficha a la casilla final

    Entonces la pieza rival se retira del tablero

    Y la dama del jugador se mueve a la nueva posición en el tablero

    Y es turno del jugador rival

    -------------------------
    Criterio de aceptacion 7
    -------------------------

    AC 7.7 Captura de dama fallido por una celda final ocupada

    Dado hay un tablero en juego

    Y es turno de un jugador

    Y una dama del jugador en una casilla inicial es seleccionada

    Y la casilla final está ocupada por una pieza rival

    Y hay una pieza rival en una celda intermedia antes de llegar a la casilla final

    Cuando el jugador mueve su ficha a la casilla final

    Entonces no se realiza el movimiento

    Y es turno del mismo jugador

    -------------------------
    Criterio de aceptacion 8
    -------------------------

    AC 7.8 Coronación de un peón al llegar al otro extremo del tablero

    Dado hay un tablero en juego

    Y es turno de un jugador

    Y un peón del jugador en una casilla inicial es seleccionado

    Y la casilla final está vacía

    Y la casilla final está en la última fila opuesta

    Cuando el jugador mueve su peón a la casilla final

    Entonces el peón se convierte en dama y adquiere sus funcionalidades

    Y es turno del jugador rival

    -------------------------
    Criterio de aceptacion 9
    -------------------------

    AC 7.9 Ganador de un juego

    Dado hay un tablero en juego

    Y es turno de un jugador

    Cuando el jugador no tiene movimientos disponibles

    Entonces el juego termina y rival es el ganador

    

**
