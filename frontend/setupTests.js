import '@testing-library/jest-dom';

// Mock fetch globally cho các tests
global.fetch = jest.fn();

// Reset mocks sau mỗi test
afterEach(() => {
  jest.clearAllMocks();
});