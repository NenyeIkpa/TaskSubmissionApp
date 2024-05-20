import React, { useState } from 'react';
import { useLocalState } from '../util/useLocalStorage';
import { Navigate } from 'react-router-dom';
import api from '../service/apiservice';

const PrivateRoute = ({ children}) => {
    const [token] = useLocalState("", "token");
    const [isLoading, setIsLoading] = useState(true);
    const [isValid, setIsValid] = useState(null)

    if (token) {
        api(`/api/v1/auth/validate?token=${token}`, 'GET', token)
        .then((isValid) => {
            console.log(`isValid: ${isValid}`)
            setIsValid(isValid)
            setIsLoading(false)
        })
    } else {
        // setIsLoading(false)
        return <Navigate to="/login" />;
    }
    return isLoading ? (<div>Loading...</div>) : isValid ? (children) : (<Navigate to="/login" />); 
};

export default PrivateRoute;