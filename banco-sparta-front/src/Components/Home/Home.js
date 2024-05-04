import { Box, Grid, Typography, Container } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { Debito } from '../Debito/Debito'
import { Depositar } from '../Depositar/Depositar'
import { keycloak } from '../../keycloak/keycloak';
import { getMovimientos, getSaldo } from '../../axios/axios';
import { MDBDataTable } from 'mdbreact';
import 'mdbreact/dist/css/mdb.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

export const Home = () => {

    const [effect, setEffect] = useState(false)
    const [movimientos, setMovimientos] = useState([]);
    const [saldo, setSaldo] = useState({});

    useEffect(() => {
      handleMovimientos();
      setEffect(false)
    }, [effect]);

    const handleMovimientos = async () => {
      setMovimientos((await getMovimientos(keycloak.token)).data)
      setSaldo((await getSaldo(keycloak.token)).data)
    }
      
    const data = {
        columns: [
            {label: 'Fecha', field: 'fecha'},
            {label: 'Monto', field: 'monto'},
            {label: 'Motivo', field: 'motivo'},
            // {label: 'Comprobante', field: 'comprobante'}
          ],
        rows: []
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
    <>
    <Grid container spacing={0}>      
        <Grid items xs={3}>
            <Debito effect={setEffect}/>
            <Depositar effect={setEffect}/>
        </Grid>

        <Grid items xs={9}>
        <Container maxWidth="lg" sx={{ mt: '12vh' }}>         
          <Box sx={{ textAlign:'left', mb: 2}}>
          <Box>
            <Typography component='h3' variant='h6' >CUU: {saldo.cuu}</Typography>
            <Typography component='h3' variant='h6' >Saldo: ${saldo.saldo}</Typography>
          </Box>
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
        </Grid>
    </Grid>
    
    </>
  )
}
