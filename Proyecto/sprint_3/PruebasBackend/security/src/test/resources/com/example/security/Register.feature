Feature: Register

  Scenario Outline:Registro exitoso de una cuenta de usuario
    Given un nombre de usuario <valido> no registrado
    And una direccion de correo <existente> no registrado
    And una contrasena <valida>
    And una contrasena de <confirmacion> que coincida con la anterior
    When me registro con esos campos
    Then la cuenta debe ser registrada exitosamente
    And el sistema retorna un token de sesion valido

    Examples:
      |valido    |valida     |confirmacion|existente        |
      |LuisSpr   |luisSpr123 |luisSpr123  |luis123@gmail.com|
      |MariaGomez|123Maria456|123Maria456 |maria@gmail.com  |
      |RaulPerez |RPerez789  |RPerez789   |raulp@gmail.com  |

  Scenario Outline: Registro fallido de una cuenta de usuario con un nombre de usuario registrado
    Given un nombre de usuario <valido> registrado
    And una direccion de correo <existente> no registrado
    And una contrasena <valida>
    And una contrasena de <confirmacion> que coincida con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser registrada
    And el sistema muestra un mensaje de nombre ya en uso

    Examples:
      |valido|valida  |confirmacion|existente          |
      |Pedro |Pedro123|Pedro123    |pedroluis@gmail.com|
      |Pedro |Pedro456|Pedro456    |pedrojuan@gmail.com|
      |Pedro |soyUnPedro123|soyUnPedro123    |pedro123@gmail.com|

  Scenario Outline: Registro fallido de una cuenta de usuario con un correo ya registrado
    Given un nombre de usuario <valido> no registrado
    And una direccion de correo <existente> registrado
    And una contrasena <valida>
    And una contrasena de <confirmacion> que coincida con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser registrada
    And el sistema muestra un mensaje de correo ya en uso

    Examples:
      |valido |valida    |confirmacion|existente      |
      |Pedrito|Pedrito123|Pedrito123  |Pedro@gmail.com|
      |Luisito|Luisito123|Luisito123  |Pedro@gmail.com|
      |Hugo|soyUnHugo53|soyUnHugo53  |Pedro@gmail.com|

  Scenario Outline: Registro fallido de una cuenta de usuario con contrasenas no coincidentes
    Given un nombre de usuario <valido> no registrado
    And una direccion de correo <existente> no registrado
    And una contrasena <valida>
    And una contrasena de <confirmacion> que no coincida con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser registrada
    And el sistema muestra un mensaje de contrasenas no coinciden

    Examples:
      |valido |valida    |confirmacion|existente          |
      |Pedrito|Pedrito123|Pedrito1234 |pedroluis@gmail.com|
      |Luisito|Luisito42|Luisito1234  |luisito@gmail.com|
      |Hugo|soyUnHugo13|soyUnHugoestudiand43  |hugoEstudiante@gmail.com|


  Scenario Outline: Registro fallido de una cuenta de usuario con una contrasena no valida
    Given un nombre de usuario <valido> no registrado
    And una direccion de correo <existente> no registrado
    And una contrasena no valida <no valida>
    And una contrasena de <confirmacion> que coincida con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser registrada
    And el sistema muestra un mensaje de contrasena insegura

    Examples:
      |valido|no valida  |confirmacion|existente          |
      |Juan |Juan123|Juan123    |juanito@gmail.com|
      |Luis |luisito42|luisito42    |luis@gmail.com|
      |Hugo |HUGO   |HUGO       |huguito123@gmail.com|