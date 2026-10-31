# QuizMaster — Full-stack Online Quiz Application

React + Vite frontend, Java 21 / Spring Boot REST API, MySQL persistence, JWT authentication, student result history and administrator quiz management.

## Included features
- Student registration and login; BCrypt password hashing and JWT authentication.
- Admin account seeded from environment variables.
- Student dashboard with published quizzes.
- Timed multiple-choice quiz, automatic scoring on the backend, no correct answers exposed by the question endpoint.
- Persistent quiz attempts and student result history.
- Admin quiz creation, question creation, publish flag, quiz listing and deletion.
- Responsive React interface.
- Dockerfile and Render deployment blueprint; Vercel SPA configuration.

## Local setup
Requirements: Java 21, Maven 3.9+, Node.js 20+, MySQL 8.

1. Create database: `CREATE DATABASE online_quiz;`
2. Edit `backend/src/main/resources/application.properties`. Set `spring.datasource.password` to your local MySQL password. Change the JWT secret and admin credentials for any shared environment.
3. Start API:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   API: `http://localhost:8080`
4. In a second terminal:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   Open the Vite URL, normally `http://localhost:5173`.

### Default seeded administrator (local development only)
- Email: `admin@quizmaster.com`
- Password: `ChangeMe123!`

Change these in `application.properties` before sharing/deploying. On hosted deployment, set `ADMIN_EMAIL` and `ADMIN_PASSWORD` environment variables. The seeded admin is created only if that email does not already exist.

## API routes
Public:
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/quizzes`
- `GET /api/quizzes/{id}/questions`

Authenticated student:
- `GET /api/me`
- `POST /api/quizzes/{id}/submit`
- `GET /api/attempts/my`

Admin:
- `GET /api/admin/quizzes`
- `POST /api/admin/quizzes`
- `PUT /api/admin/quizzes/{id}`
- `DELETE /api/admin/quizzes/{id}`

## Deploy
You must create your own hosting accounts and connect this project to a GitHub repository; this archive cannot be deployed into your accounts automatically.

### Suggested hosting combination
- MySQL: Railway MySQL (create a database and copy its connection values).
- Backend: Render Web Service using the included `render.yaml` / Dockerfile.
- Frontend: Vercel static site using the `frontend` directory.

### Backend environment variables
Set on Render:
- `DB_URL`: JDBC URL, for example `jdbc:mysql://HOST:PORT/DB_NAME?useSSL=true&serverTimezone=UTC`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`: long random secret (at least 32 characters)
- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`
- `FRONTEND_URL`: exact deployed Vercel origin, e.g. `https://your-quiz-app.vercel.app`

### Frontend environment variable
Set on Vercel:
- `VITE_API_URL`: deployed backend origin, e.g. `https://quizmaster-api.onrender.com` (no trailing slash).

Build settings for Vercel:
- Root Directory: `frontend`
- Build Command: `npm run build`
- Output Directory: `dist`

After changing environment variables, redeploy both services. Configure your MySQL provider to accept connections from the backend host. The exact host/port/SSL settings depend on your MySQL provider.

## Security / production notes
This is a complete academic project starter, but review it before production use. The quiz countdown is enforced in the browser only; add server-side attempt start/deadline enforcement for high-stakes exams. Add rate limiting, email verification/password reset, audit logging, backups, monitoring and HTTPS policies before a public commercial launch. The admin seed credentials must be changed immediately. Never commit secrets or database passwords.
