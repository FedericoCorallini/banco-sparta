import React, { useEffect, useState } from 'react'
import { crearCuenta } from '../../axios/axios'
import { keycloak } from '../../keycloak/keycloak'
import { Typography } from '@mui/material';

export const Crear = () => {

    const [error, setError] = useState('');

    useEffect(() => {
      handleCrear();
    }, []);

    const handleCrear = async () => {
      await crearCuenta(keycloak.token)
      .then((response) => {
        setError('Cuenta creada: ' + response.data.cuu)
      }).catch((error) => {
        setError(error.response.data.message)
      })
    }

  return (
    <Typography>{error}</Typography>
  )
}



