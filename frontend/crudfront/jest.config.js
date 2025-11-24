module.exports = {
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
  
  moduleNameMapper: {
    '\\.(css|less|scss|sass)$': 'identity-obj-proxy',
    '\\.(jpg|jpeg|png|gif|svg)$': '<rootDir>/__mocks__/fileMock.js'
  },
  
  transform: {
    '^.+\\.(js|jsx)$': ['babel-jest', { configFile: './babel.config.js' }]
  },
  
  collectCoverageFrom: [
    'src/util/**/*.{js,jsx}',
    'src/pages/**/*.{js,jsx}',
    'src/product/**/*.{js,jsx}',
    '!src/index.js',
    '!src/App.js',
    '!src/setupTests.js'
  ],
  
  coverageThreshold: {
    global: {
      statements: 80,
      branches: 80,
      functions: 80,
      lines: 80
    }
  },
  
  testMatch: [
    '<rootDir>/src/__tests__/**/*.test.{js,jsx}',
    '<rootDir>/src/**/*.test.{js,jsx}'
  ]
};