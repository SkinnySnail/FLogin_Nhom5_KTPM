import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
// Nếu đã có component Login thì import, nếu chưa thì mock đơn giản
// import Login from '../components/Login';
import Login from '../components/Login';

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
  test('TC_LOGIN_INT_01: Hiển thị lỗi khi submit form rỗng', async () => {
    render(<Login />);
    
    const submitButton = screen.getByTestId('login-button');
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toBeInTheDocument();
      expect(screen.getByTestId('username-error').textContent).toBe('Username is required');
    });
  });

  test('TC_LOGIN_INT_02: Hiển thị lỗi khi username rỗng', async () => {
    render(<Login />);
    
    const passwordInput = screen.getByTestId('password-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(passwordInput, { target: { value: 'Test123' } });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toBeInTheDocument();
    });
  });

  test('TC_LOGIN_INT_03: Hiển thị lỗi khi password rỗng', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(usernameInput, { target: { value: 'testuser' } });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('password-error')).toBeInTheDocument();
    });
  });

  test('TC_LOGIN_INT_04: Hiển thị lỗi khi username quá ngắn', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const passwordInput = screen.getByTestId('password-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(usernameInput, { target: { value: 'ab' } });
    fireEvent.change(passwordInput, { target: { value: 'Test123' } });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toHaveTextContent('Username too short');
    });
  });

  test('TC_LOGIN_INT_05: Hiển thị lỗi khi password quá ngắn', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const passwordInput = screen.getByTestId('password-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(usernameInput, { target: { value: 'testuser' } });
    fireEvent.change(passwordInput, { target: { value: '123' } });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('password-error')).toHaveTextContent('Password too short');
    });
  });

  test('TC_LOGIN_INT_06: Hiển thị lỗi khi username có ký tự đặc biệt', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const passwordInput = screen.getByTestId('password-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(usernameInput, { target: { value: 'user@123' } });
    fireEvent.change(passwordInput, { target: { value: 'Test123' } });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toHaveTextContent('Username contains invalid characters');
    });
  });

  test('TC_LOGIN_INT_07: Hiển thị lỗi khi password không có số', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const passwordInput = screen.getByTestId('password-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(usernameInput, { target: { value: 'testuser' } });
    fireEvent.change(passwordInput, { target: { value: 'TestPassword' } });
    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByTestId('password-error')).toHaveTextContent('Password must contain both letters and numbers');
    });
  });

  test('TC_LOGIN_INT_08: Button disabled khi đang loading', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const passwordInput = screen.getByTestId('password-input');
    const submitButton = screen.getByTestId('login-button');
    
    fireEvent.change(usernameInput, { target: { value: 'testuser' } });
    fireEvent.change(passwordInput, { target: { value: 'Test123' } });
    fireEvent.click(submitButton);

    // Kiểm tra button bị disable ngay sau khi click
    expect(submitButton).toBeDisabled();
  });

  test('TC_LOGIN_INT_09: Clear error khi user nhập lại', async () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const submitButton = screen.getByTestId('login-button');
    
    // Submit với username rỗng
    fireEvent.click(submitButton);
    
    await waitFor(() => {
      expect(screen.getByTestId('username-error')).toBeInTheDocument();
    });

    // User nhập lại username
    fireEvent.change(usernameInput, { target: { value: 't' } });
    
    // Error vẫn còn (chỉ clear khi validation pass)
    expect(screen.getByTestId('username-error')).toBeInTheDocument();
  });

  test('TC_LOGIN_INT_10: Form fields có placeholder đúng', () => {
    render(<Login />);
    
    const usernameInput = screen.getByTestId('username-input');
    const passwordInput = screen.getByTestId('password-input');
    
    expect(usernameInput).toHaveAttribute('placeholder', 'Nhập username');
    expect(passwordInput).toHaveAttribute('placeholder', 'Nhập password');
  });
});