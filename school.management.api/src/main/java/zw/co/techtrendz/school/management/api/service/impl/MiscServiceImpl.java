package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.enums.Role;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.Status;
import zw.co.techtrendz.school.management.api.enums.SubjectType;
import zw.co.techtrendz.school.management.api.service.MiscService;

import java.util.Arrays;
import java.util.List;

@Service
public class MiscServiceImpl implements MiscService {
    @Override
    public List<Role> getRoles() {
        return Arrays.stream(Role.values()).toList();
    }

    @Override
    public List<Status> getStatuses() {
        return Arrays.stream(Status.values()).toList();
    }

    @Override
    public List<Salutation> getSalutations() {
        return Arrays.stream(Salutation.values()).toList();
    }

    @Override
    public List<SubjectType> getSubjectTypes() {
        return Arrays.stream(SubjectType.values()).toList();
    }
}
