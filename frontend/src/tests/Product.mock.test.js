import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import * as productService from '../utils/productService';
import React from 'react';

jest.mock('../utils/productService');

function ProductForm() {
  const [name, setName] = React.useState('');
  const [price, setPrice] = React.useState('');
  const [quantity, setQuantity] = React.useState('');
  const [message, setMessage] = React.useState('');
  const [error, setError] = React.useState('');
  const handleCreate = async () => {
    try {
      const res = await productService.createProduct({ name, price, quantity });
      setMessage('Tạo thành công');
      setError('');
    } catch (err) {
      setError(err.message || 'Lỗi tạo sản phẩm');
      setMessage('');
    }
  };
  return (
    <div>
      <input data-testid="product-name" value={name} onChange={e => setName(e.target.value)} />
      <input data-testid="product-price" value={price} onChange={e => setPrice(e.target.value)} />
      <input data-testid="product-quantity" value={quantity} onChange={e => setQuantity(e.target.value)} />
      <button data-testid="create-btn" onClick={handleCreate}>Tạo</button>
      {error && <div data-testid="product-error">{error}</div>}
      {message && <div data-testid="product-message">{message}</div>}
    </div>
  );
}

describe('Product Mock Tests', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('Mock: Create product thành công', async () => {
    productService.createProduct.mockResolvedValue({ id: 1, name: 'Laptop', price: 15000000 });
    render(<ProductForm />);
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.click(screen.getByTestId('create-btn'));
    await waitFor(() => {
      expect(productService.createProduct).toHaveBeenCalledWith({ name: 'Laptop', price: '15000000', quantity: '10' });
      expect(screen.getByTestId('product-message')).toHaveTextContent('Tạo thành công');
    });
  });

  test('Mock: Create product thất bại', async () => {
    productService.createProduct.mockRejectedValue({ message: 'Lỗi tạo sản phẩm' });
    render(<ProductForm />);
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.click(screen.getByTestId('create-btn'));
    await waitFor(() => {
      expect(productService.createProduct).toHaveBeenCalledWith({ name: 'Laptop', price: '15000000', quantity: '10' });
      expect(screen.getByTestId('product-error')).toHaveTextContent('Lỗi tạo sản phẩm');
    });
  });
});
