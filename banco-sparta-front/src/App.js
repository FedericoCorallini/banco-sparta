import { Route, Routes } from 'react-router-dom';
import Saldo from './Components/Saldo/Saldo';
import Landing from './Components/Landing/Landing';
import { initKeycloak, keycloak } from './keycloak/keycloak';
import { useEffect } from 'react';
import { Movimientos } from './Components/Movimientos/Movimientos';
import { Token } from './Components/Token/Token';
import { Validar } from './Components/Validar/Validar';
import { Depositar } from './Components/Depositar/Depositar';
import { Crear } from './Components/Crear/Crear';
import { Debito } from './Components/Debito/Debito';
import { Home } from './Components/Home/Home';


function App() {

  useEffect(() => {
    inicializar();
  }, []);

  const inicializar = async () =>{
    await initKeycloak();
  }

  return (
    <Routes>
      {/* <Box sx={{ display: "flex", flexDirection: 'column', alignItems: 'center' }}>
        <Typography component="h1" variant='h5' mb={8}>BANCO SPARTA</Typography>
        <Button variant='contained'>Login</Button>
      </Box> */}
      <Route exact path='/' element={<Landing />}/>
      <Route exact path='/saldo' element={<Saldo />}/>
      <Route exact path='/movimientos' element={<Movimientos />}/>
      <Route exact path='/token' element={<Token />}/>
      <Route exact path='/validar' element={<Validar />} />
      <Route exact path='/depositar' element={<Depositar />} />
      <Route exact path='/crear' element={<Crear />} />
      <Route exact path='/debitar' element={<Debito />} />
      <Route exact path='/home' element={<Home />} />
    </Routes>
  );
}

export default App;
