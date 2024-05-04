
import React, { useEffect, useState } from 'react'
import { keycloak } from '../../keycloak/keycloak';
import { getMovimientos } from '../../axios/axios';
import { MDBDataTable } from 'mdbreact';
import 'mdbreact/dist/css/mdb.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import { Box, Container, Typography } from '@mui/material';
import Saldo from '../Saldo/Saldo';


export const Movimientos = ({ effect }) => {

    const [movimientos, setMovimientos] = useState([]);

    useEffect(() => {
      handleMovimientos();
    }, [effect]);

    const handleMovimientos = async () => {
      setMovimientos((await getMovimientos(keycloak.token)).data)
    }
      
    const data = {
        columns: [
            {label: 'Fecha', field: 'fecha'},
            {label: 'Monto', field: 'monto'},
            {label: 'Motivo', field: 'motivo'},
            // {label: 'Comprobante', field: 'comprobante'}
          ],
        rows: [],
    };

    movimientos.forEach(movimiento => {
        data.rows.push({
            fecha: JSON.stringify(movimiento.fecha).slice(1).split('T').at(0) + ' - ' + JSON.stringify(movimiento.fecha).split('T').at(1).split('.').at(0), 
            monto: movimiento.monto,
            motivo: movimiento.motivo,
            // comprobante: movimiento.comprobante,
        })
    });

  return (
    <Container maxWidth="lg" sx={{ mt: '12vh' }}>
    <Box sx={{ textAlign:'left', mb: 2}}>
      <Saldo></Saldo>
    </Box>

    <MDBDataTable 
    
    data={data}
    striped
    entriesOptions={[5, 10, 20, 50]} 
    entries={5} 
    small
    noBottomColumns={true}
    selectRows={true}
    >
    </MDBDataTable>
    </Container>
  )
}
