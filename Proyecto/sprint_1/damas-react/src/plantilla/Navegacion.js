import React from 'react'

export default function Navegacion() {
  return (
    <div className='conteiner'>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <a className="navbar-brand" href="/">DamasSprite.com</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <a className="nav-link active" aria-current="page" href="/">Inicio</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/">¿Comó jugar?</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/">Preguntas frecuentes</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link disabled" aria-disabled="true">Acerca de nosotros</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
  )
}
