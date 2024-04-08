import React from 'react';
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Link } from 'react-router-dom';

export default function Login() {


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
            {/* Utilizamos vh-100 para ocupar toda la altura de la ventana */}
            <div className="bg-light p-4 rounded bg-light bg-opacity-75" style={{width:"350px"}}>
                {/* Establecemos un fondo claro, padding y bordes redondeados */}
                <div className="text-center mb-4">
                    <h3>Damas Sprite</h3>
                </div>

                <form>
                    <div className="mb-3" tabIndex="-1">
                        <label htmlFor="exampleInputEmail1" className="form-label">Usuario</label>
                        <input type="text" className="form-control" id="exampleInputEmail1" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="exampleInputPassword1" className="form-label">Contraseña</label>
                        <input type="password" className="form-control" id="exampleInputPassword1"/>
                    </div>
                    <button type="submit" className="btn btn-dark w-100 mb-2">Ingresar</button>
                    <Link to = "/registrar" className='btn btn-dark w-100 mb-3'>Registrarse</Link>
                    <p className='text-center'><a href="/" className="link-secondary 
                    link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Olvidé mi contraseña</a></p>

                </form>
            </div>
        </div>
    </div>
    
  );
}
