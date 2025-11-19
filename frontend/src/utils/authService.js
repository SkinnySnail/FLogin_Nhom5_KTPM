const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Login user by calling backend API
 * @param {string} username 
 * @param {string} password 
 * @returns {Promise<{success: boolean, token?: string, message?: string}>}
 */
export async function loginUser(username, password) {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password })
    });

    const data = await response.json();

    if (!response.ok) {
      // API trả về lỗi (401, 400, etc.)
      throw new Error(data.message || 'Đăng nhập thất bại');
    }

    // Login thành công
    return {
      success: true,
      token: data.token,
      message: data.message
    };
  } catch (error) {
    // Network error hoặc JSON parse error
    throw {
      message: error.message || 'Không thể kết nối đến server'
    };
  }
}

/**
 * Mock version for testing (giữ lại để test không cần backend chạy)
 */
export async function loginUserMock(username, password) {
  // Giả lập delay network
  await new Promise(resolve => setTimeout(resolve, 500));
  
  if (username === 'testuser' && password === 'Test123') {
    return Promise.resolve({ 
      success: true, 
      token: 'mock_token_123',
      message: 'Đăng nhập thành công'
    });
  }
  return Promise.reject({ message: 'Invalid credentials' });
}