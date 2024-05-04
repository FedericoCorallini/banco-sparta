import React, { useEffect, useState } from 'react'
import { depositarSaldo } from '../../axios/axios';
import { keycloak } from '../../keycloak/keycloak';
import { Box, Button, TextField, Container } from '@mui/material';
import { Movimientos } from '../Movimientos/Movimientos';


export const Depositar = ( { effect }) => {
  
  const [monto, setMonto] = useState('');
  
  const handleClick = async () =>{
    await depositarSaldo(keycloak.token, monto)
    setMonto('');
    effect(true);
  }


  return (
    <Container maxWidth="lg" sx={{ mt: '10vh' }}>
      <Box sx={{display: 'grid', gap: 2, width: '300px' }}>
        < TextField 
        id="Standard-basic" 
        label="Ingresar monto a depositar" 
        variant="standard" value={monto} 
        onChange={(e) => setMonto(e.target.value)}
        />
        <Button variant='contained' onClick={handleClick}>Depositar</Button>
      </Box>
    </Container>
  )
}

