# Historias de usuario y Criterios de aceptacion

**1. Como nuevo usuario de damasSprite necesito poder crear una cuenta para iniciar sesion.**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 1.1 Registro exitoso de una cuenta de usuario

    **DADO** un nombre de usuario válido no registrado

    **Y** una direccion de correo existente no registrado

    **Y** una contraseña valida
    
    **Y** una contraseña de confirmacion que coincida con la  anterior

    **CUANDO** me registro con esos campos

    **ENTONCES** la cuenta debe ser creada exitosamente

    **Y** el  sistema retorna un token de sesion valido

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 1.2 Registro fallido de una cuenta de usuario con un nombre de usuario registrado

    **DADO** un nombre de usuario valido registrado

    **Y** una direccion de correo existente no registrado

    **Y** una contraseña valida

    **Y** una contraseña de confirmacion que coincida con la anterior

    **CUANDO** me registro con esos campos

    **ENTONCES** la cuenta no debe ser creada

    **Y** el sistema muestra un de error de nombre ya en uso

    -------------------------
    Criterio de aceptacion 3
    -------------------------

    AC 1.3 Registro fallido de una cuenta de usuario con una contraseña no valida

    **DADA** un nombre de usuario válido que no haya sido registrado

    **Y** una contraseña no valida

    **Y** una contraseña de confirmacion que coincida con la contraseña anterior

    **Y** una direccion de correo existente no registrado

    **CUANDO** me registro con esos campos

    **ENTONCES** la cuenta no debe ser creada

    **Y** el sistema muestra un error contraseña no valida

    -------------------------
    Criterio de aceptacion 4
    -------------------------

    AC 1.4 Registro fallido de una cuenta de usuario con correo electronico ya registrado

    **DADA** un nombre de usuario válido que no haya sido registrado

    **Y** una contraseña valida

    **Y** una contraseña de confirmacion que coincida con la contraseña anterior

    **Y** una direccion de correo existente ya registrado

    **CUANDO** me registro con esos campos

    **ENTONCES** la cuenta no debe ser creada

    **Y** el sistema muestra un error correo electronico ya  registrado

    ---------------------------
    Criterio de aceptacion 5
    ---------------------------

    AC 1.5 Registro fallido de una cuenta de usuario con contrasenas no coincidentes
    
    **DADO** un nombre de usuario válido que no haya sido registrado

    **Y** una contraseña valida

    **Y** una contraseña de confirmacion que no coincida con la contraseña anterior

    **Y** una direccion de correo existente no registrado

    **CUANDO** me registro con esos campos

    **ENTONCES**  la cuenta no debe ser creada
    
    **Y* el sistema muestra un de error contraseñas diferentes

***

**2. Como usuario necesito poder iniciar sesion con mi cuenta creada.**

    ---------------------------
    Criterio de aceptacion 1
    ---------------------------

    AC 2.1 Inicio de sesion exitoso de una cuenta de usuario

    **DADO** un nombre de usuario registrado 

    **Y** una contraseña correspondiente al usuario registrado

    **CUANDO** inicio sesion con estas credenciales

    **ENTONCES** un token de acceso es enviado al cliente

    **Y** accede a la página de bienvenida

    --------------------------
    Criterio de aceptacion 2
    --------------------------

    AC 2.2 Inicio de sesion fallida con un nombre de usuario inexistente

    **DADO** un nombre de usuario no registrado

    **Y** una contraseña valida

    **CUANDO** se inicia sesion con esos campos

    **ENTONCES** un token de acceso no es enviado al cliente

    **Y** no tiene acceso a la página de bienvenida

    --------------------------
    Criterio de aceptacion 3
    --------------------------
    
    AC 2.3 Inicio de sesion fallido con una contrasena incorrecta

    **DADO** un nombre de usuario registrado

    **Y** una contraseña no asociada al nombre de usuario

    **CUANDO**  inicio sesion con estas credenciales

    **ENTONCES** un token de acceso no es enviado al cliente

    **Y** no tiene acceso a la pagina de bienvenida

***

**3. Como usuario de damasSprite necesito que se cierre mi sesión.**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 3.1 Log out por acción del usuario
    
    **DADO** un usuario que ha iniciado sesión

    **CUANDO** un usuario hace click en log out

    **ENTONCES** se cierra la sesión actual del usuario

    **Y** accede a la pagina de Inicio de Sesion

***
**4. Como usuario de DamasSprite necesito visualizar los movimientos válidos cuando hago click en una pieza**

    ---------------------------------
    Criterio de aceptacion 1 
    ---------------------------------

    AC 4.1 Visualizar los movimientos válidos

    **DADO** un tablero en juego

    **Y** es turno de un jugador

    **CUANDO** se selecciona una pieza

    **ENTONCES** se muestran los movimientos validas para la casilla

***
**5. Como usuario de DamasSprite necesito poder jugar con otro jugador en la misma máquina**

    -------------------------
    Criterio de aceptacion 1
    -------------------------
    
    AC 5.1 Los jugadores alternan turnos

    **DADO** un tablero en juego

    **Y** es turno del jugador negro

    **CUANDO** se termina el turno del jugador negro

    **ENTONCES** es turno del jugador blanco


