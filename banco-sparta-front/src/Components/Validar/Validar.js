import React, { useEffect } from 'react'
import { keycloak } from '../../keycloak/keycloak';
import { validarCuenta } from '../../axios/axios';

export const Validar = () => {

    useEffect(() => {
      handleValidarCuenta()
    }, []);

    const handleValidarCuenta = async () => {
      const nuevaCuenta = await validarCuenta(keycloak.token, keycloak.tokenParsed.dni);
      const cuuValor = nuevaCuenta.data.cuu;
      const dniValor = nuevaCuenta.data.dni;
      sessionStorage.setItem('cuu', cuuValor);
      sessionStorage.setItem('dni', dniValor);
    }

  return (
    <div>Esto es para que te quede el cuu en el session storage</div>
  )
}
