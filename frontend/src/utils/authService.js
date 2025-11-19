// src/utils/authService.js
export async function loginUser(username, password) {
  // Giả lập gọi API backend
  // Trong thực tế sẽ dùng fetch/axios
  if (username === 'testuser' && password === 'Test123') {
    return Promise.resolve({ success: true });
  }
  return Promise.reject({ message: 'Invalid credentials' });
}
