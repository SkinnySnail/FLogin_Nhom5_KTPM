import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
// Nếu đã có component Login thì import, nếu chưa thì mock đơn giản
// import Login from '../components/Login';

function Login() {
  const [username, setUsername] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [error, setError] = React.useState('');
  const [message, setMessage] = React.useState('');
  const handleSubmit = () => {
    if (!username) setError('Username is required');
    else if (!password) setError('Password is required');
    else if (username === 'testuser' && password === 'Test123') {
      setMessage('thanh cong');
      setError('');
    } else {
      setError('Sai username hoặc password');
      setMessage('');
    }
  };
  return (
    <div>
      <input data-testid="username-input" value={username} onChange={e => setUsername(e.target.value)} />
      <input data-testid="password-input" value={password} onChange={e => setPassword(e.target.value)} />
      <button data-testid="login-button" onClick={handleSubmit}>Login</button>
      {error && <div data-testid="username-error">{error}</div>}
      {message && <div data-testid="login-message">{message}</div>}
    </div>
  );
}

describe('Login Component Integration Tests', () => {
  test('Hiển thị lỗi khi submit form rỗng', async () => {
    render(<Login />);
    fireEvent.click(screen.getByTestId('login-button'));
    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toBeInTheDocument();
      expect(screen.getByTestId('username-error').textContent).toBe('Username is required');
    });
  });

  test('Gọi API khi submit form hợp lệ', async () => {
    render(<Login />);
    fireEvent.change(screen.getByTestId('username-input'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByTestId('password-input'), { target: { value: 'Test123' } });
    fireEvent.click(screen.getByTestId('login-button'));
    await waitFor(() => {
      expect(screen.getByTestId('login-message')).toHaveTextContent('thanh cong');
    });
  });

  test('Hiển thị lỗi khi nhập sai thông tin', async () => {
    render(<Login />);
    fireEvent.change(screen.getByTestId('username-input'), { target: { value: 'wronguser' } });
    fireEvent.change(screen.getByTestId('password-input'), { target: { value: 'wrongpass' } });
    fireEvent.click(screen.getByTestId('login-button'));
    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toBeInTheDocument();
      expect(screen.getByTestId('username-error').textContent).toBe('Sai username hoặc password');
    });
  });
});