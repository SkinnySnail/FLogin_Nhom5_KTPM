// src/tests/authService.test.js
import { loginUser, loginUserMock } from '../utils/authService';

// Mock fetch API
global.fetch = jest.fn();

describe('authService Tests', () => {
  beforeEach(() => {
    // Clear mock trước mỗi test
    fetch.mockClear();
  });

  describe('loginUser', () => {
    test('TC_AUTH_01: Đăng nhập thành công', async () => {
      // Mock response success
      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          success: true,
          token: 'test_token_123',
          message: 'Đăng nhập thành công'
        })
      });

      const result = await loginUser('testuser', 'Test123');

      expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/auth/login',
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username: 'testuser', password: 'Test123' })
        })
      );

      expect(result).toEqual({
        success: true,
        token: 'test_token_123',
        message: 'Đăng nhập thành công'
      });
    });

    test('TC_AUTH_02: Đăng nhập thất bại - sai thông tin', async () => {
      // Mock response 401
      fetch.mockResolvedValueOnce({
        ok: false,
        json: async () => ({
          message: 'Invalid credentials'
        })
      });

      await expect(loginUser('wronguser', 'wrongpass')).rejects.toEqual(
        expect.objectContaining({
          message: 'Invalid credentials'
        })
      );
    });

    test('TC_AUTH_03: Đăng nhập thất bại - lỗi network', async () => {
      // Mock network error
      fetch.mockRejectedValueOnce(new Error('Network error'));

      await expect(loginUser('testuser', 'Test123')).rejects.toEqual(
        expect.objectContaining({
          message: 'Network error'
        })
      );
    });

    test('TC_AUTH_04: Đăng nhập thất bại - server error 500', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        json: async () => ({
          message: 'Internal server error'
        })
      });

      await expect(loginUser('testuser', 'Test123')).rejects.toEqual(
        expect.objectContaining({
          message: 'Internal server error'
        })
      );
    });

    test('TC_AUTH_05: Đăng nhập thất bại - response không có message', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        json: async () => ({})
      });

      await expect(loginUser('testuser', 'Test123')).rejects.toEqual(
        expect.objectContaining({
          message: 'Đăng nhập thất bại'
        })
      );
    });
  });

  describe('loginUserMock', () => {
    test('TC_AUTH_MOCK_01: Mock đăng nhập thành công', async () => {
      const result = await loginUserMock('testuser', 'Test123');

      expect(result).toEqual({
        success: true,
        token: 'mock_token_123',
        message: 'Đăng nhập thành công'
      });
    });

    test('TC_AUTH_MOCK_02: Mock đăng nhập thất bại', async () => {
      await expect(loginUserMock('wronguser', 'wrongpass')).rejects.toEqual(
        expect.objectContaining({
          message: 'Invalid credentials'
        })
      );
    });
  });
});