***
**6. Como jugador de DamasSprite, necesito un tablero de 8x8 para un juego de damas**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 6.1 Tablero Inicializado

    **DADO** se inicia una nueva partida de damas

    **CUANDO** se inicializa el tablero

    **ENTONCES** Then se colocan peones blancos en las casillas oscuras del tablero en el rango de filas 0 a 2

    **Y** todas las casillas restantes se establecen como vacías

        -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 6.2 Referencia de casilla no válida

    **DADO** un tablero

    **Y** he seleccionado la opción de jugar contra la máquina

    **CUANDO** una casilla es referenciada con un índice de fila o columna menor que 0 o mayor que 7

    **ENTONCES** la referencia de casilla no es válida

**
**7. Como jugador de DamasSprite, necesito tener las reglas definididas para poder jugar**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 7.1 Captura de peon exitoso

    **DADO** que hay un tablero en juego

    **Y** es turno de un jugador

    **Y** que un peón del jugador en una casilla inicial es seleccionado

    **Y** que en la casilla final está vacía

    **Y** que hay una pieza rival en una casilla entre la casilla inicial y final

    **CUANDO** el jugador mueve su peón a la casilla final

    **ENTONCES** la ficha rival se retira del tablero

    **Y** el peón del jugador se mueve a la nueva posición en el tablero

    **Y** es turno de rival

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 7.2 Captura de peon fallido por una celda ocupada

    **DADO** que hay un tablero en juego

    **Y** es turno del jugador correspondiente

    **Y** un peón del jugador en una casilla inicial es seleccionado

    **Y** que la casilla final está ocupada por una pieza rival

    **Y** hay una pieza rival entre la casilla inicial y final.

    **CUANDO** el jugador mueve su peón a la casilla final

    **ENTONCES** no se realiza la captura.

    **Y** no se realiza el movimiento.

    **Y** es turno del jugador correspondiente.

    -------------------------
    Criterio de aceptacion 3
    -------------------------

    AC 7.3 Movimiento basico de dama exitoso

    **DADO** que hay un tablero en juego

    **Y** es turno del jugador correspondiente

    **Y** una dama del jugador en una casilla inicial es seleccionada.

    **Y** que la casilla final está vacia

    **Y** que la diagonal entre la casilla inicial y final está despejada.

    **CUANDO** el jugador mueve su dama a la casilla final

    **ENTONCES** la dama del jugador se mueve a la nueva posición en el tablero

    **Y** es turno del jugador rival

    -------------------------
    Criterio de aceptacion 4
    -------------------------

    AC 7.4 Movimiento basico de dama fallido por una celda final ocupada

    **DADO** que hay un tablero en juego

    **Y** es turno del jugador correspondiente

    **Y** una dama del jugador en una casilla inicial es seleccionada

    **Y** la casilla final está ocupada por una pieza rival

    **Y** la diagonal entre la casilla inicial y final está vacía

    **CUANDO** el jugador mueve su ficha a la casilla final

    **ENTONCES** no se realiza el movimiento

    **Y** es turno del mismo jugador.

    -------------------------
    Criterio de aceptacion 5
    -------------------------

    AC 7.5 Movimiento básico de dama fallido por una pieza ocupando una celda intermedia del salto

    **DADO** que hay un tablero en juego

    **Y** es turno del jugador correspondiente

    **Y** una dama del jugador en una casilla inicial es seleccionada

    **Y** la casilla final está vacia

    **Y** hay una pieza rival entre la diagonal de la casilla inicial y final

    **CUANDO** el jugador mueve su ficha a la casilla final

    **ENTONCES** no se realiza el movimiento

    **Y** es turno del mismo jugador.

    -------------------------
    Criterio de aceptacion 6
    -------------------------

    AC 7.6 Captura de dama exitoso

    **DADO** que hay un tablero en juego

    **Y** es turno de un jugador

    **Y** una dama del jugador en una casilla inicial es seleccionada

    **Y** la casilla final está vacia

    **Y** hay una pieza rival en una celda antes de llegar a la casilla final

    **CUANDO** el jugador mueve su ficha a la casilla final

    **ENTONCES** la pieza rival se retira del tablero

    **Y** la dama del jugador se mueve a la nueva posición en el tablero

    **Y** es turno del jugador rival

    -------------------------
    Criterio de aceptacion 7
    -------------------------

    AC 7.7 Captura de dama fallido por una celda final ocupada

    **DADO** que hay un tablero en juego

    **Y** es turno de un jugador

    **Y** una dama del jugador en una casilla inicial es seleccionada

    **Y** la casilla final está ocupada por una pieza rival

    **Y** hay una pieza rival en una celda antes de llegar a la casilla final

    **CUANDO** el jugador mueve su ficha a la casilla final

    **ENTONCES** no se realiza el movimiento

    **Y** es turno del mismo jugador

    -------------------------
    Criterio de aceptacion 8
    -------------------------

    AC 7.8 Coronación de un peón al llegar al otro extremo del tablero

    **DADO** que hay un tablero en juego

    **Y** es turno de un jugador

    **Y** un peón del jugador en una casilla inicial es seleccionado

    **Y** la casilla final está vacia

    **Y** la casilla final está en la última fila opuesta

    **CUANDO** el jugador mueve su peon a la casilla final

    **ENTONCES** el peón se convierte en dama y adquiere sus funcionalidades

    **Y** es turno del jugador rival

**
