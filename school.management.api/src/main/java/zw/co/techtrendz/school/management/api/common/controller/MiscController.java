package zw.co.techtrendz.school.management.api.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.techtrendz.school.management.api.user.enums.Role;
import zw.co.techtrendz.school.management.api.user.enums.Salutation;
import zw.co.techtrendz.school.management.api.common.enums.Status;
import zw.co.techtrendz.school.management.api.academic.enums.SubjectType;
import zw.co.techtrendz.school.management.api.common.service.MiscService;

import java.util.List;

@RestController
@RequestMapping("/misc")
@Tag(name = "Miscellaneous", description = "Miscellaneous utility endpoints for enums and constants")
public class MiscController {

    private final MiscService miscService;

    public MiscController(MiscService miscService) {
        this.miscService = miscService;
    }

    @Operation(
            summary = "Get all roles",
            description = "Retrieves all available user roles in the system"
    )
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return this.miscService.getRoles();
    }

    @Operation(
            summary = "Get all statuses",
            description = "Retrieves all available entity statuses in the system"
    )
    @GetMapping("/statuses")
    public List<Status> getStatuses() {
        return this.miscService.getStatuses();
    }

    @Operation(
            summary = "Get all salutations",
            description = "Retrieves all available salutations for names"
    )
    @GetMapping("/salutations")
    public List<Salutation> getSalutations() {
        return this.miscService.getSalutations();
    }

    @Operation(
            summary = "Get all subject types",
            description = "Retrieves all available subject types in the system"
    )
    @GetMapping("/subject-types")
    public List<SubjectType> getSubjectType() {
        return this.miscService.getSubjectTypes();
    }
}
