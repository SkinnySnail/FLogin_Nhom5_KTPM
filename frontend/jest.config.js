module.exports = {
  // Cho biết thông tin coverage có nên được thu thập trong khi thực thi test hay không
  collectCoverage: true,

  // Một mảng các mẫu glob cho biết một tập hợp các tệp mà thông tin coverage nên được thu thập
  collectCoverageFrom: [
    'src/utils/**/*.{js,jsx,ts,tsx}',
    '!**/node_modules/**',
    '!**/vendor/**'
  ],

  // Thư mục nơi Jest sẽ xuất các tệp coverage của mình
  coverageDirectory: 'coverage',

  // Danh sách tên các reporter mà Jest sử dụng khi ghi báo cáo coverage
  coverageReporters: ['json', 'text', 'lcov', 'clover'],

  // Môi trường test sẽ được sử dụng để testing
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['<rootDir>/setupTests.js'],
};
