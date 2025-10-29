# 🚨 CRITICAL BUG: AuthController Syntax Errors - Login không hoạt động

## ❌ **Vấn đề:**

**File:** `backend/src/main/java/com/unilab/api/AuthController.java`

**Hiện tượng:**
- Login với `admin@fpt.edu.vn` / `admin123` → **401 Unauthorized**
- Tất cả users đều không login được
- Backend start thành công nhưng authentication KHÔNG hoạt động

**Root cause:** **3 LỖI SYNTAX** trong `AuthController.java`!

---

## 🔍 **Chi tiết các lỗi:**

### **❌ LỖI 1: Line 145 - Thiếu dấu mở ngoặc `{`**

**TRƯỚC (SAI):**
```java
private String determineUserType(User user)  // ← THIẾU { Ở ĐÂY!
    if (user.getEmail().endsWith("@fpt.edu.vn")) {
        return "FPT_STUDENT";
    } else if (user.getEmail().endsWith("@gmail.com")) {
        return "GOOGLE_USER";
    } else {
        return "REGULAR_USER";
    }
}
```

**SAU (ĐÚNG):**
```java
private String determineUserType(User user) {  // ✅ THÊM {
    if (user.getEmail().endsWith("@fpt.edu.vn")) {
        return "FPT_STUDENT";
    } else if (user.getEmail().endsWith("@gmail.com")) {
        return "GOOGLE_USER";
    } else {
        return "REGULAR_USER";
    }
}
```

---

### **❌ LỖI 2: Line 94-95 - Thiếu parameters khi khởi tạo LoginVerificationResponse**

**Record definition (line 30):**
```java
record LoginVerificationResponse(String token, String loginMethod, String userType, String message) {}
// PHẢI CÓ 4 PARAMETERS!
```

**TRƯỚC (SAI):**
```java
// Line 89-96 trong register() method:
String token = jwtService.generateToken(savedUser.getEmail(), claims);

return ResponseEntity.status(201).body(new LoginVerificationResponse(
    "FPT_STUDENT", "Registration successful"  // ← CHỈ CÓ 2 PARAMS!
));
```

**SAU (ĐÚNG):**
```java
String token = jwtService.generateToken(savedUser.getEmail(), claims);

return ResponseEntity.status(201).body(new LoginVerificationResponse(
    token,              // ✅ param 1: token
    "FPT_REGISTRATION", // ✅ param 2: loginMethod
    "FPT_STUDENT",      // ✅ param 3: userType
    "Registration successful"  // ✅ param 4: message
));
```

---

### **❌ LỖI 3: Line 176 - Code bị cắt ngang (incomplete statement)**

**TRƯỚC (SAI):**
```java
// Line 175-177 trong googleLogin() method:
// Check if user exists
User  // ← CODE BỊ CẮT NGANG! THIẾU VARIABLE NAME VÀ LOGIC!
    User u = new User();
```

**SAU (ĐÚNG):**
```java
// Check if user exists
User user = userRepository.findByEmail(email).orElseGet(() -> {  // ✅
    User u = new User();
    u.setEmail(email);
    u.setFullName(fullName != null ? fullName : email);
    // ... rest of the code
});
```

---

## ✅ **Giải pháp: Sửa file AuthController.java**

### **1. Sửa lỗi line 145:**

```bash
# Tìm:
private String determineUserType(User user)
    if (user.getEmail()

# Đổi thành:
private String determineUserType(User user) {
    if (user.getEmail()
```

### **2. Sửa lỗi line 94-96:**

```bash
# Tìm:
return ResponseEntity.status(201).body(new LoginVerificationResponse(
"FPT_STUDENT", "Registration successful"
));

# Đổi thành:
return ResponseEntity.status(201).body(new LoginVerificationResponse(
    token, "FPT_REGISTRATION", "FPT_STUDENT", "Registration successful"
));
```

### **3. Sửa lỗi line 176:**

```bash
# Tìm:
// Check if user exists
User
    User u = new User();

# Đổi thành:
// Check if user exists
User user = userRepository.findByEmail(email).orElseGet(() -> {
    User u = new User();
```

---

## 🚀 **Sau khi fix:**

### **1. Rebuild backend:**

```bash
# Stop containers
docker-compose down

# Rebuild backend (để recompile Java code)
docker-compose build backend

# Start lại
docker-compose up backend db -d
```

### **2. Wait for backend to start:**

```bash
# Wait 15 seconds
sleep 15

# Check logs
docker logs swd392_group4-backend-1 --tail 20
```

### **3. Test login:**

```bash
curl -X POST http://localhost:8080/api/auth/login/fpt-sso \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@fpt.edu.vn","password":"admin123"}'
```

**Expected output:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "loginMethod": "FPT_SSO",
  "userType": "FPT_STUDENT",
  "message": "Login successful via FPT_SSO"
}
```

---

## 📊 **Impact:**

**Hiện tại:**
- ❌ Không login được (401 Unauthorized)
- ❌ Flutter app không sử dụng được
- ❌ React Web không sử dụng được
- ❌ Authentication hoàn toàn không hoạt động

**Sau khi fix:**
- ✅ Login thành công với JWT token
- ✅ Flutter app hoạt động
- ✅ React Web hoạt động
- ✅ Toàn bộ authentication flow hoạt động

---

## 🎯 **Priority: CRITICAL**

**Phải sửa ngay!** Không có auth = không có app!

---

## 🤔 **Tại sao Java vẫn compile được?**

Có thể backend đang chạy version `.class` cũ từ build trước khi có lỗi syntax. 

**Giải pháp:** Rebuild backend image để force recompile:

```bash
docker-compose down
docker-compose build --no-cache backend
docker-compose up backend db -d
```

---

## 📝 **Checklist:**

- [ ] Sửa line 145: Thêm `{` sau `User user)`
- [ ] Sửa line 94-95: Thêm đầy đủ 4 parameters cho `LoginVerificationResponse`
- [ ] Sửa line 176: Hoàn thành statement `User user = ...`
- [ ] Rebuild backend: `docker-compose build --no-cache backend`
- [ ] Restart: `docker-compose up backend db -d`
- [ ] Test login: `curl -X POST http://localhost:8080/api/auth/login/fpt-sso ...`
- [ ] Verify JWT token trong response

---

**Reported by:** Frontend Developer (Flutter)  
**Date:** 2025-10-26  
**File:** `backend/src/main/java/com/unilab/api/AuthController.java`  
**Lines:** 94-95, 145, 176  
**Severity:** CRITICAL - Authentication không hoạt động!

