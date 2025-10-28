# Flutter School Management App Development Plan

## 1. Project Overview

### Purpose and Main Functionality
The Flutter School Management App is a mobile application designed to replicate the core functionality of the existing Next.js web application (@school.management.next). It provides a comprehensive school management system that enables administrators, teachers, staff, parents, and students to manage and access school-related data efficiently.

The app maintains the same user experience and feature flow as the web version while optimizing for mobile interfaces, including touch interactions, responsive design, and offline capabilities where appropriate.

### High-Level Summary of How the Flutter App Will Mirror the Next.js App
- **Authentication & Authorization**: Login, registration, and role-based access (Admin, Teacher, Staff, Parent, Student)
- **Entity Management**: CRUD operations for Students, Parents, Teachers, Staff, Users, Classrooms, Subjects, Extra-Curriculars, Dorms, and Schools
- **Academic Records**: Coursework/Activity points, marks/ratings for subjects and extra-curriculars
- **Reporting**: PDF and Excel report generation with various filters (by classroom, student, subject, teacher, etc.)
- **Profile Management**: User profiles with personal information and role-specific data
- **Navigation**: Role-based landing pages and navigation flows

The Flutter app will consume the same backend APIs as the Next.js version, ensuring data consistency and shared business logic.

For all objects/models, only id is not nullable, all other fields should allow null values.

## 2. Architecture

### Proposed Folder Structure
```
lib/
├── core/
│   ├── config/
│   │   ├── app_config.dart
│   │   ├── environment.dart
│   │   └── api_endpoints.dart
│   ├── constants/
│   │   ├── colors.dart
│   │   ├── strings.dart
│   │   └── dimensions.dart
│   ├── errors/
│   │   ├── exceptions.dart
│   │   └── failure.dart
│   ├── network/
│   │   ├── api_client.dart
│   │   ├── dio_client.dart
│   │   └── interceptors.dart
│   ├── services/
│   │   ├── auth_service.dart
│   │   ├── report_service.dart
│   │   └── storage_service.dart
│   ├── theme/
│   │   ├── app_theme.dart
│   │   └── theme_provider.dart
│   └── utils/
│       ├── date_formatter.dart
│       ├── validators.dart
│       └── helpers.dart
├── data/
│   ├── models/
│   │   ├── user.dart
│   │   ├── student.dart
│   │   ├── teacher.dart
│   │   ├── classroom.dart
│   │   ├── subject.dart
│   │   ├── coursework.dart
│   │   ├── mark.dart
│   │   ├── report.dart
│   │   └── ...
│   ├── repositories/
│   │   ├── auth_repository.dart
│   │   ├── student_repository.dart
│   │   ├── report_repository.dart
│   │   └── ...
│   └── datasources/
│       ├── remote/
│       │   ├── auth_remote_data_source.dart
│       │   └── ...
│       └── local/
│           ├── auth_local_data_source.dart
│           └── ...
├── presentation/
│   ├── providers/
│   │   ├── auth_provider.dart
│   │   ├── student_provider.dart
│   │   └── ...
│   ├── screens/
│   │   ├── auth/
│   │   │   ├── login_screen.dart
│   │   │   ├── register_screen.dart
│   │   │   └── forgot_password_screen.dart
│   │   ├── home/
│   │   │   ├── admin_home_screen.dart
│   │   │   ├── teacher_home_screen.dart
│   │   │   ├── parent_home_screen.dart
│   │   │   └── student_home_screen.dart
│   │   ├── management/
│   │   │   ├── students/
│   │   │   │   ├── student_list_screen.dart
│   │   │   │   ├── student_detail_screen.dart
│   │   │   │   └── student_form_screen.dart
│   │   │   ├── teachers/
│   │   │   ├── classrooms/
│   │   │   ├── subjects/
│   │   │   └── ...
│   │   ├── reports/
│   │   │   ├── report_list_screen.dart
│   │   │   ├── report_filters_screen.dart
│   │   │   └── report_viewer_screen.dart
│   │   └── profile/
│       │   ├── profile_screen.dart
│       │   └── change_password_screen.dart
│   ├── widgets/
│   │   ├── common/
│   │   │   ├── custom_app_bar.dart
│   │   │   ├── custom_button.dart
│   │   │   ├── custom_text_field.dart
│   │   │   ├── loading_indicator.dart
│   │   │   └── error_widget.dart
│   │   ├── forms/
│   │   │   ├── student_form.dart
│   │   │   ├── teacher_form.dart
│   │   │   └── ...
│   │   ├── cards/
│   │   │   ├── student_card.dart
│   │   │   ├── teacher_card.dart
│   │   │   └── ...
│   │   └── lists/
│       │   ├── student_list_item.dart
│       │   └── ...
│   └── blocs/
│       ├── auth/
│       ├── student/
│       └── ...
└── main.dart
```

