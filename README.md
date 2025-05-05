# Spring Boot WebSocket Security with Token Authentication and Authorization

This project demonstrates a secure Spring Boot application using **WebSocket** communication protected by **JWT token-based authentication and role-based authorization**.

## 🚀 Features

- ✅ User Registration & Login with JWT
- ✅ Token-based Authentication
- ✅ Role-based Authorization (e.g., USER, ADMIN)
- ✅ Secure WebSocket communication using JWT
- ✅ Token Blacklisting on Logout
- ✅ Email Notifications (Optional)
- ✅ Real-time Updates via WebSocket

## 📦 Tech Stack

- **Backend**: Spring Boot, Spring Security, JWT
- **WebSocket**: Spring WebSocket + STOMP
- **Database**: MySQL
- **Build Tool**: Maven or Gradle


## 🔐 JWT Security

- On login, a JWT token is generated and returned to the client.
- The token must be included in the Authorization header:  
  `Authorization: Bearer <token>`
- WebSocket connections are established using token authentication.

## 📡 WebSocket Flow

1. Client connects via WebSocket with JWT token.
2. Spring Security validates the token before allowing communication.
3. Messages are handled securely based on user roles.


