// src/utils/productService.js
export async function createProduct(product) {
  // Giả lập gọi API backend
  // Trong thực tế sẽ dùng fetch/axios
  return Promise.resolve({ id: 1, ...product });
}
