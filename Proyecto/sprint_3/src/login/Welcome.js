import React from 'react'
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Link } from 'react-router-dom';

export default function Welcome() {

    const containerStyle = {
        backgroundImage: `url(${damasImage})`,
        margin: '0px',
        padding: '0px',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        minHeight: '100vh',
        maxWidth: "100%",
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: '#000000'
      };

  return (
    <div className='container' style={containerStyle}>
        <div className="container d-flex justify-content-center align-items-center  ">
            <div className="bg-light p-4 rounded bg-light bg-opacity-75" style={{width:"350px"}}>
                <div className="text-center mb-4">
                    <h3>Damas Sprite</h3>
                </div>

                <div className='text-center container mb-3'>
                    Te damos la bienvenida a DamasSprite. <br/><br/>
                    Te enviamos un correo electronico, por favor confirmalo!
                </div>
                <Link to = "/" className='btn btn-dark w-100 mr-3 mb-3'>Iniciar Sesion</Link>
            </div>
        </div>
    </div>
    
  );

}
