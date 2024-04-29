Feature: Login


  Scenario:Logeo exitoso de una cuenta de usuario
    Given nombre de usuario registrado pancracio
    And una contrasena pancracio123
    When inicio sesion con esos campos
    Then la cuenta debe ser logeada exitosamente

  Scenario:Logeo fallido de una cuenta de usuario con nombre no registrado
    Given nombre de usuario no registrado luisito
    And una contrasena luisito123
    When inicio sesion con esos campos
    Then la cuenta no debe ser logeada exitosamente
    Then el sistema retorna usuario no registrado

  Scenario: Logeo fallido de una contrasena incorrecta
    Given nombre de usuario registrado Maria
    And una contrasena maria1234
    When inicio sesion con esos campos
    Then la cuenta NO debe ser logeada exitosamente
    Then el sistema retorna contrasena incorrecta

