import React, { useState } from 'react';
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios'; 
import { setAuthToken } from '../componentes/axios_helper';

export default function Login({ setIsAuthenticated }) {
    let navegacion = useNavigate();

    const[usuario, getUsuario] = useState({
        username: "",
        password: ""
    })

    const{username, password} = usuario

    const onImputChange = (e) => {
        getUsuario({...usuario, [e.target.name]: e.target.value})
    }

    const onSubmit = async (e) => {
        e.preventDefault(); 
        const urlBase = "http://localhost:8080/api/v1/auth/authenticate";

        try {
            const response = await axios.post(urlBase, usuario);

            if (response.status === 200) {
                setAuthToken(response.data.token)
                setIsAuthenticated(true);
                navegacion("/Menu");
            }
        } catch (error) {
            alert("Las Credenciales son incorrectas");
        }
    };


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

                <form onSubmit={(e) => onSubmit(e)}>
                    <div className="mb-3" tabIndex="-1">
                        <label htmlFor="exampleInputEmail1" className="form-label">Usuario</label>
                        <input type="text" className="form-control" required={true} id="username" name='username' value={username} onChange={(e) => onImputChange(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="exampleInputPassword1" className="form-label">Contraseña</label>
                        <input type="password" className="form-control" required={true} name='password' id="password" value={password} onChange={(e) => onImputChange(e)}/>
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
