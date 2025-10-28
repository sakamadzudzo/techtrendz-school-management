class ApiEndpoints {
  // Base paths
  static const String auth = '/auth';
  static const String users = '/user';
  static const String students = '/student';
  static const String teachers = '/teacher';
  static const String parents = '/parent';
  static const String classrooms = '/classroom';
  static const String classroomYear = '/classroom/year';
  static const String subjects = '/subject';
  static const String subjectYear = '/subject/year';
  static const String extraCurriculars = '/extra-curricular';
  static const String extraCurricularYear = '/extra-curricular/year';
  static const String dorms = '/dorm';
  static const String dormYear = '/dorm/year';
  static const String schools = '/school';
  static const String coursework = '/coursework';
  static const String marks = '/mark';
  static const String activityPoints = '/activity-point';
  static const String ratings = '/rating';
  static const String reports = '/reports';
  static const String reportsDropdowns = '/reports/dropdowns';

  // Authentication endpoints
  static const String login = '$auth/login';
  static const String register = '$auth/register';
  static const String logout = '$auth/logout';
  static const String refresh = '$auth/refresh';
  static const String forgotPassword = '$auth/forgot-password';
  static const String resetPassword = '$auth/reset-password';
  static const String verifyEmail = '$auth/verify-email';

  // User management
  static const String userSave = '$users/save';
  static const String userViewGetAll = '$users/view/get/all';
  static const String userViewGetAllByFilter = '$users/view/get/all/by/filter';
  static const String userViewGetOne = '$users/view/get/one';
  static const String userViewGetAllByExample = '$users/view/get/all/by/example';
  static const String userChangePassword = '$users/change-password';

  // Student management
  static const String studentSave = '$students/save';
  static const String studentViewGetAll = '$students/view/get/all';
  static const String studentViewGetAllByFilter = '$students/view/get/all/by/filter';
  static const String studentViewGetOne = '$students/view/get/one';
  static const String studentViewGetAllByExample = '$students/view/get/all/by/example';
  static const String studentDropdownGetAll = '$students/dropdown/get/all';
  static const String studentDropdownGetAllByExample = '$students/dropdown/get/all/by/example';
  static const String studentDropdownGetAllForSubject = '$students/dropdown/get/all/for/subject';
  static const String studentDropdownGetAllForClassroom = '$students/dropdown/get/all/for/classroom';

  // Teacher management
  static const String teacherSave = '$teachers/save';
  static const String teacherViewGetAll = '$teachers/view/get/all';
  static const String teacherViewGetAllByFilter = '$teachers/view/get/all/by/filter';
  static const String teacherViewGetOne = '$teachers/view/get/one';
  static const String teacherViewGetAllByExample = '$teachers/view/get/all/by/example';
  static const String teacherDropdownGetAll = '$teachers/dropdown/get/all';

  // Parent management
  static const String parentSave = '$parents/save';
  static const String parentSaveAll = '$parents/save/all';
  static const String parentViewGetAll = '$parents/view/get/all';
  static const String parentViewGetAllByFilter = '$parents/view/get/all/by/filter';
  static const String parentViewGetOne = '$parents/view/get/one';
  static const String parentViewGetOneByPerson = '$parents/view/get/one/by/person';
  static const String parentDropdownGetAll = '$parents/dropdown/get/all';
  static const String parentManageStudents = '$parents/manage/students';

  // Classroom management
  static const String classroomSave = '$classrooms/save';
  static const String classroomGetAll = '$classrooms/get/all';
  static const String classroomGetAllByFilter = '$classrooms/get/all/by/filter';
  static const String classroomGetOne = '$classrooms/get/one';
  static const String classroomGetAllByExample = '$classrooms/get/all/by/example';

  // Classroom Year management
  static const String classroomYearSave = '$classroomYear/save';
  static const String classroomYearViewGetAll = '$classroomYear/view/get/all';
  static const String classroomYearViewGetAllByFilter = '$classroomYear/view/get/all/by/filter';
  static const String classroomYearViewGetOne = '$classroomYear/view/get/one';
  static const String classroomYearViewGetAllByExample = '$classroomYear/view/get/all/by/example';
  static const String classroomYearDropdownGetAll = '$classroomYear/dropdown/get/all';

  // Subject management
  static const String subjectSave = '$subjects/save';
  static const String subjectGetAll = '$subjects/get/all';
  static const String subjectGetAllList = '$subjects/get/all/list';
  static const String subjectGetAllByFilter = '$subjects/get/all/by/filter';
  static const String subjectGetOne = '$subjects/get/one';
  static const String subjectGetAllByExample = '$subjects/get/all/by/example';

  // Subject Year management
  static const String subjectYearSave = '$subjectYear/save';
  static const String subjectYearViewGetAll = '$subjectYear/view/get/all';
  static const String subjectYearViewGetAllByFilter = '$subjectYear/view/get/all/by/filter';
  static const String subjectYearViewGetOne = '$subjectYear/view/get/one';
  static const String subjectYearViewGetAllByExample = '$subjectYear/view/get/all/by/example';
  static const String subjectYearDropdownGetAll = '$subjectYear/dropdown/get/all';

  // Extra-curricular management
  static const String extraCurricularSave = '$extraCurriculars/save';
  static const String extraCurricularGetAll = '$extraCurriculars/get/all';
  static const String extraCurricularGetAllList = '$extraCurriculars/get/all/list';
  static const String extraCurricularGetAllByFilter = '$extraCurriculars/get/all/by/filter';
  static const String extraCurricularGetOne = '$extraCurriculars/get/one';
  static const String extraCurricularGetAllByExample = '$extraCurriculars/get/all/by/example';

  // Extra-curricular Year management
  static const String extraCurricularYearSave = '$extraCurricularYear/save';
  static const String extraCurricularYearViewGetAll = '$extraCurricularYear/view/get/all';
  static const String extraCurricularYearViewGetAllByFilter = '$extraCurricularYear/view/get/all/by/filter';
  static const String extraCurricularYearViewGetOne = '$extraCurricularYear/view/get/one';
  static const String extraCurricularYearViewGetAllByExample = '$extraCurricularYear/view/get/all/by/example';

  // Dorm management
  static const String dormSave = '$dorms/save';
  static const String dormGetAll = '$dorms/get/all';
  static const String dormGetAllList = '$dorms/get/all/list';
  static const String dormGetAllByFilter = '$dorms/get/all/by/filter';
  static const String dormGetOne = '$dorms/get/one';
  static const String dormGetAllByExample = '$dorms/get/all/by/example';

  // Dorm Year management
  static const String dormYearSave = '$dormYear/save';
  static const String dormYearGetAll = '$dormYear/get/all';
  static const String dormYearGetAllByFilter = '$dormYear/get/all/by/filter';
  static const String dormYearGetOne = '$dormYear/get/one';
  static const String dormYearGetAllByExample = '$dormYear/get/all/by/example';
  static const String dormYearDropdownGetAll = '$dormYear/dropdown/get/all';

  // School management
  static const String schoolSave = '$schools/save';
  static const String schoolGetAll = '$schools/get/all';
  static const String schoolGetAllList = '$schools/get/all/list';
  static const String schoolGetAllByFilter = '$schools/get/all/by/filter';
  static const String schoolGetOne = '$schools/get/one';
  static const String schoolGetAllByExample = '$schools/get/all/by/example';

  // Coursework management
  static const String courseworkSave = '$coursework/save';
  static const String courseworkSaveAll = '$coursework/save/all';
  static const String courseworkGetById = '$coursework/get/by/id';
  static const String courseworkGetAll = '$coursework/get/all';
  static const String courseworkGetAllByCourseworkType = '$coursework/get/all/by/coursework/type';
  static const String courseworkGetAllBySubject = '$coursework/get/all/by/subject';
  static const String courseworkGetAllByTypeBySubject = '$coursework/get/all/by/type/by/subject';

  // Marks management
  static const String marksSave = '$marks/save';
  static const String marksSaveAll = '$marks/save/all';
  static const String marksGetById = '$marks/get/by/id';
  static const String marksGetAll = '$marks/get/all';
  static const String marksGetAllByCoursework = '$marks/get/all/by/coursework';
  static const String marksGetAllByStudent = '$marks/get/all/by/student';
  static const String marksGetAllBySubject = '$marks/get/all/by/subject';
  static const String marksGetAllBySubjectByCoursework = '$marks/get/all/by/subject/by/coursework';
  static const String marksGetAllByStudentBySubject = '$marks/get/all/by/student/by/subject';
  static const String marksGetByStudentByCoursework = '$marks/get/by/student/by/coursework';
  static const String marksGetAllBySubjectByCourseworkType = '$marks/get/all/by/subject/by/coursework/type';
  static const String marksGetAllByStudentByCourseworkType = '$marks/get/all/by/student/coursework/type';
  static const String marksGetAllByStudentBySubjectByCourseworkType = '$marks/get/all/by/student/by/subject/by/coursework/type';

  // Activity points management
  static const String activityPointsSave = '$activityPoints/save';
  static const String activityPointsSaveAll = '$activityPoints/save/all';
  static const String activityPointsGetById = '$activityPoints/get/by/id';
  static const String activityPointsGetAll = '$activityPoints/get/all';
  static const String activityPointsGetAllByActivity = '$activityPoints/get/all/by/activity';
  static const String activityPointsGetAllByStudent = '$activityPoints/get/all/by/student';
  static const String activityPointsGetByStudentByActivity = '$activityPoints/get/by/student/by/activity';

  // Ratings management
  static const String ratingsSave = '$ratings/save';
  static const String ratingsSaveAll = '$ratings/save/all';
  static const String ratingsGetOne = '$ratings/get/one';
  static const String ratingsGetAll = '$ratings/get/all';
  static const String ratingsGetAllByActivity = '$ratings/get/all/by/activity';

  // Reports Dropdowns
  static const String reportsDropdownsAll = '$reportsDropdowns/all';
  static const String reportsDropdownsSelective = '$reportsDropdowns/selective';

  // Reports
  static const String reportsPdfMarksStudentClassroomYear = '$reports/pdf/marks/student-classroom-year';
  static const String reportsPdfMarksStudentSubjectYear = '$reports/pdf/marks/student-subject-year';
  static const String reportsPdfMarksStudentYear = '$reports/pdf/marks/student-year';
  static const String reportsPdfMarksStudent = '$reports/pdf/marks/student';
  static const String reportsPdfMarksYear = '$reports/pdf/marks/year';
  static const String reportsPdfMarksClassroomYear = '$reports/pdf/marks/classroom-year';
  static const String reportsPdfMarksSubjectYear = '$reports/pdf/marks/subject-year';
  static const String reportsPdfRatingsStudentActivityYear = '$reports/pdf/ratings/student-activity-year';
  static const String reportsPdfRatingsStudentYear = '$reports/pdf/ratings/student-year';
  static const String reportsPdfRatingsActivityYear = '$reports/pdf/ratings/activity-year';
  static const String reportsPdfRatingsActivity = '$reports/pdf/ratings/activity';
  static const String reportsPdfMarksTeacherYear = '$reports/pdf/marks/teacher-year';
  static const String reportsPdfRatingsTeacherYear = '$reports/pdf/ratings/teacher-year';
  static const String reportsPdfProgressStudent = '$reports/pdf/progress/student';
  static const String reportsPdfProgressTeacher = '$reports/pdf/progress/teacher';
  static const String reportsPdfProgressSubject = '$reports/pdf/progress/subject';
  static const String reportsPdfProgressClassroom = '$reports/pdf/progress/classroom';
  static const String reportsPdfProgressActivity = '$reports/pdf/progress/activity';
  static const String reportsPdfProgressFacilitator = '$reports/pdf/progress/facilitator';
  static const String reportsExcelMarksStudentClassroomYear = '$reports/excel/marks/student-classroom-year';
  static const String reportsExcelMarksStudentSubjectYear = '$reports/excel/marks/student-subject-year';
  static const String reportsExcelMarksStudentYear = '$reports/excel/marks/student-year';
  static const String reportsExcelMarksStudent = '$reports/excel/marks/student';
  static const String reportsExcelMarksYear = '$reports/excel/marks/year';
  static const String reportsExcelMarksClassroomYear = '$reports/excel/marks/classroom-year';
  static const String reportsExcelMarksSubjectYear = '$reports/excel/marks/subject-year';
  static const String reportsExcelRatingsStudentActivityYear = '$reports/excel/ratings/student-activity-year';
  static const String reportsExcelRatingsStudentYear = '$reports/excel/ratings/student-year';
  static const String reportsExcelRatingsActivityYear = '$reports/excel/ratings/activity-year';
  static const String reportsExcelRatingsActivity = '$reports/excel/ratings/activity';
  static const String reportsExcelMarksTeacherYear = '$reports/excel/marks/teacher-year';
  static const String reportsExcelRatingsTeacherYear = '$reports/excel/ratings/teacher-year';
  static const String reportsExcelProgressStudent = '$reports/excel/progress/student';
  static const String reportsExcelProgressTeacher = '$reports/excel/progress/teacher';
  static const String reportsExcelProgressSubject = '$reports/excel/progress/subject';
  static const String reportsExcelProgressClassroom = '$reports/excel/progress/classroom';
  static const String reportsExcelProgressActivity = '$reports/excel/progress/activity';
  static const String reportsExcelProgressFacilitator = '$reports/excel/progress/facilitator';

  // Utility methods
  static String replacePathParam(String endpoint, String param, String value) {
    return endpoint.replaceAll('{$param}', value);
  }

  static String buildUrl(String endpoint, [Map<String, dynamic>? queryParams]) {
    final buffer = StringBuffer(endpoint);

    if (queryParams != null && queryParams.isNotEmpty) {
      buffer.write('?');
      queryParams.forEach((key, value) {
        if (value != null) {
          buffer.write('$key=$value&');
        }
      });
      // Remove trailing '&'
      if (buffer.toString().endsWith('&')) {
        buffer.write(buffer.toString().substring(0, buffer.length - 1));
      }
    }

    return buffer.toString();
  }
}