
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./login/Login";
//import Login2 from "./login/Login2";
import Navegacion from "./plantilla/Navegacion";
import RegistrarUsuario from "./usuarios/RegistrarUsuario";
import Welcome from "./login/Welcome";

function App() {
  return (
    <div className="conteiner">
      <BrowserRouter>
        <Navegacion/>
        <Routes>
          <Route exact path="/" element={<Login/>}/>
          <Route exact path="/registrar" element={<RegistrarUsuario/>}/>
          <Route exact path="/registroExitoso" element={<Welcome/>}/>
        </Routes>
      </BrowserRouter>
    </div>
    
  );
}

export default App;
