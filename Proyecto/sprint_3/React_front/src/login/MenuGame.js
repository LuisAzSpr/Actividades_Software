import React from 'react';
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Link, useNavigate } from 'react-router-dom';
import axios from "axios";
import { getAuthToken, setAuthToken } from '../componentes/axios_helper';

const MenuGame = ({ setIsAuthenticated }) => {
let navegacion = useNavigate();
  const containerStyle = {
    backgroundImage: `url(${damasImage})`,
    margin: '0px',
    padding: '0px',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    minHeight: '100vh',
    maxWidth: '100%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#000000'
  };

  const handleLogout = async () => {
    try {
      const UrlBase = "http://localhost:8080/api/v1/demo-controller/logout"
      const headers = {
        'Authorization': `Bearer ${getAuthToken()}`, 
        'Content-Type': 'application/json' 
      };
      const response = await axios.post(UrlBase, null, {headers});
      

      if(response.status === 200){
        setIsAuthenticated(false);
        setAuthToken(null);
        navegacion("/");
      }
  
    } catch (error) {
      console.error('Error al cerrar sesión:', error);
    }
  };

  return (
    <div className="container" style={containerStyle}>
      <div className="container d-flex justify-content-center align-items-center">
        <div className="bg-light p-4 rounded bg-light bg-opacity-75" style={{ width: '350px' }}>
          <div className="text-center mb-4">
            <h3>Damas Sprite</h3>
          </div>
          <Link to="/Game" className="btn btn-dark w-100 mb-3">Jugar 1 vs 1</Link>
          <Link className="btn btn-dark w-100 mb-3" onClick={handleLogout}>Cerrar Sesión</Link>
        </div>
      </div>
    </div>
  );
};

export default MenuGame;
