Feature: Navegacion de un usuario
  dentro de la pagina de Damas Sprite

  Background: Soy un nuevo usuario que visita por primera vez la pagina de
  Damas Sprite
      Given Navego a www.DamasSprite.com

    Scenario Outline: Puedo acceder a las subpaginas de la barra de navegacion
      When Yo voy a la <seccion> usando la barra de navegacion
      Then Navego a las rutas correspondientes de cada <seccion>
      Examples:
        |seccion|
        |Inicio|
        |Preguntas frecuentes|
        |Acerca de nosotros|

    Scenario: No puedo acceder a la pagina si soy un usuario sin registrarse
      And Dejo todos los campos vacios
      When Yo le doy al boton Ingresar
      Then El sistema no me permite ingresar

    Scenario: Accedo a la pagina de registro y trato de registrarme con los campos vacios
      When Yo le doy al boton Registrarse
      And Dejo todos los campos vacios
      Then El sistema no me permite registrar

    Scenario Outline: Accedo a la pagina de registro y trato de registrarme con datos incorrectos en los campos
      When Yo le doy al boton Registrarse
      And Lleno los campos nombre de usuario con <nombre> correo electronico con <email> elegimos el password <password> confirmamos el password con <passwordConfirmation>
      And Aceptamos los terminos y condiciones
      When Yo le doy otra vez al boton Registrarse
      Then El sistema no me permite registrar
      Examples:
        |nombre|email           |password  |passwordConfirmation|
        |Ao    |Ao_123@gmail.com|Ao1234AoAo|Ao123#AoAo          |
        |Ao1231 |Ao_123gmail.com |Ao1234AoAo|Ao123#AoAo          |
        |Ao1233 |Ao_123@gmailcom |Ao1234AoAo|Ao123#AoAo          |
        |Ao1234 |@gmail.com|Ao1234AoAo|Ao123#AoAo          |
        |Ao1235 |Ao_123@gmail.com|Ao        |Ao                  |
        |Ao1238 |Ao_123@gmail.com|Ao1234AoAo|Ao1235AoAo          |

    Scenario: Accedo a la pagina de registro y trato de registrarme con datos correctos
      When Yo le doy al boton Registrarse
      And Lleno los campos nombre de usuario con Ao1234 correo electronico con Ao_123@gmailcom elegimos el password Ao1234AoAo confirmamos el password con Ao1234AoAo
      And Aceptamos los terminos y condiciones
      When Yo le doy otra vez al boton Registrarse
      Then El sistema me da la bienvenida a DamasSprite

    Scenario: Trato de ingresar con credenciales incorrectas
      And Lleno los campos de login con nombre de usuario con cuentaInexistente y con un password password
      When Yo le doy al boton Ingresar
      Then El sistema no me permite ingresar

    Scenario: Ingreso con credenciales validas
      And Lleno los campos de login con nombre de usuario con Ao1234 y con un password Ao1234AoAo
      When Yo le doy al boton Ingresar
      Then El sistema me muestra el menu del juego

    Scenario: Si le doy al boton cerrar sesion deberia volver a la pagina de inicio
      And Estoy en la pagina del menu del juego
      When Yo le doy al boton Cerrar sesion
      Then Regreso a la pagina de inicio