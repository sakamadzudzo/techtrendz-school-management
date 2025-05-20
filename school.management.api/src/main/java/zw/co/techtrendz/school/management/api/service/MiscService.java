package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.enums.Role;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.Status;
import zw.co.techtrendz.school.management.api.enums.SubjectType;

import java.util.List;

@Service
public interface MiscService {

    public List<Role> getRoles();

    public List<Status> getStatuses();

    public List<Salutation> getSalutations();

    public List<SubjectType> getSubjectTypes();
}
