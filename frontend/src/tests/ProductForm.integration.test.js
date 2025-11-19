import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
// Nếu đã có component ProductForm thì import, nếu chưa thì mock đơn giản
// import ProductForm from '../components/ProductForm';

function ProductForm() {
  const [name, setName] = React.useState('');
  const [price, setPrice] = React.useState('');
  const [quantity, setQuantity] = React.useState('');
  const [message, setMessage] = React.useState('');
  const [error, setError] = React.useState('');
  const handleSubmit = () => {
    if (!name) setError('Tên sản phẩm không được để trống');
    else if (price <= 0) setError('Giá sản phẩm phải lớn hơn 0');
    else {
      setMessage('Thêm sản phẩm thành công');
      setError('');
    }
  };
  return (
    <form>
      <label htmlFor="product-name">Tên sản phẩm</label>
      <input id="product-name" value={name} onChange={e => setName(e.target.value)} />
      <label htmlFor="product-price">Giá</label>
      <input id="product-price" value={price} onChange={e => setPrice(e.target.value)} />
      <label htmlFor="product-quantity">Số lượng</label>
      <input id="product-quantity" value={quantity} onChange={e => setQuantity(e.target.value)} />
      <button type="button" onClick={handleSubmit}>Lưu</button>
      {error && <div>{error}</div>}
      {message && <div>{message}</div>}
    </form>
  );
}

describe('Product Form Integration Tests', () => {
  test('Tạo sản phẩm mới thành công', async () => {
    render(<ProductForm />);
    fireEvent.change(screen.getByLabelText('Tên sản phẩm'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByLabelText('Giá'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByLabelText('Số lượng'), { target: { value: '10' } });
    fireEvent.click(screen.getByText('Lưu'));
    await waitFor(() => {
      expect(screen.getByText('Thêm sản phẩm thành công')).toBeInTheDocument();
    });
  });

  test('Hiển thị lỗi khi tên sản phẩm rỗng', async () => {
    render(<ProductForm />);
    fireEvent.change(screen.getByLabelText('Tên sản phẩm'), { target: { value: '' } });
    fireEvent.change(screen.getByLabelText('Giá'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByLabelText('Số lượng'), { target: { value: '10' } });
    fireEvent.click(screen.getByText('Lưu'));
    await waitFor(() => {
      expect(screen.getByText('Tên sản phẩm không được để trống')).toBeInTheDocument();
    });
  });

  test('Hiển thị lỗi khi giá <= 0', async () => {
    render(<ProductForm />);
    fireEvent.change(screen.getByLabelText('Tên sản phẩm'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByLabelText('Giá'), { target: { value: '0' } });
    fireEvent.change(screen.getByLabelText('Số lượng'), { target: { value: '10' } });
    fireEvent.click(screen.getByText('Lưu'));
    await waitFor(() => {
      expect(screen.getByText('Giá sản phẩm phải lớn hơn 0')).toBeInTheDocument();
    });
  });
});