# 📚 HƯỚNG DẪN CHẠY ỨNG DỤNG FLOGIN

## 🎯 Tổng Quan

Dự án FLogin bao gồm:
- **Backend**: Spring Boot API (Java 17) - Port 8080
- **Frontend**: React Application - Port 3000
- **Database**: H2 In-Memory Database (không cần cài đặt riêng)

---

## 🔧 YÊU CẦU HỆ THỐNG

### Backend Requirements:
- ✅ Java JDK 17 hoặc mới hơn
- ✅ Maven 3.6+ (có thể dùng mvnw đi kèm project)

### Frontend Requirements:
- ✅ Node.js 16+ và npm 8+

### Kiểm Tra Phiên Bản:
```powershell
# Kiểm tra Java
java -version

# Kiểm tra Maven (nếu có)
mvn -version

# Kiểm tra Node.js
node --version

# Kiểm tra npm
npm --version
```

---

## 🚀 CÁCH 1: CHẠY BACKEND (Spring Boot)

### Bước 1: Mở Terminal/PowerShell tại thư mục backend
```powershell
cd "D:\sinh vien\Software Testing\FLogin_Nhom5_KTPM\backend\flogin"
```

### Bước 2: Chạy Backend bằng Maven Wrapper (Khuyến nghị)
```powershell
# Windows
.\mvnw.cmd spring-boot:run

# Hoặc nếu có Maven global
mvn spring-boot:run
```

### Bước 3: Kiểm Tra Backend Đã Chạy
Backend sẽ chạy tại: **http://localhost:8080**

Test bằng browser hoặc Postman:
- Health Check Auth: http://localhost:8080/api/auth/health
- Health Check Product: http://localhost:8080/api/products/health
- H2 Console: http://localhost:8080/h2-console

### H2 Database Console (Xem Database):
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (để trống)

---

## 🎨 CÁCH 2: CHẠY FRONTEND (React)

### Bước 1: Mở Terminal/PowerShell MỚI tại thư mục frontend
```powershell
cd "D:\sinh vien\Software Testing\FLogin_Nhom5_KTPM\frontend"
```

### Bước 2: Cài Dependencies (Chỉ cần làm 1 lần)
```powershell
npm install
```

### Bước 3: Chạy Frontend Development Server
```powershell
npm start
```

Frontend sẽ tự động mở browser tại: **http://localhost:3000**

---

## ✅ CHẠY CẢ 2: BACKEND + FRONTEND

### Khuyến nghị: Mở 2 Terminal/PowerShell riêng biệt

**Terminal 1 - Backend:**
```powershell
cd "D:\sinh vien\Software Testing\FLogin_Nhom5_KTPM\backend\flogin"
.\mvnw.cmd spring-boot:run
```

**Terminal 2 - Frontend:**
```powershell
cd "D:\sinh vien\Software Testing\FLogin_Nhom5_KTPM\frontend"
npm start
```

---

## 🧪 CHẠY TESTS

### Backend Tests (JUnit + Integration Tests):
```powershell
cd "D:\sinh vien\Software Testing\FLogin_Nhom5_KTPM\backend\flogin"

# Chạy tất cả tests
.\mvnw.cmd test

# Chạy tests với coverage report
.\mvnw.cmd clean test jacoco:report
```

### Frontend Tests (Jest):
```powershell
cd "D:\sinh vien\Software Testing\FLogin_Nhom5_KTPM\frontend"

# Chạy tests một lần với coverage
npm run test:ci

# Chạy tests ở watch mode (development)
npm test

# Chỉ xem coverage report
npm run test:coverage
```

---

## 👤 TÀI KHOẢN MẪU ĐỂ TEST

Database H2 đã được tự động thêm các tài khoản sau:

| Username   | Password  | Mô tả                    |
|------------|-----------|--------------------------|
| `admin`    | `admin123`| Tài khoản admin          |
| `testuser` | `Test123` | Tài khoản test chính     |
| `user1`    | `User@123`| Tài khoản user thường    |

### Sản Phẩm Mẫu:
- Laptop Dell XPS 13 - 25,000,000 VNĐ
- iPhone 15 Pro - 30,000,000 VNĐ
- Samsung Galaxy S24 - 22,000,000 VNĐ
- MacBook Pro M3 - 45,000,000 VNĐ
- iPad Air - 15,000,000 VNĐ

---

## 🔌 API ENDPOINTS

### Authentication APIs:
- `POST /api/auth/login` - Đăng nhập
- `GET /api/auth/health` - Health check

