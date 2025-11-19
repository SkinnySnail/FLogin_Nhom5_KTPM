// jest.config.js - CẬP NHẬT để fix CSS import
module.exports = {
  // Môi trường test
  testEnvironment: 'jsdom',
  
  // Module file extensions
  moduleFileExtensions: ['js', 'jsx', 'json'],
  
  // Setup file
  setupFilesAfterEnv: ['<rootDir>/setupTests.js'],

  // Module name mapper - PHẢI ĐẶT TRƯỚC transform
  moduleNameMapper: {
    '\\.(css|less|scss|sass)$': '<rootDir>/__mocks__/styleMock.js',
    '\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$': '<rootDir>/__mocks__/fileMock.js'
  },

  // Transform files
  transform: {
    '^.+\\.(js|jsx)$': 'babel-jest'
  },

  // Thu thập coverage
  collectCoverage: true,
  collectCoverageFrom: [
    'src/utils/**/*.{js,jsx}',
    'src/components/**/*.{js,jsx}',
    '!src/index.js',
    '!src/App.js',
    '!src/setupTests.js',
    '!**/node_modules/**'
  ],

  // Coverage directory
  coverageDirectory: 'coverage',

  // Coverage reporters
  coverageReporters: ['json', 'text', 'lcov', 'clover', 'html'],

  // Coverage thresholds
  coverageThreshold: {
    global: {
      statements: 80,
      branches: 80,
      functions: 80,
      lines: 80
    }
  },

  // Test match patterns
  testMatch: [
    '**/src/tests/**/*.test.js',
    '**/src/tests/**/*.spec.js'
  ]
};