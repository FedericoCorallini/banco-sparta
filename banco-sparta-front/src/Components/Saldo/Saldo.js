import { Box, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { getSaldo } from '../../axios/axios';
import { keycloak } from '../../keycloak/keycloak';

function Saldo() {

  const [saldo, setSaldo] = useState({});

  useEffect(() => {
    handleSaldo();
  }, []);

  const handleSaldo = async () => {
    setSaldo((await getSaldo(keycloak.token)).data)
  }

  return (
    <Box>
        <Typography component='h3' variant='h6' >CUU: {saldo.cuu}</Typography>
        <Typography component='h3' variant='h6' >Saldo: ${saldo.saldo}</Typography>
    </Box>
  )
}

export default Saldo