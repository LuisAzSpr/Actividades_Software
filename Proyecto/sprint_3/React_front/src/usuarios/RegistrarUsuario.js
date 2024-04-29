import React, { useState } from 'react';
import axios from 'axios';
import damasImage from '../imagenes/wallpaperbetter.jpg';
import { Link, useNavigate } from 'react-router-dom';

export default function RegistrarUsuario() {
    let navegacion = useNavigate();
    
    const [usuario, setUsuario] = useState({
        email:"",
        password:"",
        username: "",
        confirmPassword: ""
    })

    const { username, email, password, confirmPassword } = usuario;
    

    const isUsernameValid = (username) => {
        return username.length >= 3;
    };

    const isPasswordSecure = (password) => {
        const hasUpperCase = /[A-Z]/.test(password);
        const hasNumber = /[0-9]/.test(password);
        const isLongEnough = password.length >= 8;
        return hasUpperCase && hasNumber && isLongEnough;
    };

    const isEmailValid = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const onInputChange = (e) => {
        const { name, value } = e.target;
        setUsuario({ ...usuario, [name]: value });
    }

    const onSubmit = async (e) =>{
        e.preventDefault();

        if (isUsernameValid && isPasswordSecure && isEmailValid && password !== confirmPassword) {
            return;
        }

        const urlBase = "http://localhost:8080/api/v1/auth/register";
        
        try {
            const response = await axios.post(urlBase, usuario);
            if (response.status === 200) {
                navegacion('/registroExitoso');
            }
            else{
                alert("Error: ", response.status)
            }
        } catch (error) {
            if (error.response && (error.response.status === 409 || error.response.status === 403)) {
                alert('El usuario ya existe. Por favor, elija otro nombre de usuario.');
            } else {
                console.error('Error al intentar agregar usuario:', error);
                alert('Hubo un problema al intentar agregar el usuario. Por favor, inténtelo de nuevo.', error);
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
            <div className="bg-light p-4 rounded bg-light bg-opacity-75" style={{width:"350px"}}>
                <div className="text-center mb-4">
                    <h3>Registro</h3>
                </div>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className="mb-3">
                        <label htmlFor="nombre" className="form-label">Nombre de Usuario</label>
                        <input type="text" className="form-control" id="nombre" name='username' required={true} value={username} onChange={(e)=>onInputChange(e)}/>
                        {username.length > 0 && !isUsernameValid(username) && (
                            <div className="text-danger">El nombre de usuario debe tener al menos 3 caracteres</div>
                             )}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Correo electronico</label>
                        <input type="email" className="form-control" id="email" aria-describedby="emailHelp" required={true} name='email' value={email} onChange={(e)=>onInputChange(e)}/>
                        {email.length > 0 && !isEmailValid(email) && (
                            <div className="text-danger">Ingrese un correo electrónico válido</div>
                        )}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Elija una contraseña</label>
                        <input type="password" className="form-control" id="password" name='password' required={true} value={password} onChange={(e)=>onInputChange(e)}/>
                        {password.length > 0 && !isPasswordSecure(password) && (
                                <div className="text-danger">
                                    La contraseña debe tener al menos una mayúscula, un número y ser de al menos 8 caracteres de longitud
                                </div>
                         )}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="passwordConfirmation" className="form-label">Repita la contraseña</label>
                        <input type="password" className="form-control" id="confirmPassword" name='confirmPassword' value={confirmPassword} onChange={(e)=>onInputChange(e)} required={true}/>
                        {confirmPassword !== password && (
                                <div className="text-danger">
                                    Las contraseñas deben de ser iguales
                                </div>
                         )}
                    </div>
                    <div className="form-check">
                        <input className="form-check-input bg-secondary" style={{borderColor: "gray"}} type="checkbox" value="" id="terminosYCondiciones" name='aceptarterminosycondiciones'/>
                            <label className="form-check-label mb-3" htmlFor="terminosycondiciones">
                                <a href="/" className="link-secondary 
                    link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Acepto los terminos y condiciones</a>
                            </label>
                    </div>
                    <button type="submit" className="btn btn-dark w-100 mb-2" disabled={isEmailValid}>Registrarse</button>

                    <Link to = "/" className='btn btn-dark w-100 mb-3'>Iniciar Sesion</Link>

                </form>
            </div>
        </div>
    </div>
    
  );

}
