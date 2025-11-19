// tests/ProductForm.integration.test.js - CẬP NHẬT
import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ProductForm from '../components/ProductForm';

// Mock fetch API
global.fetch = jest.fn();

describe('Product Form Integration Tests', () => {
  beforeEach(() => {
    // Clear mock trước mỗi test
    fetch.mockClear();
  });
  test('TC_PRODUCT_INT_01: Hiển thị lỗi khi name rỗng', async () => {
    render(<ProductForm />);
    
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-name-error')).toHaveTextContent('Product name is required');
    });
  });

  test('TC_PRODUCT_INT_02: Hiển thị lỗi khi price = 0', async () => {
    render(<ProductForm />);
    
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '0' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-price-error')).toHaveTextContent('Price must be greater than 0');
    });
  });

  test('TC_PRODUCT_INT_03: Hiển thị lỗi khi quantity âm', async () => {
    render(<ProductForm />);
    
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '-1' } });
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-quantity-error')).toHaveTextContent('Quantity cannot be negative');
    });
  });

  test('TC_PRODUCT_INT_04: Hiển thị lỗi khi name quá ngắn', async () => {
    render(<ProductForm />);
    
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'ab' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-name-error')).toHaveTextContent('Product name too short');
    });
  });

  test('TC_PRODUCT_INT_05: Hiển thị lỗi khi description quá dài', async () => {
    render(<ProductForm />);
    
    const longDescription = 'a'.repeat(501);
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.change(screen.getByTestId('product-description'), { target: { value: longDescription } });
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-description-error')).toHaveTextContent('Description too long');
    });
  });

  test('TC_PRODUCT_INT_06: Dropdown category hiển thị đúng options', () => {
    render(<ProductForm />);
    
    const categorySelect = screen.getByTestId('product-category');
    const options = categorySelect.querySelectorAll('option');
    
    // Có 6 options: 1 placeholder + 5 categories
    expect(options).toHaveLength(6);
    expect(options[0].textContent).toBe('-- Chọn danh mục --');
    expect(options[1].textContent).toBe('Electronics');
    expect(options[2].textContent).toBe('Clothing');
  });

  test('TC_PRODUCT_INT_07: Clear error khi user sửa input', async () => {
    render(<ProductForm />);
    
    // Submit với name rỗng
    fireEvent.click(screen.getByTestId('submit-btn'));
    
    await waitFor(() => {
      expect(screen.getByTestId('product-name-error')).toBeInTheDocument();
    });

    // User nhập tên sản phẩm
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'L' } });
    
    // Error bị xóa
    await waitFor(() => {
      expect(screen.queryByTestId('product-name-error')).not.toBeInTheDocument();
    });
  });

  test('TC_PRODUCT_INT_08: Button disabled khi đang loading', async () => {
    render(<ProductForm />);
    
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    
    const submitBtn = screen.getByTestId('submit-btn');
    fireEvent.click(submitBtn);

    // Button bị disable ngay sau khi click
    expect(submitBtn).toBeDisabled();
  });

  test('TC_PRODUCT_INT_09: Form reset sau khi tạo thành công', async () => {
    // Mock API success response
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ 
        id: 1, 
        name: 'Laptop Dell', 
        price: 15000000, 
        quantity: 10 
      })
    });

    const mockOnProductCreated = jest.fn();
    render(<ProductForm onProductCreated={mockOnProductCreated} />);
    
    fireEvent.change(screen.getByTestId('product-name'), { target: { value: 'Laptop Dell' } });
    fireEvent.change(screen.getByTestId('product-price'), { target: { value: '15000000' } });
    fireEvent.change(screen.getByTestId('product-quantity'), { target: { value: '10' } });
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-message')).toHaveTextContent('Thêm sản phẩm thành công!');
    });

    // Kiểm tra form đã reset
    expect(screen.getByTestId('product-name').value).toBe('');
    expect(screen.getByTestId('product-price').value).toBe('');
    expect(screen.getByTestId('product-quantity').value).toBe('');
  });

  test('TC_PRODUCT_INT_10: Hiển thị nhiều lỗi cùng lúc', async () => {
    render(<ProductForm />);
    
    // Submit form hoàn toàn rỗng
    fireEvent.click(screen.getByTestId('submit-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('product-name-error')).toBeInTheDocument();
      expect(screen.getByTestId('product-price-error')).toBeInTheDocument();
      // quantity default là '' nên sẽ bị convert thành NaN -> lỗi
    });
  });
});