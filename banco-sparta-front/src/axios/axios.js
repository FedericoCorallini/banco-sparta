import axios from "axios"

const BASE_URL = 'http://localhost:8080/api'

export const getSaldo = async (token) => {
    
    const config = {
        method: `get`,
        url: BASE_URL + '/cuenta/' + sessionStorage.getItem('cuu'),
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwt') }
    } 

    return await axios(config);
}

export const getMovimientos = async (token) => {
    
    const config = {
        method: `get`,
        url: BASE_URL + '/cuenta/' + sessionStorage.getItem('cuu') + '/movimientos',
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwt') }
    } 

    return await axios(config);
}

export const getToken = async (token) => {
    
    const config = {
        method: `post`,
        url: BASE_URL + '/token/generar-token/' + sessionStorage.getItem('cuu') ,
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwt') }
    } 

    return await axios(config);
}

export const crearCuenta = async (token) => {
    
    const config = {
        method: `post`,
        url: BASE_URL + '/cuenta/crear',
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwt') }
    } 

    return await axios(config);
}

export const validarCuenta = async (token, dni) => {
    
    const config = {
        method: `get`,
        url: BASE_URL + '/cliente/verificar/' + dni,
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwt') }
    } 

    return await axios(config);
}

export const depositarSaldo = async (token, monto) => {
    
    const config = {
        method: `put`,
        url: BASE_URL + '/cuenta/' + sessionStorage.getItem('cuu') + '/' + monto,
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' + sessionStorage.getItem('jwt') }
    } 

    return await axios(config);
}

export const apiDebitar = async ( token, data ) => {

    const config = {
        method: `post`,
        url: BASE_URL + '/debito',
        data: data,
        headers: { 
            'Access-Control-Allow-Origin': '*', 
            'Authorization': 'Bearer ' +  sessionStorage.getItem('jwt')
        }
    }

    return await axios(config);
}