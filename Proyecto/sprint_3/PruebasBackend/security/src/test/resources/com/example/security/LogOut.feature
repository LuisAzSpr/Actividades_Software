Feature: LogOut

  Scenario Outline: Cierre de sesion exitoso de una cuenta de usuario con sesion abierta
    Given un usuario logeado con nombre de usuario <valido>
    When cierro sesion con este usuario
    Then la sesion es cerrada invalidando el token de acceso

    Examples:
      |valido|
      |Pedro|