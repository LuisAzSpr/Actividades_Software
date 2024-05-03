import React, { useState, useEffect } from 'react';
import { getAuthToken } from '../componentes/axios_helper';
import '../styles/styles.css';
import axios from 'axios';

// Define el componente funcional Celda
const Celda = React.memo(({ rowIndex, colIndex, celda, handleClick }) => {
  return (
    <div
      className={`celda ${(rowIndex + colIndex) % 2 !== 0 ? 'blanco' : 'negro'} ${celda === "REINABLANCA" ? 'reina-blancas' : ''} ${celda === "REINANEGRA" ? 'reina-negras' : ''}`}
      onClick={() => handleClick(rowIndex, colIndex)}
    >
      {celda === "PEONBLANCO" && <div className="ficha jugador1"></div>}
      {celda === "PEONNEGRO" && <div className="ficha jugador2"></div>}
      {celda === "REINABLANCA" && <div className="reina jugador1">Q</div>}
      {celda === "REINANEGRA" && <div className="reina jugador2">Q</div>}
    </div>
  );
});

const ProbandoJuego = () => {
  const [tablero, setTablero] = useState([]);
  const [movimiento, setMovimiento] = useState([]);
  const [turno, setTurno] = useState(null);
  const [termino, setTermino] = useState(false)
  const [saltos, setSaltos] = useState([]);
  const [primerClick, setPrimerClick] = useState(true);
  const [user, setUser] = useState(null);
  const [ganador, setGanador] = useState(null);
  const [filaI, setfilaI] = useState(null);
  const [colI, setColI] = useState(null);
  const [filaF, setfilaF] = useState(null);
  const [colF, setColF] = useState(null);

  useEffect(() => {
    const obtenerTablero = async () => {
      const urlBase = "http://localhost:8080/api/v1/game-controller/iniciarPartida";
      const headers = {
        'Authorization': `Bearer ${getAuthToken()}`,
        'Content-Type': 'application/json'
      };
      try {
        const response = await axios.post(urlBase, null, { headers })
        if (response.status === 200) {
          setTablero(response.data.casillas);
          setUser(response.data.username)
          setTermino(response.data.termino)
          setSaltos(response.data.saltos)
          setGanador(response.data.ganador)
          setTurno(response.data.turno)
        }
      } catch (error) {
        console.error('Error al obtener el tablero:', error);
      }
    };

    obtenerTablero();
  }, []);

  const handleConfirmarMovimiento = async (rowIndex, colIndex) => {
    const urlBase = "http://localhost:8080/api/v1/game-controller/realizarMovimiento";
    const headers = {
      'Authorization': `Bearer ${getAuthToken()}`,
      'Content-Type': 'application/json'
    };
    if (filaI !== null && colI !== null) {
      try {
        const response = await axios.post(urlBase, { filaIni: filaI, colIni: colI, filaFin: rowIndex, colFin: colIndex }, { headers });
        if (response.status === 200) {
          setTablero(response.data.casillas);
          setUser(response.data.username);
          setTermino(response.data.termino);
          setSaltos(response.data.saltos);
          setGanador(response.data.ganador);
          setTurno(response.data.turno);
          setfilaI(null);
          setColI(null);
          setColF(colIndex);
          setfilaF(rowIndex);
        }
      } catch (error) {
        console.error("Error al obtener los datos en el segundo click ", error)
      }
    }

  }

  const handleClickFicha = async (rowIndex, colIndex) => {
    if (primerClick) {
      const urlBase = "http://localhost:8080/api/v1/game-controller/obtenerMovimientos";
      const headers = {
        'Authorization': `Bearer ${getAuthToken()}`,
        'Content-Type': 'application/json'
      };
      try {
        const response = await axios.post(urlBase, { fila: rowIndex, columna: colIndex }, { headers });
        if (response.status === 200) {
          setfilaI(rowIndex);
          setColI(colIndex);
          setPrimerClick(false);

        }
      } catch (error) {
        console.error('Error al obtener los movimientos válidos:', error);
      }
      setPrimerClick(false);
    } else {
      await handleConfirmarMovimiento(rowIndex, colIndex);
      setPrimerClick(true);
    }
  };

  const renderizarTablero = () => {
    if (!tablero) {
      return <div>{tablero}</div>;
    }
    return (
      <div className='contenedor-tablero'>
        <div className="tablero">
        {tablero.map((fila, rowIndex) => (
          <div key={rowIndex} className="fila">
            {fila.map((celda, colIndex) => (
              <Celda
                key={`${rowIndex}-${colIndex}`}
                rowIndex={rowIndex}
                colIndex={colIndex}
                celda={celda}
                handleClick={handleClickFicha}
              />
            ))}
          </div>
        ))}
       </div>
      </div>
      
    );
  };

  return (
    <div className="juego">
      {renderizarTablero()}
      <p>Estado del primer clic: {primerClick ? 'Hizo el primer clic' : 'Hizo el segundo clic'}</p>
      <div>
        <p>punto inicial: {filaI} , {colI} </p>
        <p>punto final: {filaF} , {colF}</p>
        <p>Turno: {turno}</p>
        <p>Salto: {saltos}</p>
        <p>Termino: {termino}</p>
        <p>Ganador: {ganador}</p>
        <p>Usuario: {user}</p>
      </div>
    </div>
  );
};

export default ProbandoJuego;
