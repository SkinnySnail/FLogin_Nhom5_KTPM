// validation.test.js
import { validateUsername, validatePassword } from '../utils/validation';

describe('Login Validation', () => {
  test('username rỗng -> báo lỗi', () => {
    expect(validateUsername('')).toBe('Username is required');
  });

  test('username quá ngắn (< 3 ký tự)', () => {
    expect(validateUsername('ab')).toBe('Username too short');
  });

  test('password rỗng -> báo lỗi', () => {
    expect(validatePassword('')).toBe('Password is required');
  });

  test('password quá ngắn (< 6 ký tự)', () => {
    expect(validatePassword('123')).toBe('Password too short');
  });

  test('username & password hợp lệ', () => {
    expect(validateUsername('thinh123')).toBe(true);
    expect(validatePassword('abc12345')).toBe(true);
  });
});
// Thêm vào validation.test.js

describe('validateUsername - Boundary & Edge Cases', () => {
  
  // TC: Username quá dài (> 50 ký tự)
  test('username quá dài (> 50) -> báo lỗi', () => {
    const longUsername = 'a'.repeat(51);
    expect(validateUsername(longUsername)).toBe('Username too long');
  });

  // TC: Ký tự đặc biệt không hợp lệ
  test('username có ký tự đặc biệt không hợp lệ', () => {
    expect(validateUsername('user@123')).toBe('Username contains invalid characters');
    expect(validateUsername('user#123')).toBe('Username contains invalid characters');
  });

  // TC: Ký tự hợp lệ (a-z, A-Z, 0-9, -, ., _)
  test('username với -, ., _ hợp lệ', () => {
    expect(validateUsername('user-name')).toBe(true);
    expect(validateUsername('user.name')).toBe(true);
    expect(validateUsername('user_123')).toBe(true);
  });

  // TC: Khoảng trắng đầu/cuối
  test('username có khoảng trắng đầu/cuối -> báo lỗi', () => {
    expect(validateUsername(' user ')).toBe('Username cannot have leading/trailing spaces');
  });

  // TC: Boundary - Đúng 3 ký tự (min)
  test('username đúng 3 ký tự (min) -> hợp lệ', () => {
    expect(validateUsername('abc')).toBe(true);
  });

  // TC: Boundary - Đúng 50 ký tự (max)
  test('username đúng 50 ký tự (max) -> hợp lệ', () => {
    const maxUsername = 'a'.repeat(50);
    expect(validateUsername(maxUsername)).toBe(true);
  });
});

describe('validatePassword - Boundary & Edge Cases', () => {
  
  // TC: Password quá dài (> 100 ký tự)
  test('password quá dài (> 100) -> báo lỗi', () => {
    const longPassword = 'a'.repeat(101);
    expect(validatePassword(longPassword)).toBe('Password too long');
  });

  // TC: Password không có chữ
  test('password chỉ có số -> báo lỗi', () => {
    expect(validatePassword('123456')).toBe('Password must contain both letters and numbers');
  });

  // TC: Password không có số
  test('password chỉ có chữ -> báo lỗi', () => {
    expect(validatePassword('abcdef')).toBe('Password must contain both letters and numbers');
  });

  // TC: Password có cả chữ và số (hợp lệ)
  test('password có cả chữ và số -> hợp lệ', () => {
    expect(validatePassword('abc123')).toBe(true);
  });

  // TC: Boundary - Đúng 6 ký tự (min) + có chữ và số
  test('password đúng 6 ký tự (min) -> hợp lệ', () => {
    expect(validatePassword('abc123')).toBe(true);
  });

  // TC: Boundary - Đúng 100 ký tự (max)
  test('password đúng 100 ký tự (max) -> hợp lệ', () => {
    const maxPassword = 'a'.repeat(50) + '1'.repeat(50);
    expect(validatePassword(maxPassword)).toBe(true);
  });

  // TC: Khoảng trắng trong password
  test('password có khoảng trắng -> báo lỗi', () => {
    expect(validatePassword('abc 123')).toBe('Password cannot contain spaces');
  });
});