// src/tests/productService.test.js
import {
  createProduct,
  getProducts,
  getProductById,
  updateProduct,
  deleteProduct,
  createProductMock
} from '../utils/productService';

// Mock fetch API
global.fetch = jest.fn();

describe('productService Tests', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  describe('createProduct', () => {
    test('TC_PRODUCT_SRV_01: Tạo sản phẩm thành công', async () => {
      const productData = {
        name: 'Laptop Dell',
        price: 15000000,
        quantity: 10,
        category: 'Electronics'
      };

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 1, ...productData })
      });

      const result = await createProduct(productData);

      expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/products',
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(productData)
        })
      );

      expect(result).toEqual({ id: 1, ...productData });
    });

    test('TC_PRODUCT_SRV_02: Tạo sản phẩm thất bại - validation error', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        json: async () => ({ message: 'Tên sản phẩm không được rỗng' })
      });

      await expect(createProduct({ name: '', price: 100 })).rejects.toEqual(
        expect.objectContaining({
          message: 'Tên sản phẩm không được rỗng'
        })
      );
    });

    test('TC_PRODUCT_SRV_03: Tạo sản phẩm thất bại - network error', async () => {
      fetch.mockRejectedValueOnce(new Error('Network error'));

      await expect(createProduct({ name: 'Test', price: 100 })).rejects.toEqual(
        expect.objectContaining({
          message: 'Network error'
        })
      );
    });
  });

  describe('getProducts', () => {
    test('TC_PRODUCT_SRV_04: Lấy danh sách sản phẩm thành công', async () => {
      const products = [
        { id: 1, name: 'Product 1', price: 100 },
        { id: 2, name: 'Product 2', price: 200 }
      ];

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => products
      });

      const result = await getProducts();

      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/products');
      expect(result).toEqual(products);
    });

    test('TC_PRODUCT_SRV_05: Lấy danh sách sản phẩm thất bại', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        json: async () => ({})
      });

      await expect(getProducts()).rejects.toEqual(
        expect.objectContaining({
          message: 'Không thể tải danh sách sản phẩm'
        })
      );
    });

    test('TC_PRODUCT_SRV_06: Lấy danh sách sản phẩm - network error', async () => {
      fetch.mockRejectedValueOnce(new Error('Connection failed'));

      await expect(getProducts()).rejects.toEqual(
        expect.objectContaining({
          message: 'Connection failed'
        })
      );
    });
  });

  describe('getProductById', () => {
    test('TC_PRODUCT_SRV_07: Lấy sản phẩm theo ID thành công', async () => {
      const product = { id: 1, name: 'Product 1', price: 100 };

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => product
      });

      const result = await getProductById(1);

      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/products/1');
      expect(result).toEqual(product);
    });

    test('TC_PRODUCT_SRV_08: Lấy sản phẩm không tồn tại (404)', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        status: 404,
        json: async () => ({})
      });

      await expect(getProductById(999)).rejects.toEqual(
        expect.objectContaining({
          message: 'Không tìm thấy sản phẩm'
        })
      );
    });

    test('TC_PRODUCT_SRV_09: Lấy sản phẩm thất bại - server error', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        json: async () => ({})
      });

      await expect(getProductById(1)).rejects.toEqual(
        expect.objectContaining({
          message: 'Không thể tải sản phẩm'
        })
      );
    });

    test('TC_PRODUCT_SRV_10: Lấy sản phẩm - network error', async () => {
      fetch.mockRejectedValueOnce(new Error('Network timeout'));

      await expect(getProductById(1)).rejects.toEqual(
        expect.objectContaining({
          message: 'Network timeout'
        })
      );
    });
  });

  describe('updateProduct', () => {
    test('TC_PRODUCT_SRV_11: Cập nhật sản phẩm thành công', async () => {
      const updatedProduct = { id: 1, name: 'Updated Product', price: 150 };

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => updatedProduct
      });

      const result = await updateProduct(1, { name: 'Updated Product', price: 150 });

      expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/products/1',
        expect.objectContaining({
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' }
        })
      );

      expect(result).toEqual(updatedProduct);
    });

    test('TC_PRODUCT_SRV_12: Cập nhật sản phẩm không tồn tại (404)', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        status: 404,
        json: async () => ({})
      });

      await expect(updateProduct(999, { name: 'Test' })).rejects.toEqual(
        expect.objectContaining({
          message: 'Không tìm thấy sản phẩm'
        })
      );
    });

    test('TC_PRODUCT_SRV_13: Cập nhật sản phẩm thất bại - server error', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        json: async () => ({})
      });

      await expect(updateProduct(1, { name: 'Test' })).rejects.toEqual(
        expect.objectContaining({
          message: 'Cập nhật sản phẩm thất bại'
        })
      );
    });

    test('TC_PRODUCT_SRV_14: Cập nhật sản phẩm - network error', async () => {
      fetch.mockRejectedValueOnce(new Error('Update failed'));

      await expect(updateProduct(1, { name: 'Test' })).rejects.toEqual(
        expect.objectContaining({
          message: 'Update failed'
        })
      );
    });
  });

  describe('deleteProduct', () => {
    test('TC_PRODUCT_SRV_15: Xóa sản phẩm thành công', async () => {
      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({})
      });

      const result = await deleteProduct(1);

      expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/products/1',
        expect.objectContaining({
          method: 'DELETE'
        })
      );

      expect(result).toBe(true);
    });

    test('TC_PRODUCT_SRV_16: Xóa sản phẩm không tồn tại (404)', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        status: 404,
        json: async () => ({})
      });

      await expect(deleteProduct(999)).rejects.toEqual(
        expect.objectContaining({
          message: 'Không tìm thấy sản phẩm'
        })
      );
    });

    test('TC_PRODUCT_SRV_17: Xóa sản phẩm thất bại - server error', async () => {
      fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        json: async () => ({})
      });

      await expect(deleteProduct(1)).rejects.toEqual(
        expect.objectContaining({
          message: 'Xóa sản phẩm thất bại'
        })
      );
    });

    test('TC_PRODUCT_SRV_18: Xóa sản phẩm - network error', async () => {
      fetch.mockRejectedValueOnce(new Error('Delete failed'));

      await expect(deleteProduct(1)).rejects.toEqual(
        expect.objectContaining({
          message: 'Delete failed'
        })
      );
    });
  });

  describe('createProductMock', () => {
    test('TC_PRODUCT_SRV_19: Mock tạo sản phẩm thành công', async () => {
      const productData = { name: 'Test Product', price: 100 };
      const result = await createProductMock(productData);

      expect(result).toEqual({ id: 1, ...productData });
    });
  });
});
