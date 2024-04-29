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

const aboutStyle = {
  backgroundColor: 'rgba(255, 255, 255, 0.8)', // Fondo semi-transparente blanco
  padding: '20px',
  borderRadius: '10px',
  boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)', // Sombra ligera
  marginBottom: '20px' // Margen inferior de 20px para separar los globos
};

export default function Acercadenosotros() {
  return (
    <div className='container-fluid' style={containerStyle}>
      <Container>
        <h2 className="text-center mb-5">Acerca de nosotros</h2>
        <Row>
          <Col md={6}>
            <div style={aboutStyle}>
              <h4>Misión</h4>
              <p>Nuestro objetivo es proporcionar una aplicación web de damas accesible y emocionante, que permita a los jugadores debatir sus partidas en la misma maquina local o jugar con un computador. Nos esforzamos por ofrecer una experiencia de juego intuitiva y satisfactoria para jugadores de los niveles basicos e intermedios.</p>
            </div>
          </Col>
          <Col md={6}>
            <div style={aboutStyle}>
              <h4>Visión</h4>
              <p>Queremos crear una aplicacion web de damas local, que redefina la forma en que los jugadores disfrutan y mejoran sus habilidades en este clásico juego. Queremos ser el destino preferido para los amantes de las damas de todo el mundo, ofreciendo una experiencia de juego envolvente.</p>
            </div>
          </Col>
        </Row>
        <Row>
          <Col md={6}>
            <div style={aboutStyle}>
              <h4>Elevator Pitch</h4>
              <p>"DamasSprite te ofrece la oportunidad de jugar damas en locamente con otro jugador o poner a prueba tus habilidades contra una maquina. Con una interfaz intuitiva y dos modos de juego, somos la plataforma definitiva para los amantes de las damas".</p>
            </div>
          </Col>
          <Col md={6}>
            <div style={aboutStyle}>
              <h4>Valor</h4>
              <p>Nuestra aplicación web de damas ofrece entretenimiento a jugadores para los que inician en este mundo de las damas o los que ya tienen un nivel intermedio. Además, proporciona una oportunidad para la formación y la mejora de sus habilidades. También ofrece potencial para colaboraciones con educadores y organizadores de eventos relacionados con juegos de mesa.</p>
            </div>
          </Col>
        </Row>
        {/* Agrega más filas de información según sea necesario */}
      </Container>
    </div>
  );
}
