# 🎓 QuizMaster Academy Portal (Full-Stack React.js + Spring Boot)

An interactive, production-ready online assessment portal built with **React.js** and **Java Spring Boot REST API**, backed by an **in-memory H2 database**.

---

## 🌟 Feature Tour & Portal Modules

### 1. 🏠 Landing Page & Learning Tracks
- **Track Selection**:
  - **☕ Java & Spring Boot Mastery** (Collections, JVM Architecture, REST Annotations, JPA `@Id`)
  - **⚛️ Modern React & Web UI** (`useState`, `useEffect`, Virtual DOM Diffing, Flexbox)
  - **💾 Databases & REST Architecture** (ACID Atomicity, In-memory H2, HTTP 201, Idempotency)
  - **🏆 Full-Stack Challenge** (Comprehensive assessment across all 12 questions)
- Metric highlights: 12 curated questions, 3 learning tracks, real-time server scoring.

### 2. ✍️ Active Quiz Experience
- **Server-Side Security**: Question retrieval (`GET /api/quiz/questions`) omits correct answers and explanations.
- **Interactive Timer**: 5-minute track timer / 10-minute exam timer with visual warning (<60s) and critical alert (<30s).
- **Flexible View Modes**:
  - **All-in-One**: Scroll and answer all questions in a continuous view.
  - **Step-by-Step Focus**: Answer one question at a time with Prev / Next navigation.
- **Question Flagging & Navigation**: Bookmark questions for review with a dedicated question pill ribbon (Pending, Answered, Flagged).
- **Submission Protection**: Confirmation warning modal before submitting if unanswered questions remain.

### 3. 📊 Scorecard & Pedagogical Review
- **Immediate Server Evaluation**: Spring Boot calculates score, percentage, pass/fail status, and feedback messages.
- **Detailed Solution Inspector**:
  - Filter solutions by **All**, **Correct Only (✓)**, or **Incorrect Only (✗)**.
  - Visual color cards showing student's answer vs the official solution.
  - Pedagogical explanation box for each question.
- **Actions**: Retake quiz anytime or browse other learning tracks.

### 4. 🏆 Student Leaderboard
- Real-time ranking table displaying top scores, accuracy percentage bars, completion times, and student badges.
- Automatically inserts the student's recent score with a highlighted ⭐ **Verified Score** row.

### 5. 📈 Assessment History & Analytics
- Persisted locally in `localStorage`.
- Summary statistics: Total exams taken, average accuracy, pass rate, and total passed certifications.
- Full log of past quiz attempts with timestamps, categories, and score breakdowns.

### 6. 🌙 Dark & Light Themes
- Smooth theme toggle stored in preferences.

---

## 🛠️ Technology Stack

| Layer | Technologies |
|---|---|
| **Frontend** | React 18, Vite 5, Modern CSS (Variables, Flexbox/Grid, Dark Mode) |
| **Backend** | Java 17 (Temurin), Spring Boot 3.3.5, Spring MVC, Spring Data JPA |
| **Database** | In-Memory H2 Database (`jdbc:h2:mem:quizdb`) with H2 Console |
| **Build Tools** | Maven 3.9.9, Node.js v24, npm 11 |

---

## 📁 Project Structure

```text
online-quiz-app/
├── backend/
│   ├── pom.xml                                  # Maven dependencies (Spring Boot, JPA, H2)
│   ├── src/main/java/com/quiz/
│   │   ├── QuizApplication.java                # Spring Boot entry point
│   │   ├── config/
│   │   │   ├── CorsConfig.java                 # CORS configuration
│   │   │   └── DataInitializer.java            # Seeds 12 sample quiz questions
│   │   ├── controller/
│   │   │   └── QuizController.java             # REST API (/api/quiz/*)
│   │   ├── dto/
│   │   │   ├── AnswerSubmissionDto.java        # Single answer DTO
│   │   │   ├── QuestionResponseDto.java        # Client question DTO (zero leakage)
│   │   │   ├── QuestionResultDto.java          # Evaluated answer with explanation
│   │   │   ├── QuizResultDto.java              # Total score and summary DTO
│   │   │   └── QuizSubmissionDto.java          # Submission payload DTO
│   │   ├── model/
│   │   │   └── Question.java                   # JPA Entity with category & difficulty
│   │   ├── repository/
│   │   │   └── QuestionRepository.java         # Spring Data JPA Repository
│   │   └── service/
│   │       └── QuizService.java                # Scoring, category filtering & evaluation
│   └── src/main/resources/
│       └── application.properties              # Port 8081, H2 configuration
├── frontend/
│   ├── package.json                            # React & Vite dependencies
│   ├── vite.config.js                          # Vite server + proxy to port 8081
│   ├── index.html                              # Web app HTML template
│   └── src/
│       ├── main.jsx                            # React bootstrap
│       ├── App.jsx                             # Main portal state & tab router
│       ├── App.css                             # Light/Dark design system & responsive layout
│       └── components/
│           ├── Navbar.jsx                      # Navigation, branding & theme toggle
│           ├── HomeHero.jsx                    # Hero section & statistics
│           ├── CategoryGrid.jsx                # Track selection cards
│           ├── QuizTimer.jsx                   # Countdown timer component
│           ├── QuestionCard.jsx                # Question selection & solution review
│           ├── ProgressBar.jsx                 # Question pill navigation & progress
│           ├── ScoreSummary.jsx                # Scorecard, filters & retake actions
│           ├── Leaderboard.jsx                 # Academy student leaderboard
│           └── HistoryView.jsx                 # Past attempts analytics
└── README.md
```

---

## 🚀 Running the Portal Locally

Both services run concurrently:

### 1. Spring Boot Backend (Port 8081)
```bash
export JAVA_HOME="/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
cd backend
mvn spring-boot:run
```
- API Base: `http://localhost:8081/api/quiz/questions`
- H2 Console: `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:mem:quizdb`, User: `sa`, Password: empty)

### 2. React Frontend (Port 5173)
```bash
cd frontend
npm install
npm run dev
```
Open your browser at `http://localhost:5173` (or `http://127.0.0.1:5173`).
