You are an AI software planner.  
Your task is to generate a **detailed Markdown project plan** for a Flutter application that replicates the functionality of my existing Next.js application.

### Context
- The current app (@school.management.next) is built with **Next.js**.
- It includes the following core features:
  - Authentication and registration
  - Student, Parent, Teacher, Staff, and user recording
  - Classroom recording
  - Subjects recording
  - Extra curriculars recording
  - Dorm, School recording
  - Respective object year for classroom, subject, extra curricula, dorm
  - Coursework/Activity points and marks/ratings for subjects/extra curriculars
  - PDF and Excel reports as defined in reportConfig.ts
  - Profile management
  - Student, parent, teacher, staff management
  - Classroom, subject, extra curricular, dorm, school management
  - Coursework, activity points, mark, rating management
- The goal is to **rebuild this app in Flutter**, maintaining the same user experience and feature flow.
- Assume the Flutter app will consume the same backend APIs and follow similar navigation flows as the current web version.

### Task
Create a **Flutter app development plan** in **Markdown format** that includes:

1. **Project Overview**
   - Purpose and main functionality.
   - High-level summary of how the Flutter app will mirror the Next.js app.

2. **Architecture**
   - Proposed folder structure (e.g. `lib/screens`, `lib/widgets`, `lib/services`, etc.)
   - State management approach (e.g. Riverpod, Provider, Bloc, etc.)
   - Backend communication method (REST, GraphQL, WebSocket, etc.)

3. **Feature Breakdown**
   - List each main feature or screen.
   - For each, describe:
     - Purpose
     - Key UI elements
     - Dependencies or libraries needed
     - Navigation flow

4. **Dependencies**
   - Suggested Flutter packages (with short justifications).
   - Any native integrations (Firebase, maps, notifications, etc.)

5. **UI/UX Notes**
   - How to replicate or adapt the existing design system.
   - Theming strategy (light/dark modes, color scheme, typography).

6. **Environment & Config**
   - How to handle `.env` and secrets safely.
   - Setup instructions (for dev/test/prod).

7. **Milestones / Build Phases**
   - Suggested order for implementation.
   - Incremental checkpoints (e.g. Phase 1: Authentication; Phase 2: UI components; Phase 3: Backend integration).

### Output Format
Produce the result **strictly in Markdown**, well-formatted and ready for review and follow-up.  
Do not generate any Flutter code yet — only the structured plan.
