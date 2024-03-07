import React, { useEffect, useState } from 'react';
import '../App.css';
import { useNavigate } from 'react-router-dom';

const Registration = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [pin, setPassword] = useState('');
  const [confirmPin, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [rank, setRank] = useState(''); 
  const [department, setDepartment] = useState(''); 
  const [role, setRole] = useState(''); 
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setUsername(lastName + firstName);
  }, [firstName, lastName]);

  const handleRegistration = async () => {
    if (!firstName || !lastName || !rank || !department || !pin || !confirmPin) {
      setError('Please fill in all fields.');
      return; // This is the correct place for return
    }

    // Check if PIN contains only numbers
    if (!/^\d+$/.test(pin)) {
      setError('PIN must contain only numbers.');
      return;
    }

    if (pin !== confirmPin) {
      setError('PIN does not match.');
      return;
    }

    setIsLoading(true);

    try {
      // POST request to '/api/register' for user registration
      const response = await fetch('api/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({username, pin, firstName, lastName, rank, department, role }),
      });

      if (response.status === 201) {
        navigate('/');
      } else {
        // Handle non-201 responses (like 400 or 500 errors)
        const errorMsg = await response.text();
        throw new Error(errorMsg || 'Registration failed. Please try a different username or contact administration.');
      }
    } catch (error) {
      // Handle parsing errors, or custom errors thrown from above
      console.error('Registration error:', error);
      if (error instanceof TypeError) {
        // could indicate a network request issue
        setError('A network error occurred. Please check your connection and try again.');
      } else {
        // Handle other errors
        setError(error.toString());
      }
    } finally {
      // This will always run regardless of success or failure
      setIsLoading(false);
    }
  };

  // Styles
  const containerStyle = { marginLeft: '1in', width: '50%' };
  const inputStyle = { display: 'block', marginBottom: '50px' };
  const labelStyle = { fontWeight: 'bold', width: '150px' };
  const inlineGroupStyle = { display: 'inline-block', marginRight: '20px' };

  return (
    <div style={containerStyle}>
      <h1>User Registration</h1>
      <div>
        {/* First Name and Last Name Fields */}
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>First Name: </label>
          <input
            type="text"
            style={inputStyle}
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
        </div>
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>Last Name: </label>
          <input
            type="text"
            style={inputStyle}
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
        </div>
        {/* Username Field (Read-Only) */}
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>Username: </label>
          <input
            type="text"
            style={inputStyle}
            value={username}
            readOnly
          />
        </div>

        {/* Rank Dropdown */}
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>Rank: </label>
          <select
            style={inputStyle}
            value={rank}
            onChange={(e) => setRank(e.target.value)}
          >
            <option value="">Select Rank</option>
            <option value="Instructor">Instructor</option>
            <option value="Assistant Professor">Assistant Professor</option>
            <option value="Associate Professor">Associate Professor</option>
            <option value="Professor">Professor</option>
            <option value="Distinguished Professor">Distinguished Professor</option>
          </select>
        </div>

        {/* Department Dropdown */}
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>Department: </label>
          <select
            style={inputStyle}
            value={department}
            onChange={(e) => setDepartment(e.target.value)}
          >
            <option value="">Select Department</option>
            <option value="AE">AE</option>
            <option value="CivE">CivE</option>
            <option value="EECS">EECS</option>
            <option value="EF">EF</option>
            <option value="ME">ME</option>
          </select>
        </div>

        {/* Role Dropdown */}
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>Role: </label>
          <select
            style={inputStyle}
            value={role}
            onChange={(e) => setRole(e.target.value)}
          >
            <option value="">Select Role</option>
            <option value="User">User</option>
            <option value="Department Head">Department Head</option>
            <option value="Dean">Dean</option>
          </select>
        </div>
        {/* Blank */}
        <div>
          <br />
        </div>
        {/* PIN and Confirm PIN Fields */}
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>PIN: </label>
          <input
            type="PIN"
            style={inputStyle}
            value={pin}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div style={inlineGroupStyle}>
          <label style={labelStyle}>Confirm PIN: </label>
          <input
            type="PIN"
            style={inputStyle}
            value={confirmPin}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
        </div>

        {/* Registration Button */}
        <button onClick={handleRegistration} disabled={isLoading}>
          {isLoading ? 'Registering...' : 'Register'}
        </button>

        {/* Error Message */}
        {error && <p className="error" style={{ color: 'red' }}>{error}</p>}
      </div>
    </div>
  );
};

export default Registration;
