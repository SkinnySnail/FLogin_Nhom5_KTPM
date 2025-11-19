// src/components/ProductForm.jsx
import React, { useState } from 'react';
import { validateProduct } from '../utils/productValidation';
import { createProduct } from '../utils/productService';
import '../styles/ProductForm.css';

function ProductForm({ onProductCreated }) {
  const [product, setProduct] = useState({
    name: '',
    price: '',
    quantity: '',
    category: '',
    description: ''
  });
  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const categories = ['Electronics', 'Clothing', 'Food', 'Books', 'Toys'];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct(prev => ({
      ...prev,
      [name]: value
    }));
    // Clear error when user types
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Clear previous messages
    setErrors({});
    setMessage('');

    // Convert string to numbers for validation
    const productToValidate = {
      ...product,
      price: Number(product.price),
      quantity: Number(product.quantity)
    };

    // Validate
    const validationErrors = validateProduct(productToValidate);
    
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    // Call API
    setLoading(true);
    try {
      const response = await createProduct(productToValidate);
      setMessage('Thêm sản phẩm thành công!');
      // Reset form
      setProduct({
        name: '',
        price: '',
        quantity: '',
        category: '',
        description: ''
      });
      if (onProductCreated) {
        onProductCreated(response);
      }
    } catch (error) {
      setErrors({ general: error.message || 'Thêm sản phẩm thất bại' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="product-form-container">
      <h2>Thêm sản phẩm mới</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="product-name">Tên sản phẩm *</label>
          <input
            id="product-name"
            data-testid="product-name"
            type="text"
            name="name"
            value={product.name}
            onChange={handleChange}
            placeholder="Nhập tên sản phẩm"
            disabled={loading}
          />
          {errors.name && (
            <span className="error" data-testid="product-name-error">
              {errors.name}
            </span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="product-price">Giá *</label>
          <input
            id="product-price"
            data-testid="product-price"
            type="number"
            name="price"
            value={product.price}
            onChange={handleChange}
            placeholder="Nhập giá sản phẩm"
            disabled={loading}
          />
          {errors.price && (
            <span className="error" data-testid="product-price-error">
              {errors.price}
            </span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="product-quantity">Số lượng *</label>
          <input
            id="product-quantity"
            data-testid="product-quantity"
            type="number"
            name="quantity"
            value={product.quantity}
            onChange={handleChange}
            placeholder="Nhập số lượng"
            disabled={loading}
          />
          {errors.quantity && (
            <span className="error" data-testid="product-quantity-error">
              {errors.quantity}
            </span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="product-category">Danh mục</label>
          <select
            id="product-category"
            data-testid="product-category"
            name="category"
            value={product.category}
            onChange={handleChange}
            disabled={loading}
          >
            <option value="">-- Chọn danh mục --</option>
            {categories.map(cat => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>
          {errors.category && (
            <span className="error" data-testid="product-category-error">
              {errors.category}
            </span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="product-description">Mô tả</label>
          <textarea
            id="product-description"
            data-testid="product-description"
            name="description"
            value={product.description}
            onChange={handleChange}
            placeholder="Nhập mô tả sản phẩm (tối đa 500 ký tự)"
            rows="4"
            disabled={loading}
          />
          {errors.description && (
            <span className="error" data-testid="product-description-error">
              {errors.description}
            </span>
          )}
        </div>

        {errors.general && (
          <div className="error general-error" data-testid="product-general-error">
            {errors.general}
          </div>
        )}

        {message && (
          <div className="success" data-testid="product-message">
            {message}
          </div>
        )}

        <button
          type="submit"
          data-testid="submit-btn"
          disabled={loading}
          className="btn-primary"
        >
          {loading ? 'Đang xử lý...' : 'Lưu sản phẩm'}
        </button>
      </form>
    </div>
  );
}

export default ProductForm;