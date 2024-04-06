# Historias de usuario y Criterios de aceptacion

**1. Como nuevo usuario de damasSprite necesito poder crear una cuenta para iniciar sesion.**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 1.1 Registro exitoso de una cuenta de usuario

    **DADO** un nombre de usuario valido que no haya sido registrado

    **Y** una contraseña que se considere valida que coincida con la contraseña confirmada

    **Y** una direccion de correo existente

    **CUANDO** creo una cuenta con este nombre de usuario

    **ENTONCES** el sistema crea exitosamente la cuenta nueva 

    **Y** Se muestra un mensaje que me registre con exito.

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 1.2 Registro fallido de una cuenta de usuario con un nombre de usuario registrado

    **DADO** un nombre de usuario valido que ya haya sido registrado

    **Y** una contraseña valida que coincida con la contraseña confirmada 

    **Y** una direccion de correo existente

    **CUANDO** creo una cuenta con ese nombre de usuario

    **ENTONCES** el sistema nos muestra un error con un mensaje de usuario existente

    -------------------------
    Criterio de aceptacion 3
    -------------------------

    AC 1.3 Registro fallido de una cuenta de usuario con una contraseña no valida

    **DADA** una contraseña no valida 

    **Y** un nombre de usuario valido que no haya sido registrado

    **Y** una direccion de correo electronico

    **CUANDO** creo una cuenta con esta contraseña no valida

    **ENTONCES** el sistema nos muestra un error con un mensaje de contraseña no valida.

    -------------------------
    Criterio de aceptacion 4
    -------------------------

    AC 1.4 Registro fallido de una cuenta de usuario con contrasenas que no coinciden

    **DADA** una contraseña valida 

    **Y** una contraseña confirmada que no coincida con la primera

    **CUANDO** creo una cuenta con estas contraseñas

    **ENTONCES** el sistema nos muestra un error con un mensaje de contrasenas no coincidentes.

    ---------------------------
    Criterio de aceptacion 5
    ---------------------------

    AC 1.5 Prevencion de creacion de cuentas maliciosas con caracteres especiales
    
    **DADO** un nombre de usuario no valido(malicioso)

    **Y** una contrasena valida

    **CUANDO** inicio sesion con el nombre de usuario y la contrasena 

    **ENTONCES** los intentos de sesion deben fallar
    
    **Y* mostrar un mensaje de error con un mensaje de nombre de usuario no valido

***

**2. Como usuario necesito poder iniciar sesion con mi cuenta creada.**

    ---------------------------
    Criterio de aceptacion 1
    ---------------------------

    AC 2.1 Inicio de sesion exitoso de una cuenta de usuario

    **DADO** un nombre de usuario registrado 

    **CUANDO** inicio sesion con este nombre de usuario 

    **Y** una contrasena que coincide con la contrasena con la que se creo la cuenta

    **ENTONCES** el sistema inicia sesion con esa cuenta

    **Y** nos redirige a la ventana de inicio

    --------------------------
    Criterio de aceptacion 2
    --------------------------

    AC 2.2 Inicio de sesion fallida con un nombre de usuario inexistente

    **DADO** un nombre de usuario no registrado

    **CUANDO** inicio sesion con este nombre de usuario

    **Y** una contrasena que coincide o no con la del usuario

    **ENTONCES** el sistema nos muestra un mensaje de usuario no registrado.

    --------------------------
    Criterio de aceptacion 3
    --------------------------
    
    AC 2.3 Inicio de sesion fallido con una contrasena incorrecta

    **DADO** un nombre de usuario registrado

    **Y** una contrasena que no coincide con la del usuario

    **CUANDO** inicio sesion con esta contrasena

    **ENTONCES** el sistema nos muestra un mensaje de contrasena incorrecta