### Product APIs:
- `GET /api/products` - Lấy tất cả sản phẩm
- `GET /api/products/{id}` - Lấy sản phẩm theo ID
- `POST /api/products` - Tạo sản phẩm mới
- `PUT /api/products/{id}` - Cập nhật sản phẩm
- `DELETE /api/products/{id}` - Xóa sản phẩm
- `GET /api/products/health` - Health check

### Example Request (POST Login):
```json
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "Test123"
}
```

### Example Response:
```json
{
  "success": true,
  "message": "Đăng nhập thành công",
  "token": "token_testuser_1700472634000"
}
```

---

## 🐛 TROUBLESHOOTING (Xử Lý Lỗi)

### ❌ Lỗi: Port 8080 đã được sử dụng
```powershell
# Tìm process đang dùng port 8080
netstat -ano | findstr :8080

# Kill process theo PID
taskkill /PID <PID_NUMBER> /F
```

### ❌ Lỗi: Port 3000 đã được sử dụng
Frontend sẽ tự động hỏi bạn có muốn dùng port khác không. Chọn `Y`.

### ❌ Lỗi: "Java not found"
- Cài đặt Java JDK 17 từ: https://adoptium.net/
- Thêm Java vào PATH environment variable

### ❌ Lỗi: "npm command not found"
- Cài đặt Node.js từ: https://nodejs.org/
- Restart terminal sau khi cài

### ❌ Lỗi Backend không kết nối được từ Frontend
- Kiểm tra Backend đã chạy tại http://localhost:8080
- Kiểm tra CORS đã được enable trong controller (`@CrossOrigin(origins = "*")`)
- Kiểm tra firewall không block port 8080

### ❌ Database bị mất data sau khi restart
- H2 là **in-memory database**, data sẽ mất khi tắt backend
- Data mẫu sẽ tự động được insert lại khi khởi động từ file `data.sql`
- Nếu cần persistent database, chuyển sang MySQL/PostgreSQL

---

## 📊 XEM COVERAGE REPORTS

### Backend Coverage (JaCoCo):
Sau khi chạy `mvnw test jacoco:report`:
```
File: backend/flogin/target/site/jacoco/index.html
```
Mở file HTML này bằng browser.

### Frontend Coverage (Jest):
Sau khi chạy `npm run test:ci`:
```
File: frontend/coverage/index.html
File: frontend/coverage/lcov-report/index.html
```
Mở file HTML này bằng browser.

---

## 🎓 DEMO WORKFLOW

### 1. Khởi động Backend và Frontend
```powershell
# Terminal 1
cd backend\flogin
.\mvnw.cmd spring-boot:run

# Terminal 2
cd frontend
npm start
```

### 2. Test Login trên Frontend
- Mở http://localhost:3000
- Username: `testuser`
- Password: `Test123`
- Click "Đăng nhập"

### 3. Test Product Management
- Sau khi login, xem danh sách sản phẩm
- Thêm sản phẩm mới
- Sửa/Xóa sản phẩm

### 4. Kiểm tra Database
- Mở http://localhost:8080/h2-console
- Login vào H2 Console
- Chạy query: `SELECT * FROM USERS`
- Chạy query: `SELECT * FROM PRODUCT`

---

## 📝 GHI CHÚ QUAN TRỌNG

✅ **Database H2**: In-memory, data sẽ mất khi tắt backend  
✅ **CORS**: Đã được enable, frontend có thể gọi backend  
✅ **Port**: Backend (8080), Frontend (3000)  
✅ **Tests**: Backend có Unit + Integration tests, Frontend có Unit + Integration + Mock tests  
✅ **Coverage**: Backend và Frontend đều đạt >80% coverage  

---

## 📞 HỖ TRỢ

Nếu gặp vấn đề:
1. Kiểm tra lại yêu cầu hệ thống
2. Đọc phần Troubleshooting
3. Kiểm tra logs trong terminal
4. Đảm bảo không có process nào đang dùng port 8080 hoặc 3000

---

## ✨ TÍNH NĂNG CHÍNH

### Backend:
- ✅ Authentication API với validation
- ✅ Product CRUD API
- ✅ H2 In-memory Database
- ✅ Unit Tests & Integration Tests
- ✅ E2E Tests
- ✅ CORS enabled

### Frontend:
- ✅ Login Form với validation
- ✅ Product Form với validation
- ✅ React Components
- ✅ Unit Tests (Jest)
- ✅ Integration Tests
- ✅ Mock Tests
- ✅ 98% Test Coverage

---

**🎉 CHÚC BẠN DEMO THÀNH CÔNG! 🎉**
