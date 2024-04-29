Feature: Register


  Scenario:Registro exitoso de una cuenta de usuario
    Given nombre de usuario no registrado luisito
    And un correo electronico no registrado luiscorreo
    And una contrasena luisito123
    And otra contrasena luisito123 coincidente con la anterior
    When me registro con esos campos
    Then la cuenta debe ser creada exitosamente


  Scenario:Registro fallido con un nombre ya registrado
    Given nombre de usuario registrado pedrito
    And un correo electronico no registrado pedritocorreo
    And una contrasena pedrito123
    And otra contrasena pedrito123 coincidente con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser creada
    And el sistema retorna usuario ya registrado

  Scenario:Registro fallido con un correo ya registrado
    Given nombre de usuario no registrado juanito
    And un correo electronico registrado juanitocorreo
    And una contrasena juanito123
    And otra contrasena juanito123 coincidente con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser creada
    And el sistema retorna correo ya registrado

  Scenario:Registro fallido con contrasenas no coincidentes
    Given nombre de usuario no registrado huguito
    And un correo electronico no registrado huguitoUserCorreo
    And una contrasena huguito123
    And otra contrasena pedrito123 que no coincide con la anterior
    When me registro con esos campos
    Then la cuenta no debe ser creada
    And el sistema retorna contrasenas no coincidentes