***
**3. Como usuario que olvido su cuenta necesito poder recuperarla.**

    -----------------------------
    Criterio de aceptacion 1
    -----------------------------

    AC 3.1 Recuperacion de cuenta exitosa

    **DADO** un correo electronico registrado

    **CUANDO** doy al boton recuperar cuenta con este correo

    **ENTONCES** me llega un codigo al correo electronico 

    **Y** puedo restablecer mi contrasena
    
    **Y** el sistema me muestra contrasena reestablecida

    -------------------------------
    Criterio de aceptacion 2
    -------------------------------

    AC 3.2 Recuperacion de cuenta fallida con un correo electronico incorrecto

    **DADO** un correo electronico no registrado 

    **CUANDO** doy al boton recuperar cuenta con este correo

    **ENTONCES** el sistema nos muestra un mensaje de correo no registrado.

***
**4. Como usuario de damasSprite necesito que se cierre mi sesión.**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 4.1 Log out por acción del usuario
    
    **DADO** un usuario que ha iniciado sesión

    **CUANDO** un usuario hace click en log out

    **ENTONCES** se cierra la sesión actual del usuario.

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 4.2 Log out automático debido a la inactividad

    **DADO** un usuario que ha iniciado sesión.

    **CUANDO** la última interacción del usuario fue hace más de 30 minutos

    **ENTONCES** se cierra la sesión actual del usuario

***
**5. Como usuario de DamasSprite necesito visualizar los movimientos válidos cuando hago click en una pieza**

    ---------------------------------
    Criterio de aceptacion 1 
    ---------------------------------

    AC .1 Visualizar los movimientos válidos

    **DADO** estoy registrado en la página de DamasSprite

    **Y** estoy en una partida con un tablero de juego visible

    **CUANDO** hago click en una pieza para seleccionarla

    **ENTONCES** debería de ver los movimientos válidos que puedo hacer con esa pieza

***
**6. Como usuario de damasSprite necesito poder jugar online**
    
    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 6.1 Conexion exitoso entre dos jugadores

    **DADO** que estoy autenticado en damasSprite

    **CUANDO** selecciono la opcion para jugar con otro jugador

    **ENTONCES** el sistema encuentra y establece una conexion exitosa entre dos jugadores

    **Y** ambos jugadores pueden ver el tablero de juego y realizar movimientos de manera sincronizada

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 6.2 Conexion fallida entre dos jugadores
    
    -------------------------
    Criterio de aceptacion 3
    -------------------------

    AC 6.3 Búsqueda de Oponentes
    
    **DADO** que estoy en la página de damas

    **Y** que hay un jugador disponible

    **CUANDO** hago click en "buscar oponente"

    **ENTONCES** el sistema te empareja con un jugador disponible e inicia la partida

***
**7. Como usuario de damasSprite necesito poder revisar mis partidas**

    -------------------------
    Criterio de aceptacion 1
    -------------------------
    
    AC 7.1 Acceso al historial del usuario

    **DADO** que estoy en la pagina de damas

    **Y** he jugado partidas en la aplicacion

    **CUANDO** accedo a mi perfil de usuario

    **ENTONCES** debo de ver una lista de partidas anteriores con fechas y oponentes.

    -------------------------
    Criterio de aceptacion 2
    -------------------------

    AC 7.2 Acceso denegado al historial del usuario

    **DADO** que estoy en la pagina de damas
    
    **Y** no he jugado partidas en la aplicacion
    
    **CUANDO** accedo a mi perfil de usuario
    
    **ENTONCES** me sale un mensaje de que no tengo todavia ninguna partida jugada en la aplicacion.

***
**8. Como usuario de damasSprite necesito poder jugar contra una maquina**

    -------------------------
    Criterio de aceptacion 1
    -------------------------

    AC 8.1 Iniciar una partida contra la máquina

    **DADO** estoy en la pantalla de juego de DamasSprite

    **Y** he seleccionado la opción de jugar contra la máquina

    **CUANDO** Hago clic en el botón de iniciar partida

    **ENTONCES** la máquina realiza su primer movimiento y la partida comienza

  