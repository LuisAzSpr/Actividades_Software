Feature: Login

  Scenario Outline: Inicio de sesión exitoso de una cuenta de usuario
    Given un nombre de usuario <valido> registrado
    And una contrasena <valida> correspondiente al usuario
    When inicio sesion con esos campos
    Then el sistema retorna un token de sesion valido
    And la cuenta debe ser logeada exitosamente

    Examples:
      |valido|valida|
      |Pedro|Pedro123|

  Scenario Outline: Inicio de sesión fallido con un nombre de usuario inexistente
    Given un nombre de usuario <valido> no registrado
    And una contrasena <valida>
    When inicio sesion con esos campos
    Then la cuenta no debe ser logeada
    And el sistema muestra un mensaje de usuario inexistente

    Examples:
      |valido|valida|
      |Guillermo|Guillermo123|
      |Hugo|HugoEstudiante123|
      |Luis|LuisEstudiante57|


  Scenario Outline:Logeo fallido de una cuenta de usuario con una contrasena incorrecta
    Given un nombre de usuario <valido> registrado
    And una contrasena <valida>
    When inicio sesion con esos campos
    Then la cuenta no debe ser logeada
    And el sistema muestra un mensaje de contrasenas no coinciden

    Examples:
      |valido|valida|
      |Pedro|Pedro1234|
      |Pedro|Pedrito56|
      |Pedro|Pedrito63|