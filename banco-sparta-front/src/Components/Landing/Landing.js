import React, { useEffect, useState } from 'react'
import { Box, Button, Container } from '@mui/material'
import { Link } from 'react-router-dom'
import { keycloak } from '../../keycloak/keycloak'
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { crearCuenta, validarCuenta } from '../../axios/axios';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 550,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

function Landing() {
  
    const [open, setOpen] = useState(false);
    const [error, setError] = useState('');
    const handleClose = () => setOpen(false);
    const handleOpen = () => {
      setOpen(true);
    }

    const handleOnClickLogout = () => {
      keycloak.logout({redirectUri: 'http://localhost:3000/'});
    }

    const handleCrear = async () => {
      await crearCuenta(keycloak.token)
      .then((response) => {
        setError('Cuenta creada: ' + response.data.cuu)
      }).catch((error) => {
        setError(error.response.data.message)
      })
      handleOpen()
    }

    const handleValidarCuenta = async () => {
      const nuevaCuenta = await validarCuenta(keycloak.token, keycloak.tokenParsed.dni);
      const cuuValor = nuevaCuenta.data.cuu;
      const dniValor = nuevaCuenta.data.dni;
      sessionStorage.setItem('cuu', cuuValor);
      sessionStorage.setItem('dni', dniValor);
      setError("Funcion para cargar los datos en el session storage")
      handleOpen()
    }

  return (
    <Container maxWidth="sm" sx={{ mt: '25vh' }}>

    <Box sx={{display: 'grid', gap: 2}}>

      
      <Button variant='contained' onClick={handleCrear}>Crear Cuenta</Button>
      

     
      <Button variant='contained' onClick={handleValidarCuenta}>Get Cuenta</Button>
      
{/* 
      <Link to={'/saldo'}>
        <Button variant='contained'>Get Saldo</Button>
      </Link>

      <Link to={'/depositar'}>
        <Button variant='contained'>Cargar Saldo</Button>
      </Link>

      <Link to={'/movimientos'}>
        <Button variant='contained'>Get Movimientos</Button>
      </Link>

      <Link to={'/token'}>
        <Button variant='contained'>Get Token</Button>
      </Link>
      
      <Link to={'/debitar'}>
        <Button variant='contained'>Solicitar debito</Button>
      </Link> */}

      <Link to={'/home'}>
        <Button variant='contained' style={{ width: '100%'}}>Home</Button>
      </Link>

      <Button variant='contained' onClick={() => handleOnClickLogout()}>Logout</Button>
    </Box>

      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            {error}
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            {error}
          </Typography>
        </Box>
      </Modal>


    </Container>
  )
}

export default Landing