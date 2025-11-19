// src/utils/productService.js - CẬP NHẬT gọi API thực

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Create new product
 * @param {Object} product - Product data {name, price, quantity, category?, description?}
 * @returns {Promise<Object>} Created product with ID
 */
export async function createProduct(product) {
  try {
    const response = await fetch(`${API_BASE_URL}/products`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(product)
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'Tạo sản phẩm thất bại');
    }

    return data;
  } catch (error) {
    throw {
      message: error.message || 'Không thể kết nối đến server'
    };
  }
}

/**
 * Get all products
 * @returns {Promise<Array>} List of products
 */
export async function getProducts() {
  try {
    const response = await fetch(`${API_BASE_URL}/products`);
    
    if (!response.ok) {
      throw new Error('Không thể tải danh sách sản phẩm');
    }

    return await response.json();
  } catch (error) {
    throw {
      message: error.message || 'Không thể kết nối đến server'
    };
  }
}

/**
 * Get product by ID
 * @param {number} id - Product ID
 * @returns {Promise<Object>} Product data
 */
export async function getProductById(id) {
  try {
    const response = await fetch(`${API_BASE_URL}/products/${id}`);
    
    if (!response.ok) {
      if (response.status === 404) {
        throw new Error('Không tìm thấy sản phẩm');
      }
      throw new Error('Không thể tải sản phẩm');
    }

    return await response.json();
  } catch (error) {
    throw {
      message: error.message || 'Không thể kết nối đến server'
    };
  }
}

/**
 * Update product
 * @param {number} id - Product ID
 * @param {Object} product - Updated product data
 * @returns {Promise<Object>} Updated product
 */
export async function updateProduct(id, product) {
  try {
    const response = await fetch(`${API_BASE_URL}/products/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(product)
    });

    if (!response.ok) {
      if (response.status === 404) {
        throw new Error('Không tìm thấy sản phẩm');
      }
      throw new Error('Cập nhật sản phẩm thất bại');
    }

    return await response.json();
  } catch (error) {
    throw {
      message: error.message || 'Không thể kết nối đến server'
    };
  }
}

/**
 * Delete product
 * @param {number} id - Product ID
 * @returns {Promise<boolean>} True if deleted successfully
 */
export async function deleteProduct(id) {
  try {
    const response = await fetch(`${API_BASE_URL}/products/${id}`, {
      method: 'DELETE'
    });

    if (!response.ok) {
      if (response.status === 404) {
        throw new Error('Không tìm thấy sản phẩm');
      }
      throw new Error('Xóa sản phẩm thất bại');
    }

    return true;
  } catch (error) {
    throw {
      message: error.message || 'Không thể kết nối đến server'
    };
  }
}

/**
 * Mock version for testing
 */
export async function createProductMock(product) {
  await new Promise(resolve => setTimeout(resolve, 500));
  return Promise.resolve({ id: 1, ...product });
}