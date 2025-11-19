// src/components/Login.jsx
import React, { useState } from 'react';
import { loginUser } from '../utils/authService';
import { validateUsername, validatePassword } from '../utils/validation';
import '../styles/Login.css';

function Login({ onLoginSuccess }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Clear previous messages
    setErrors({});
    setMessage('');

    // Validate inputs
    const usernameError = validateUsername(username);
    const passwordError = validatePassword(password);

    if (usernameError !== true || passwordError !== true) {
      setErrors({
        username: usernameError !== true ? usernameError : '',
        password: passwordError !== true ? passwordError : ''
      });
      return;
    }

    // Call API
    setLoading(true);
    try {
      const response = await loginUser(username, password);
      setMessage('Đăng nhập thành công!');
      if (onLoginSuccess) {
        onLoginSuccess(response);
      }
    } catch (error) {
      setErrors({ general: error.message || 'Đăng nhập thất bại' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Đăng nhập</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              id="username"
              data-testid="username-input"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Nhập username"
              disabled={loading}
            />
            {errors.username && (
              <span className="error" data-testid="username-error">
                {errors.username}
              </span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              data-testid="password-input"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Nhập password"
              disabled={loading}
            />
            {errors.password && (
              <span className="error" data-testid="password-error">
                {errors.password}
              </span>
            )}
          </div>

          {errors.general && (
            <div className="error general-error" data-testid="general-error">
              {errors.general}
            </div>
          )}

          {message && (
            <div className="success" data-testid="login-message">
              {message}
            </div>
          )}

          <button
            type="submit"
            data-testid="login-button"
            disabled={loading}
            className="btn-primary"
          >
            {loading ? 'Đang xử lý...' : 'Đăng nhập'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;