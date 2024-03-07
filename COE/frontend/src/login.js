import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
 
  const handleLogin = async () => {
    try {
      // Temporarily log username and password for troubleshooting
      console.log('Logging in with:', username, password); // Make sure to remove this line after debugging

      const response = await fetch('api/login', { 
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({username, password }),
      });
  
      if (response.ok) {
        const data = await response.json();
        // save token
        localStorage.setItem('token', data.token);
        // store user profile info
        console.log(data.user); // can use to update UI/state
        navigate('/app'); 
      } else if (response.status === 401) {
        // Handle 401 
        setError('Incorrect username or password. Please try again.');
      } else {
        // Handle generic error
        setError('Login failed. Please try again.');
      }
    } catch (error) {
      console.error('Login error:', error);
      setError('An error occurred while trying to log in. Please try again.');
    }
  };

  const navigateToRegistration = () => {
    navigate('/Registration');
  };

  const containerStyle = {
    textAlign: 'center', 
    marginLeft: 'auto', 
    marginRight: 'auto', 
    width: '50%',
  };

  return (
    <div style={containerStyle}>
      <h1>USER LOGIN</h1>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>Login</button>
      <button onClick={navigateToRegistration}>Register</button>
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default Login;
