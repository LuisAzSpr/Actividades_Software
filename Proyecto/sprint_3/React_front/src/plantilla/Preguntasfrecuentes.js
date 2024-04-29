import React from 'react';
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Container, Row, Col } from 'react-bootstrap';

const containerStyle = {
  backgroundImage: `url(${damasImage})`,
  margin: '0px',
  padding: '0px',
  backgroundSize: 'cover',
  backgroundPosition: 'center',
  minHeight: '100vh',
  maxWidth: "100%",
  color: '#000000'
};

const faqStyle = {
  backgroundColor: 'rgba(255, 255, 255, 0.8)', // Fondo semi-transparente blanco
  padding: '20px',
  borderRadius: '10px',
  boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)', // Sombra ligera
  marginBottom: '20px' // Margen inferior de 20px para separar los globos
};

export default function Preguntasfrecuentes() {
  return (
    <div className='container-fluid' style={containerStyle}>
      <Container>
        <h2 className="text-center mb-5">Preguntas Frecuentes</h2>
        <Row>
          <Col md={6}>
            <div style={faqStyle}>
              <h4>¿Cómo se juega a las damas?</h4>
              <p>Las damas es un juego de mesa para dos jugadores que se juega en un tablero cuadriculado. Cada jugador tiene un conjunto de fichas que se mueven diagonalmente hacia adelante en el tablero. El objetivo del juego es capturar todas las fichas del oponente o bloquearlas para que no puedan moverse.</p>
            </div>
          </Col>
          <Col md={6}>
            <div style={faqStyle}>
              <h4>¿Cuál es el objetivo del juego de damas?</h4>
              <p>El objetivo principal del juego de damas es capturar todas las fichas del oponente o bloquearlas de manera que no puedan moverse.</p>
            </div>
          </Col>
        </Row>
        <Row>
          <Col md={6}>
            <div style={faqStyle}>
              <h4>¿Cómo se mueven las fichas en el juego de damas?</h4>
              <p>Las fichas se mueven diagonalmente hacia adelante en el tablero, una casilla a la vez. Si una ficha alcanza el extremo opuesto del tablero, se convierte en una "dama" y puede moverse en diagonal en cualquier dirección.</p>
            </div>
          </Col>
          <Col md={6}>
            <div style={faqStyle}>
              <h4>¿Qué es una "dama" en el juego de damas?</h4>
              <p>Una "dama" es una ficha que ha alcanzado el extremo opuesto del tablero del oponente. Una vez que una ficha se convierte en una dama, tiene la capacidad de moverse en diagonal en cualquier dirección en lugar de solo hacia adelante.</p>
            </div>
          </Col>
        </Row>
        <Row>
          <Col md={6}>
            <div style={faqStyle}>
              <h4>¿Cuándo se captura una ficha en el juego de damas?</h4>
              <p>Una ficha se captura cuando el oponente salta sobre ella en diagonal y aterriza en una casilla vacía justo al lado de ella. Las fichas se pueden capturar tanto hacia adelante como hacia atrás, pero solo una casilla a la vez.</p>
            </div>
          </Col>
          <Col md={6}>
            <div style={faqStyle}>
              <h4>¿Cuándo termina una partida de damas?</h4>
              <p>Una partida de damas termina cuando uno de los jugadores captura todas las fichas del oponente o cuando ambos jugadores acuerdan un empate debido a que ninguno puede realizar movimientos legales.</p>
            </div>
          </Col>
        </Row>
        {/* Agrega más filas de preguntas y respuestas según sea necesario */}
      </Container>
    </div>
  );
}