### State Management Approach
**Bloc Pattern** with **Provider** for dependency injection.

- **Bloc**: For complex state management involving business logic, especially for authentication, data fetching, and form handling
- **Provider**: For simpler state management and dependency injection
- **Riverpod** as an alternative if the team prefers its syntax and features

This approach provides:
- Predictable state changes
- Separation of business logic from UI
- Testability
- Scalability for a complex app with multiple features

### Backend Communication Method
**REST API** with Dio HTTP client.

- **Protocol**: RESTful APIs (same as Next.js app)
- **Client**: Dio for robust HTTP requests with interceptors for authentication, logging, and error handling
- **Serialization**: JSON with built-in Dart JSON support and potentially json_serializable for complex models
- **Error Handling**: Custom exceptions and failure classes for different error types
- **Caching**: Local storage for offline capabilities and performance optimization

## 3. Feature Breakdown

### Authentication & Registration
- **Purpose**: Secure user login and registration with role-based access
- **Key UI Elements**: Login form, registration form, forgot password, role selector
- **Dependencies**: Dio for API calls, shared_preferences for token storage
- **Navigation Flow**: Splash → Login/Register → Role-based home screen

### Student Management
- **Purpose**: CRUD operations for student records
- **Key UI Elements**: Student list, student detail view, student form (add/edit)
- **Dependencies**: Bloc for state management, form validation
- **Navigation Flow**: Student list → Student detail → Edit student

### Teacher Management
- **Purpose**: CRUD operations for teacher records
- **Key UI Elements**: Teacher list, teacher detail view, teacher form
- **Dependencies**: Similar to student management
- **Navigation Flow**: Teacher list → Teacher detail → Edit teacher

### Classroom Management
- **Purpose**: Manage classrooms and classroom-year associations
- **Key UI Elements**: Classroom list, classroom detail, classroom form, year management
- **Dependencies**: Date pickers for year selection
- **Navigation Flow**: Classroom list → Classroom detail → Classroom year management

### Subject Management
- **Purpose**: CRUD for subjects and subject-year associations
- **Key UI Elements**: Subject list, subject detail, subject form, year assignment
- **Dependencies**: Dropdowns for subject types and categories
- **Navigation Flow**: Subject list → Subject detail → Subject year management

### Extra-Curricular Management
- **Purpose**: Manage extra-curricular activities and facilitators
- **Key UI Elements**: Activity list, activity detail, activity form, facilitator assignment
- **Dependencies**: Multi-select for facilitators and students
- **Navigation Flow**: Activity list → Activity detail → Edit activity

### Dorm Management
- **Purpose**: Manage dormitories and dorm-year associations
- **Key UI Elements**: Dorm list, dorm detail, dorm form, year management
- **Dependencies**: Address input fields
- **Navigation Flow**: Dorm list → Dorm detail → Dorm year management

### School Management
- **Purpose**: CRUD for school information
- **Key UI Elements**: School list, school detail, school form
- **Dependencies**: Contact information fields
- **Navigation Flow**: School list → School detail → Edit school

### Coursework & Marks Management
- **Purpose**: Manage coursework assignments and student marks
- **Key UI Elements**: Coursework list, mark entry forms, grade calculators
- **Dependencies**: Number inputs, validation for mark ranges
- **Navigation Flow**: Subject detail → Coursework list → Mark entry

### Activity Points & Ratings Management
- **Purpose**: Track extra-curricular participation and ratings
- **Key UI Elements**: Activity point list, rating forms, progress tracking
- **Dependencies**: Rating scales (1-5 or similar)
- **Navigation Flow**: Activity detail → Activity points → Rating entry

### Reporting System
- **Purpose**: Generate PDF and Excel reports with various filters
- **Key UI Elements**: Report type selector, filter forms, report viewer
- **Dependencies**: PDF viewer, Excel generation libraries, date range pickers
- **Navigation Flow**: Reports → Report type selection → Filters → Report generation → Viewer

### Profile Management
- **Purpose**: User profile viewing and editing
- **Key UI Elements**: Profile view, edit profile form, change password
- **Dependencies**: Image picker for avatars
- **Navigation Flow**: Profile → Edit profile

### Parent Management
- **Purpose**: Manage parent records and student associations
- **Key UI Elements**: Parent list, parent detail, parent form, student linking
- **Dependencies**: Multi-select for associated students
- **Navigation Flow**: Parent list → Parent detail → Edit parent

