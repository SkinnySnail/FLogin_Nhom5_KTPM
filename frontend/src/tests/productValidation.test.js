// tests/productValidation.test.js
import { validateProduct } from '../utils/productValidation';

describe('Product Validation', () => {
  
  // ===== Test Name =====
  test('TC1: Product name rỗng -> báo lỗi', () => {
    const product = { name: '', price: 1000, quantity: 10 };
    const errors = validateProduct(product);
    expect(errors.name).toBe('Product name is required');
  });

  test('TC2: Product name quá ngắn (< 3)', () => {
    const product = { name: 'ab', price: 1000, quantity: 10 };
    const errors = validateProduct(product);
    expect(errors.name).toBe('Product name too short');
  });

  test('TC3: Product name quá dài (> 100)', () => {
    const product = { name: 'a'.repeat(101), price: 1000, quantity: 10 };
    const errors = validateProduct(product);
    expect(errors.name).toBe('Product name too long');
  });

  // ===== Test Price =====
  test('TC4: Price = 0 -> báo lỗi', () => {
    const product = { name: 'Laptop', price: 0, quantity: 10 };
    const errors = validateProduct(product);
    expect(errors.price).toBe('Price must be greater than 0');
  });

  test('TC5: Price âm -> báo lỗi', () => {
    const product = { name: 'Laptop', price: -1000, quantity: 10 };
    const errors = validateProduct(product);
    expect(errors.price).toBe('Price must be greater than 0');
  });

  test('TC6: Price > 999,999,999 -> báo lỗi', () => {
    const product = { name: 'Laptop', price: 1000000000, quantity: 10 };
    const errors = validateProduct(product);
    expect(errors.price).toBe('Price too high');
  });

  // ===== Test Quantity =====
  test('TC7: Quantity âm -> báo lỗi', () => {
    const product = { name: 'Laptop', price: 1000, quantity: -1 };
    const errors = validateProduct(product);
    expect(errors.quantity).toBe('Quantity cannot be negative');
  });

  test('TC8: Quantity > 99,999 -> báo lỗi', () => {
    const product = { name: 'Laptop', price: 1000, quantity: 100000 };
    const errors = validateProduct(product);
    expect(errors.quantity).toBe('Quantity too high');
  });

  // ===== Test Description =====
  test('TC9: Description > 500 ký tự -> báo lỗi', () => {
    const product = {
      name: 'Laptop',
      price: 1000,
      quantity: 10,
      description: 'a'.repeat(501)
    };
    const errors = validateProduct(product);
    expect(errors.description).toBe('Description too long');
  });

  // ===== Test Category =====
  test('TC10: Category không hợp lệ -> báo lỗi', () => {
    const product = {
      name: 'Laptop',
      price: 1000,
      quantity: 10,
      category: 'InvalidCategory'
    };
    const errors = validateProduct(product);
    expect(errors.category).toBe('Invalid category');
  });

  // ===== Happy Path =====
  test('TC11: Product hợp lệ -> không có lỗi', () => {
    const product = {
      name: 'Laptop Dell',
      price: 15000000,
      quantity: 10,
      category: 'Electronics',
      description: 'A good laptop'
    };
    const errors = validateProduct(product);
    expect(Object.keys(errors).length).toBe(0);
  });
});