import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import * as authService from '../utils/authService';

jest.mock('../utils/authService');

function Login() {
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [message, setMessage] = React.useState('');
  const [error, setError] = React.useState('');
  const handleSubmit = async () => {
    try {
      const res = await authService.loginUser(username, password);
      setMessage(res.success ? 'thanh cong' : 'that bai');
      setError('');
    } catch (err) {
      setError(err.message || 'Loi dang nhap');
      setMessage('');
    }
  };
  return (
    <div>
      <input data-testid="username-input" value={username} onChange={e => setUsername(e.target.value)} />
      <input data-testid="password-input" value={password} onChange={e => setPassword(e.target.value)} />
      <button data-testid="login-button" onClick={handleSubmit}>Login</button>
      {error && <div data-testid="login-error">{error}</div>}
      {message && <div data-testid="login-message">{message}</div>}
    </div>
  );
}

describe('Login Mock Tests', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('Mock: Login thanh cong', async () => {
    authService.loginUser.mockResolvedValue({ success: true });
    render(<Login />);
    fireEvent.change(screen.getByTestId('username-input'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByTestId('password-input'), { target: { value: 'Test123' } });
    fireEvent.click(screen.getByTestId('login-button'));
    await waitFor(() => {
      expect(authService.loginUser).toHaveBeenCalledWith('testuser', 'Test123');
      expect(screen.getByTestId('login-message')).toHaveTextContent('thanh cong');
    });
  });

  test('Mock: Login that bai', async () => {
    authService.loginUser.mockRejectedValue({ message: 'Invalid credentials' });
    render(<Login />);
    fireEvent.change(screen.getByTestId('username-input'), { target: { value: 'wronguser' } });
    fireEvent.change(screen.getByTestId('password-input'), { target: { value: 'wrongpass' } });
    fireEvent.click(screen.getByTestId('login-button'));
    await waitFor(() => {
      expect(authService.loginUser).toHaveBeenCalledWith('wronguser', 'wrongpass');
      expect(screen.getByTestId('login-error')).toHaveTextContent('Invalid credentials');
    });
  });
});
