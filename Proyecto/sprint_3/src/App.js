import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Login from "./login/Login";
import Navegacion from "./plantilla/Navegacion";
import RegistrarUsuario from "./usuarios/RegistrarUsuario";
import Welcome from "./login/Welcome";
import { useState } from "react";
import MenuGame from "./login/MenuGame";
import Preguntasfrecuentes from "./plantilla/Preguntasfrecuentes";
import Acercadenosotros from "./plantilla/Acercadenosotros";
import JuegoJava from "./Juego/JuegoJava";
import ProbandoJuego from "./Juego/ProbandoJuego";


function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  return (
    <div className="conteiner">
      <BrowserRouter>
        <Navegacion isAuthenticated={isAuthenticated}/>
        <Routes>
          <Route exact path="/" element={<Login setIsAuthenticated={setIsAuthenticated}/>}/>
          <Route exact path="/registrar" element={<RegistrarUsuario/>}/>
          <Route exact path="/registroExitoso" element={<Welcome/>}/>
          <Route exact path="/Preguntasfrecuentes" element={<Preguntasfrecuentes/>}/>
          <Route exact path="/Acercadenosotros" element={<Acercadenosotros/>}/>
          <Route exact path="/Game" element={isAuthenticated ? <JuegoJava/> : <Navigate to="/Menu" replace />}/>
          <Route exact path="/Menu" element={isAuthenticated ? <MenuGame setIsAuthenticated={setIsAuthenticated}/> : <Navigate to="/" replace />}/>
        </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;