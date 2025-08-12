package zw.co.techtrendz.school.management.api.config.envers;

import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.service.UserService;

@Component
public class AuditUserListener implements RevisionListener {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        UserService userService = applicationContext.getBean(UserService.class);
        final AuditEntity auditEntity = (AuditEntity) revisionEntity;
        User user = new User(0);
        user.setUsername("SYSTEM");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String currentUsername = authentication.getName();
            User loadedUser = userService.getByUsername(currentUsername);
            if (loadedUser != null) {
                user = loadedUser;
            }
        }
        auditEntity.setUserId(user.getId());
        auditEntity.setUsername(user.getUsername().toUpperCase());
    }

}
