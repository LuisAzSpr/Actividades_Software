import React, { useState, useEffect } from 'react';
import { getAuthToken } from '../componentes/axios_helper';
import '../styles/styles.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

// Define el componente funcional Celda
const Celda = React.memo(({ rowIndex, colIndex, celda, handleClick, movimiento }) => {
  // Función para verificar si la celda está en la lista de movimientos
  const esMovimientoValido = () => {
    for (let i = 0; i < movimiento.length; i++) {
      if (movimiento[i][0] === rowIndex && movimiento[i][1] === colIndex) {
        return true;
      }
    }
    return false;
  };

  return (
    <div
      className={`celda ${(rowIndex + colIndex) % 2 !== 0 ? 'blanco' : 'negro'} ${celda === "REINABLANCA" ? 'reina-blancas' : ''} ${celda === "REINANEGRA" ? 'reina-negras' : ''} ${esMovimientoValido() ? 'movimiento-valido' : ''}`}
      onClick={() => handleClick(rowIndex, colIndex)}
    >
      {celda === "PEONBLANCO" && <div className="ficha jugador1"></div>}
      {celda === "PEONNEGRO" && <div className="ficha jugador2"></div>}
      {celda === "REINABLANCA" && <div className="reina jugador1">Q</div>}
      {celda === "REINANEGRA" && <div className="reina jugador2">Q</div>}
    </div>
  );
});

const JuegoJava = () => {
  let navegacion = useNavigate();
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
  const [mostrarMensajeTermino, setMostrarMensajeTermino] = useState(false);


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

  useEffect(() => {
    if (termino === 1) {
      setMostrarMensajeTermino(true);
      if(turno === "blancas") setGanador("negras");
      else setGanador("blancas");
    }
  }, [termino]);

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
          setSaltos([]);
          setMovimiento([]);
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

          // Si hay salto entonces se sombre, de lo contrario se sombrea un movimiento basico
          if(saltos.length > 0){
            //setMovimiento(saltos);
            const resultado = saltos.map(subarray => {
              const ultimosDos = subarray.slice(-2);
              if(subarray[0] === rowIndex && subarray[1] === colIndex){
                if (ultimosDos[0] !== 0 || ultimosDos[1] !== 0) {
                return ultimosDos;
            }
            }
            }).filter(Boolean);
            setMovimiento(resultado);
          }else {// sombreamos el movimiento basico
            const resultado = response.data.map(subarray => {
              const ultimosDos = subarray.slice(-2);
              if (ultimosDos[0] !== 0 || ultimosDos[1] !== 0) {
                  return ultimosDos;
              }
            }).filter(Boolean);
            setMovimiento(resultado);
          }
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
        
        <ul className="list-group mx-5">
          <li className="list-group-item">Usuario: {user}</li>

          <li className="list-group-item list-group-item-primary">Turno: {turno}</li>

          {mostrarMensajeTermino && (
          <div className="mensaje-termino alert alert-warning">
            <p className="mb-0">La partida ha terminado</p>
            <p>el ganador es: {ganador}</p>
            <button className="btn btn-primary mt-2" onClick={() => navegacion("/Menu")}>Volver al menú</button>
          </div>
          )}
        </ul>
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
              movimiento={movimiento}
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
    </div>
  );
};

export default JuegoJava;
