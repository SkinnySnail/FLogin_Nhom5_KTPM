-- Thêm dữ liệu mẫu vào database khi khởi động ứng dụng
-- File này sẽ tự động chạy khi Spring Boot khởi động với H2 database

-- Thêm user mẫu để test đăng nhập
INSERT INTO users (id, username, password) VALUES 
(1, 'admin', 'admin123'),
(2, 'testuser', 'Test123'),
(3, 'user1', 'User@123');

-- Thêm sản phẩm mẫu
INSERT INTO product (id, name, price, quantity, category, description) VALUES
(1, 'Laptop Dell XPS 13', 25000000, 10, 'Electronics', 'Laptop cao cấp, màn hình 13 inch, CPU Intel i7'),
(2, 'iPhone 15 Pro', 30000000, 15, 'Electronics', 'Điện thoại thông minh cao cấp của Apple'),
(3, 'Samsung Galaxy S24', 22000000, 20, 'Electronics', 'Điện thoại Android flagship'),
(4, 'MacBook Pro M3', 45000000, 5, 'Electronics', 'Laptop cao cấp cho developer và designer'),
(5, 'iPad Air', 15000000, 12, 'Electronics', 'Máy tính bảng đa năng');
