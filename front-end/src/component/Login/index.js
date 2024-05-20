import React, { useEffect, useState } from 'react';
import { useLocalState } from '../../util/useLocalStorage';
import { Button, Container, Form } from 'react-bootstrap';

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [token, setToken] = useLocalState("", "token");

    const sendLoginRequest = () => {
        console.log(`username:${username}, password: ${password}`)
        const reqBody = {
            username: username,
            password: password
          }
          if (!token) {
            fetch("/api/v1/auth/login", {
                headers: {
                  "Content-Type": "application/json"
                },
                method: "post",
                body: JSON.stringify(reqBody)
              })
              .then((response) => {
                  if (response.status === 200) {
                      return Promise.all([response.json(), response.headers]);
                      // console.log(response.headers.get("Authorization"));
                      // return response.json()
                  }else 
                  return Promise.reject("Invalid login attempt")
              })
              .then(([body, headers]) =>{
                console.log(`headers.get("Authorization").value: ${headers.get("Authorization").value}`);
                console.log(`headers.get("Authorization"): ${headers.get("Authorization")}`)
                setToken(headers.get("Authorization"));
                window.location.href = 'dashboard';
                })
                .catch((msg) => alert(msg))
          }
        }

    
    return (
        <Container className='mt-5'>
            <Form.Group className='mb-3 col-md-8  mx-md-auto' controlId="username">
                <Form.Label className='fs-5'>Username</Form.Label>
                <Form.Control type="email" size='lg' placeholder='task@gmail.com' value={username} onChange={(e) => setUsername(e.target.value)} />
            </Form.Group>
            <Form.Group className='mb-3 col-md-8  mx-md-auto' controlId="password">  
                <Form.Label className='fs-5'>Password</Form.Label>
                <Form.Control type="password" size='lg' value={password} placeholder='*******' onChange={(e) => setPassword(e.target.value)} />
            </Form.Group>
            <div className='mt-5 d-flex flex-column gap-3 flex-md-row justify-content-md-between col-md-8  mx-md-auto'>
            <Button id="button" type="button" size='lg' onClick={sendLoginRequest}>Login</Button>
            <Button variant='secondary' id="button" type="button" size='lg' onClick={sendLoginRequest}>Exit</Button>
            </div>
        </Container>
    );
};

export default Login;