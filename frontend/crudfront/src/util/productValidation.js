// utils/productValidation.js

export const VALID_CATEGORIES = ['Electronics', 'Clothing', 'Food', 'Books', 'Toys'];

export function validateProduct(product) {
  const errors = {};

  // Get product name from either 'name' or 'productName' field
  const productName = product.productName || product.name;

  // Validate Product Name
  if (!productName || productName.trim() === '') {
    errors.name = 'Product name is required';
  } else if (productName.length < 3) {
    errors.name = 'Product name too short';
  } else if (productName.length > 100) {
    errors.name = 'Product name too long';
  }

  // Validate Price
  if (product.price <= 0) {
    errors.price = 'Price must be greater than 0';
  } else if (product.price > 999999999) {
    errors.price = 'Price too high';
  }

  // Validate Quantity
  if (product.quantity < 0) {
    errors.quantity = 'Quantity cannot be negative';
  } else if (product.quantity > 99999) {
    errors.quantity = 'Quantity too high';
  }

  // Validate Description
  if (product.description && product.description.length > 500) {
    errors.description = 'Description too long';
  }

  // Validate Category
  if (product.category && !VALID_CATEGORIES.includes(product.category)) {
    errors.category = 'Invalid category';
  }

  return errors;
}