package zw.co.techtrendz.school.management.api.common.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.user.enums.Role;
import zw.co.techtrendz.school.management.api.user.enums.Salutation;
import zw.co.techtrendz.school.management.api.common.enums.Status;
import zw.co.techtrendz.school.management.api.academic.enums.SubjectType;

import java.util.List;

@Service
public interface MiscService {

    public List<Role> getRoles();

    public List<Status> getStatuses();

    public List<Salutation> getSalutations();

    public List<SubjectType> getSubjectTypes();
}
