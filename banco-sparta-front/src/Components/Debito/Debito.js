import React, { useEffect, useState } from 'react'
import { apiDebitar, depositarSaldo, getToken } from '../../axios/axios';
import { keycloak } from '../../keycloak/keycloak';
import { Box, Button, TextField, Container } from '@mui/material';

export const Debito = ( { effect }) => {

    const [monto, setMonto] = useState('');
    const [motivo, setMotivo] = useState('');
    const [cuu, setCuu] = useState('');
    const [nroOperacion, setNroOperacion] = useState('');
    const [token, setToken] = useState('');

    const onGetToken = async () => {
        setToken((await getToken(keycloak.token)).data.numero)
    };
  
    const body = {
        'cuu_origen': `${sessionStorage.getItem('cuu')}`,
        'cuu_destino': `${cuu}`,
        'monto': `${monto}`,
        'motivo': `${motivo}`,
        'token': `${token}`,
        'numero_operacion': `${nroOperacion}`
    }
    const handleClick =  async () =>{  
      await apiDebitar(keycloak.token, body);
      setCuu('');
      setMonto('');
      setMotivo('');
      setToken('');
      setNroOperacion('');
      effect(true);
    }


  return (
    <>
      <Container maxWidth="lg" sx={{ mt: '10vh' }}>

      <Box sx={{display: 'grid', gap: 2, width: '300px' }}>
        < TextField 
        id="Standard-basic" 
        label="Ingresar CUU destino" 
        variant="standard" value={cuu} 
        onChange={(e) => setCuu(e.target.value)}
        />
        < TextField 
        id="Standard-basic" 
        label="Ingresar monto a transferir" 
        variant="standard" value={monto} 
        onChange={(e) => setMonto(e.target.value)}
        />
        < TextField 
        id="Standard-basic" 
        label="Ingresar motivo" 
        variant="standard" value={motivo} 
        onChange={(e) => setMotivo(e.target.value)}
        />
        < TextField 
        id="Standard-basic" 
        label="Ingresar numero de operacion" 
        variant="standard" value={nroOperacion} 
        onChange={(e) => setNroOperacion(e.target.value)}
        />
        < TextField 
        id="Standard-basic" 
        label="Ingresar token" 
        variant="standard" value={token} 
        />
        <Button variant='contained' color='primary' onClick={onGetToken}>Obtener token</Button>
        <Button variant='contained' onClick={handleClick}>Transferir</Button>
      </Box>
     
      <Box sx={{mt: 10, mb: 2}}>
        
      </Box>

    </Container>
        
    </>
  )
}

