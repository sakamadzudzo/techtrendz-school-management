package zw.co.techtrendz.school.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.techtrendz.school.management.api.enums.Role;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.enums.Status;
import zw.co.techtrendz.school.management.api.enums.SubjectType;
import zw.co.techtrendz.school.management.api.service.MiscService;

import java.util.List;

@RestController
@RequestMapping("/misc")
public class MiscController {

    @Autowired
    private MiscService miscService;

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return this.miscService.getRoles();
    }

    @GetMapping("/statuses")
    public List<Status> getStatuses() {
        return this.miscService.getStatuses();
    }

    @GetMapping("/salutations")
    public List<Salutation> getSalutations() {
        return this.miscService.getSalutations();
    }

    @GetMapping("/subject-types")
    public List<SubjectType> getSubjectType() {
        return this.miscService.getSubjectTypes();
    }
}
