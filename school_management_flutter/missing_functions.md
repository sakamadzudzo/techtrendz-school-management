# Missing Functions and Unimplemented Features

## Dashboard Navigation (High Priority)

### Admin Dashboard
- Student management navigation
- Teacher management navigation
- Classroom management navigation
- Subject management navigation
- Reports navigation
- Settings navigation

### Teacher Dashboard
- My classes navigation
- Student list navigation
- Coursework management navigation
- Marks entry navigation
- Reports navigation

### Student Dashboard
- Grades view navigation
- Schedule view navigation
- Assignments view navigation
- Activities view navigation
- Messages navigation

### Parent Dashboard
- Children list navigation
- Academic progress navigation
- Reports navigation
- Activities navigation
- Messages navigation

### Staff Dashboard
- Student management navigation
- Teacher management navigation
- Classroom management navigation
- Subject management navigation
- Reports navigation

## Authentication & Security

### Token Management
- **File**: `core/network/interceptors.dart:32`
- Token refresh logic implementation
- Automatic token renewal on 401 errors

### Password Management
- **File**: `presentation/screens/profile/change_password/change_password_screen.dart:240`
- Password change API integration
- Current password verification

### Forgot Password
- **File**: `presentation/widgets/forms/forgot_password_form.dart:31`
- Forgot password API call
- Email verification for password reset

## Configuration

### Environment Detection
- **File**: `core/network/dio_client.dart:38`
- Replace hardcoded `true` with proper environment check (dev/staging/prod)
- Environment-based logging configuration

## Missing Screens & Features

### Subject Management (Complete Module Missing)
- Subject list screen
- Subject detail screen
- Subject form screen (create/edit)
- Subject BLoC integration
- Subject repository implementation
- Subject remote data source

### Coursework Management
- Coursework list screen
- Coursework detail screen
- Coursework form screen
- Teacher coursework assignment interface

### Marks & Grading
- Marks entry screen for teachers
- Grade book view
- Student marks view
- Marks calculation and aggregation

### Activities & Extra-Curricular
- Activity points management screens
- Extra-curricular activities screens
- Student activity tracking
- Activity ratings interface

### Dormitory Management
- Dorm list screen
- Dorm detail screen
- Dorm assignment interface
- Student dorm allocation

### Messages/Communication
- Messaging system (teacher-student-parent)
- Notification center
- Announcements board

### Schedule Management
- Class schedule view
- Teacher timetable
- Student timetable
- Room allocation schedule

### Settings
- App settings screen
- User preferences
- Theme customization
- Notification settings

### Profile Management
- **File**: `presentation/screens/profile/profile/profile_screen.dart:226`
- Edit profile screen navigation
- Profile update functionality

## Missing BLoC Integrations

### Subject BLoC
- Subject state management
- Subject CRUD operations
- Subject filtering and search

### Coursework BLoC
- Already exists but needs screen integration

### Mark BLoC
- Already exists but needs screen integration

### Activity Point BLoC
- Already exists but needs screen integration

### Rating BLoC
- Already exists but needs screen integration

### Dorm BLoC
- BLoC creation needed
- Screen integration needed

## Missing Data Sources

### Remote Data Sources
- Subject remote data source implementation
- Coursework remote data source implementation
- Mark remote data source implementation
- Activity point remote data source implementation
- Dorm remote data source implementation
- Extra-curricular remote data source implementation

## API Integration Gaps

### Missing API Endpoints Integration
- Subject CRUD endpoints
- Coursework CRUD endpoints
- Marks entry endpoints
- Activity tracking endpoints
- Messaging endpoints
- Schedule management endpoints
- Notification endpoints

## Missing Widgets

### Common Widgets
- Grade display widget
- Schedule calendar widget
- Activity timeline widget
- Message thread widget
- Notification badge widget

### Form Widgets
- Subject form fields
- Coursework form fields
- Marks entry form
- Activity form fields

## Missing Utilities

### Validators
- Grade validation
- Schedule conflict validation
- Activity point validation

### Formatters
- Grade formatting
- Schedule time formatting
- Activity point formatting

## Priority Recommendations

### Phase 1 (Critical)
1. Implement dashboard navigation for all user roles
2. Complete subject management module
3. Implement password change functionality
4. Add token refresh logic

### Phase 2 (High)
1. Coursework management screens
2. Marks entry and viewing
3. Schedule management
4. Profile editing

### Phase 3 (Medium)
1. Messaging system
2. Activity tracking
3. Dormitory management
4. Settings screen

### Phase 4 (Low)
1. Advanced reporting features
2. Notification system
3. Theme customization
4. Advanced search and filters
