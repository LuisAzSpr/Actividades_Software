import React, { useState } from 'react';
import axios from 'axios';
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Link, useNavigate } from 'react-router-dom';

export default function RegistrarUsuario() {
    let navegacion = useNavigate();
    
    const [usuario, setUsuario] = useState({
        email:"",
        password:"",
        role: "",
        username: ""
    })

    const{nombre, email, password} = usuario

    const onInputChange = (e) => {
        setUsuario({...usuario, [e.target.name]: e.target.value})
    }

    const onSubmit = async (e) =>{
        e.preventDefault();
        const urlBase = "localhost:8080/api/v1/auth/register";
        
        try {
            const response = await axios.post(urlBase, usuario);
            if (response.status === 200) {
                // Usuario agregado exitosamente
                navegacion('/registroExitoso');
            }
        } catch (error) {
            if (error.response && error.response.status === 409) {
                // Error: Usuario ya existe
                alert('El usuario ya existe. Por favor, elija otro nombre de usuario.');
            } else {
                // Otro tipo de error
                console.error('Error al intentar agregar usuario:', error);
                alert('Hubo un problema al intentar agregar el usuario. Por favor, inténtelo de nuevo.');
            }
        }
    }

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
                    <h3>Registro</h3>
                </div>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className="mb-3">
                        <label htmlFor="nombre" className="form-label">Nombre de Usuario</label>
                        <input type="text" className="form-control" id="nombre" name='username' required={true} value={nombre} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Correo electronico</label>
                        <input type="email" className="form-control" id="email" aria-describedby="emailHelp" name='email' value={email} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Elija una contraseña</label>
                        <input type="password" className="form-control" id="password" name='password' value={password} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="passwordconfirmacion" className="form-label">Repita la contraseña</label>
                        <input type="password" className="form-control" id="passwordconfirmacion" name='passwordconfirmacion' />
                    </div>
                    <div className="form-check">
                        <input className="form-check-input bg-secondary" style={{borderColor: "gray"}} type="checkbox" value="" id="flexCheckDefault" name='aceptarterminosycondiciones'/>
                            <label className="form-check-label mb-3" htmlFor="terminosycondiciones">
                                <a href="/" className="link-secondary 
                    link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Acepto los terminos y condiciones</a>
                            </label>
                    </div>
                    <button type="submit" className="btn btn-dark w-100 mb-2">Registrarse</button>

                    <Link to = "/" className='btn btn-dark w-100 mb-3'>Iniciar Sesion</Link>

                </form>
            </div>
        </div>
    </div>
    
  );

}
