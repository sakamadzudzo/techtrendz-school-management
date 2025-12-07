/// Enum definitions matching the backend API enums
/// These enums correspond to the Java enums in the school.management.api package

enum Role {
  ADMIN,
  TEACHER,
  STUDENT,
  PARENT,
  STAFF,
  GUEST;

  String get displayName {
    switch (this) {
      case Role.ADMIN:
        return 'Admin';
      case Role.TEACHER:
        return 'Teacher';
      case Role.STUDENT:
        return 'Student';
      case Role.PARENT:
        return 'Parent';
      case Role.STAFF:
        return 'Staff';
      case Role.GUEST:
        return 'Guest';
    }
  }

  static Role fromString(String role) {
    return Role.values.firstWhere(
      (e) => e.name.toLowerCase() == role.toLowerCase(),
      orElse: () => Role.GUEST,
    );
  }
}

enum Status {
  ACTIVE,
  INACTIVE,
  SUSPENDED,
  DELETED,
  PENDING,
  APPROVED,
  REJECTED,
  BLOCKED,
  UNBLOCKED,
  ENABLED,
  DISABLED;

  String get displayName {
    switch (this) {
      case Status.ACTIVE:
        return 'Active';
      case Status.INACTIVE:
        return 'Inactive';
      case Status.SUSPENDED:
        return 'Suspended';
      case Status.DELETED:
        return 'Deleted';
      case Status.PENDING:
        return 'Pending';
      case Status.APPROVED:
        return 'Approved';
      case Status.REJECTED:
        return 'Rejected';
      case Status.BLOCKED:
        return 'Blocked';
      case Status.UNBLOCKED:
        return 'Unblocked';
      case Status.ENABLED:
        return 'Enabled';
      case Status.DISABLED:
        return 'Disabled';
    }
  }

  static Status fromString(String status) {
    return Status.values.firstWhere(
      (e) => e.name.toLowerCase() == status.toLowerCase(),
      orElse: () => Status.INACTIVE,
    );
  }
}

enum Salutation {
  MR('Mr'),
  MRS('Mrs'),
  MS('Ms'),
  DR('Dr'),
  PROF('Prof'),
  ENG('Eng'),
  REV('Rev'),
  SNR('Snr'),
  JNR('Jnr');

  const Salutation(this.value);

  final String value;

  String get displayName => value;

  static Salutation fromString(String salutation) {
    return Salutation.values.firstWhere(
      (e) => e.name.toLowerCase() == salutation.toLowerCase(),
      orElse: () => Salutation.MR,
    );
  }
}

enum Grade {
  A_P('A+'),
  A('A'),
  A_M('A-'),
  B_P('B+'),
  B('B'),
  B_M('B-'),
  C_P('C+'),
  C('C'),
  C_M('C-'),
  D_P('D+'),
  D('D'),
  D_M('D-'),
  E('E'),
  F('F'),
  U('U');

  const Grade(this.value);

  final String value;

  String get displayName => value;

  static Grade fromString(String grade) {
    return Grade.values.firstWhere(
      (e) => e.name.toUpperCase() == grade.toUpperCase(),
      orElse: () => Grade.U,
    );
  }
}