import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  const [fullname, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const history = useNavigate();

  // Password validation regex
  // const isPasswordStrong = (password) => {
  //   // Example regex: at least 8 characters, at least one letter and one number
  //   return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(password);
  // };

  const handleRegister = async (e) => {
    e.preventDefault();

    // Validate password strength
    // if (!isPasswordStrong(password)) {
    //   setError('Password must be at least 8 characters long and contain at least one letter and one number.');
    //   return;
    // }

    try {
      console.log({ fullname, email, password });
      const res = await axios.post('http://localhost:8080/api/auth/create', { fullname, email, password });
      history('/login');
    } catch (err) {
      if (err.response && err.response.data) {
        setError(err.response.data.message || 'Failed to create account. Please try again.');
      } else {
        setError('Failed to create account. Please try again.');
      }
    }
  };

  return (
    <div>
      <h2>Register</h2>
      {error && <div style={{ color: 'red' }}>{error}</div>}
      <form onSubmit={handleRegister}>
        <div>
          <label>Username</label>
          <input
            type="text"
            value={fullname}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default Register;
