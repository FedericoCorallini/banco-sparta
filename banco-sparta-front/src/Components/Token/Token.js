import React, { useEffect, useState } from 'react'
import { getToken } from '../../axios/axios'
import { keycloak } from '../../keycloak/keycloak'
import { Box, Typography } from '@mui/material';

export const Token = () => {

  const [token, setToken] = useState({});

  useEffect(async () => {
    setToken((await getToken(keycloak.token)).data)
  }, []);

  return (
    <Box>
      <Typography component='h1' variant='h5' >Nuevo token numero: {token.numero}</Typography>
    </Box>   
  )
}