### User Management
- **Purpose**: Admin-level user account management
- **Key UI Elements**: User list, user detail, user form, role assignment
- **Dependencies**: Role dropdowns, password reset functionality
- **Navigation Flow**: User list → User detail → Edit user

## 4. Dependencies

### Suggested Flutter Packages
- **State Management**: `flutter_bloc: ^8.1.3`, `provider: ^6.0.5`
- **Networking**: `dio: ^5.3.2`, `pretty_dio_logger: ^1.3.1`
- **Local Storage**: `shared_preferences: ^2.2.2`, `hive: ^2.2.3`
- **UI Components**: `flutter_screenutil: ^5.9.0` (responsive), `cached_network_image: ^3.3.0`
- **Forms**: `flutter_form_builder: ^9.1.1`, `form_builder_validators: ^9.1.0`
- **Date/Time**: `intl: ^0.19.0`, `persian_datetime_picker: ^2.7.0`
- **PDF/Excel**: `pdf: ^3.10.4`, `excel: ^2.1.0`, `printing: ^5.12.0`
- **Image Handling**: `image_picker: ^1.0.4`, `flutter_image_compress: ^2.1.0`
- **Icons**: `phosphor_flutter: ^2.0.0` (matching Next.js icons)
- **Themes**: `flex_color_scheme: ^7.3.1`
- **Navigation**: `go_router: ^12.1.1`
- **Serialization**: `json_serializable: ^6.7.1`, `freezed: ^2.4.6`
- **Error Handling**: `dartz: ^0.10.1` (Either monad)
- **Testing**: `bloc_test: ^9.2.1`, `mockito: ^5.4.4`

### Native Integrations
- **File System**: For report storage and offline access
- **Share**: For sharing reports via email/social media
- **Notifications**: Local notifications for reminders (optional)
- **Biometrics**: Fingerprint/Face ID for enhanced security (optional)

## 5. UI/UX Notes

### How to Replicate or Adapt the Existing Design System
- **Color Scheme**: Extract colors from Next.js Tailwind config and create matching Flutter theme
- **Typography**: Use similar font families and sizes, adapting for mobile readability
- **Component Library**: Create custom widgets that mirror Next.js components (buttons, cards, tables)
- **Icons**: Use Phosphor icons to match the Next.js @phosphor-icons/react usage
- **Layout**: Adapt responsive web layouts to mobile-first design with proper spacing and touch targets

### Theming Strategy (Light/Dark Modes, Color Scheme, Typography)
- **Theme Provider**: Implement theme switching with Provider/Bloc
- **Color Palette**: Primary, secondary, accent colors matching Next.js
- **Typography**: Headline, body, caption styles with consistent font sizes
- **Dark Mode**: Full dark theme support with appropriate color adjustments
- **Accessibility**: High contrast options, larger text, screen reader support

## 6. Environment & Config

### How to Handle .env and Secrets Safely
- **Environment Files**: `.env.development`, `.env.staging`, `.env.production`
- **Secrets Management**: Use flutter_dotenv for loading environment variables
- **Build Flavors**: Separate configurations for dev/staging/prod
- **Security**: Never commit secrets to version control, use secure storage for sensitive data

### Setup Instructions (for Dev/Test/Prod)
- **Development**: Local API endpoint, debug logging enabled, hot reload
- **Staging**: Staging API endpoint, production-like settings for testing
- **Production**: Production API endpoint, optimized builds, error reporting

## 7. Milestones / Build Phases

### Phase 1: Foundation & Authentication (Weeks 1-3)
- Set up Flutter project with basic architecture
- Implement authentication (login, register, logout)
- Create basic navigation structure
- Set up state management and API client
- Build role-based home screens

### Phase 2: Core Entity Management (Weeks 4-8)
- Implement CRUD for Students, Teachers, Parents
- Build Classroom, Subject, School management
- Create forms and validation
- Implement search and filtering
- Add offline data caching

### Phase 3: Academic Features (Weeks 9-12)
- Coursework and marks management
- Activity points and ratings
- Year-based associations (ClassroomYear, SubjectYear, etc.)
- Grade calculations and progress tracking

### Phase 4: Reporting System (Weeks 13-16)
- Report generation (PDF/Excel)
- Filter implementation
- Report viewer with sharing capabilities
- Performance optimization for large reports

### Phase 5: Advanced Features & Polish (Weeks 17-20)
- Profile management and settings
- Push notifications (if needed)
- Advanced search and analytics
- UI/UX refinements and testing
- Performance optimization and deployment preparation

### Phase 6: Testing & Deployment (Weeks 21-22)
- Unit and integration testing
- UI testing and bug fixes
- Beta testing and feedback incorporation
- App store deployment (iOS/Android)