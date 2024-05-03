Feature: Alternar Turnos


  Scenario Outline: Alternar entre movimientos simples
    Given un tablero en juego
    And turno de <jugador>
    And el jugador tiene una <ficha> en <inicial>
    When <jugador> realiza un movimiento simple a <final>
    Then cambia de turno

    Examples:
      |jugador|ficha|inicial|final|
      |blanco|dama  |G3|E5        |
      |blanco|peon  |B2|C3        |
      |negro |dama  |G7|F6        |
      |negro |peon  |B6|C5        |

  Scenario Outline: Capturas en cadena
    Given un tablero en juego
    And turno de <jugador>
    And el jugador tiene una <ficha> en <inicial>
    And el rival tiene una ficha cualquiera en <rival1>
    And el rival tiene una ficha extra cualquiera en <rival2>
    When <jugador> captura <rival1> en <final>
    And tiene otra captura disponible desde <final>
    Then no cambia de turno

    Examples:
      |jugador|ficha|inicial|rival1|rival2|final|
      |blanco|peon|B2      |C3|   E5       |D4   |
      |negro |peon|B6       |C5|     C3    |D4   |
      |negro |dama|G7       |F6|    D4     |E5   |
      |blanco|dama|G3       |E5|   B4       |D6   |