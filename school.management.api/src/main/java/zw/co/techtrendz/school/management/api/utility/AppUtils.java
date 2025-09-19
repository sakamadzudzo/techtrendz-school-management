package zw.co.techtrendz.school.management.api.utility;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.token.Sha512DigestUtils;
import zw.co.techtrendz.school.management.api.entity.Person;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.enums.Salutation;
import zw.co.techtrendz.school.management.api.exception.UserCreationException;
import zw.co.techtrendz.school.management.api.service.UserService;
import zw.co.techtrendz.school.management.api.view.UserDto;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Random;

public class AppUtils {

    public static String getClientFingerprint(HttpServletRequest request) {
        String ip = AppUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String language = request.getHeader("Accept-Language");

        return Sha512DigestUtils.shaHex(ip + userAgent + language);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void validateUser(User user, UserService userService) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new UserCreationException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserCreationException("Password cannot be null or empty");
        }
        if (user.getPerson() == null) {
            throw new UserCreationException("KYC details missing");
        }
        if (user.getPerson().getEmail() == null || user.getPerson().getEmail().isEmpty()) {
            throw new UserCreationException("Email cannot be null or empty");
        }
        if (userService.existsByEmail(user.getPerson().getEmail(), user.getId())) {
            throw new UserCreationException("Email already in use");
        }
        if (userService.existsByUsername(user.getUsername(), user.getId())) {
            throw new UserCreationException("Username already in use");
        }
    }

    public static String getFullname(Salutation salutation, String firstName, String otherNames, String lastName) {
        StringBuilder fullname = new StringBuilder();
        if (salutation != null) {
            fullname.append(salutation.getValue()).append(".");
        }
        if (firstName != null && !firstName.isBlank()) {
            fullname.append(fullname.isEmpty() ? firstName : " " + firstName);
        }
        if (otherNames != null && !otherNames.isBlank()) {
            fullname.append(fullname.isEmpty() ? otherNames : " " + otherNames);
        }
        if (lastName != null && !lastName.isBlank()) {
            fullname.append(fullname.isEmpty() ? lastName : " " + lastName);
        }

        return fullname.toString();
    }

    public static String getName(String firstName, String otherNames, String lastName) {
        StringBuilder name = new StringBuilder();
        if (firstName != null && !firstName.isBlank()) {
            name.append(name.isEmpty() ? firstName : " " + firstName);
        }
        if (otherNames != null && !otherNames.isBlank()) {
            name.append(name.isEmpty() ? otherNames : " " + otherNames);
        }
        if (lastName != null && !lastName.isBlank()) {
            name.append(name.isEmpty() ? lastName : " " + lastName);
        }

        return name.toString();
    }

    public static User userDtoToEntity(UserDto userDto) {
        User user = new User();
        Person person = new Person();

        person.setId(userDto.getPersonId());
        person.setSalutation(userDto.getSalutation());
        person.setFirstName(userDto.getFirstName());
        person.setOtherNames(userDto.getOtherNames());
        person.setLastName(userDto.getLastName());
        person.setEmail(userDto.getEmail());
        person.setMobileNumber(userDto.getMobileNumber());
        person.setMobileNumber2(userDto.getMobileNumber2());
        person.setTelNumber(userDto.getTelNumber());
        person.setWhatsapp(userDto.getWhatsapp());
        person.setFacebook(userDto.getFacebook());
        person.setLinkedin(userDto.getLinkedin());
        person.setDateOfBirth(userDto.getDateOfBirth());
        user.setPerson(person);

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setVerified(userDto.getVerified());
        user.setEnabled(userDto.getEnabled());
        user.setChangePassword(userDto.getChangePassword());
        user.setRoles(userDto.getRoles());

        return user;
    }

    public static UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setVerified(user.getVerified());
        userDto.setEnabled(user.getEnabled());
        userDto.setChangePassword(user.getChangePassword());
        userDto.setRoles(user.getRoles());
        userDto.setStatus(user.getStatus());
        userDto.setDatestamp(user.getDatestamp());

        Person person = user.getPerson();
        if (person != null) {
            userDto.setPersonId(person.getId());
            userDto.setSalutation(person.getSalutation());
            userDto.setFirstName(person.getFirstName());
            userDto.setOtherNames(person.getOtherNames());
            userDto.setLastName(person.getLastName());
            userDto.setDateOfBirth(person.getDateOfBirth());
            userDto.setEmail(person.getEmail());
            userDto.setMobileNumber(person.getMobileNumber());
            userDto.setMobileNumber2(person.getMobileNumber2());
            userDto.setTelNumber(person.getTelNumber());
            userDto.setWhatsapp(person.getWhatsapp());
            userDto.setFacebook(person.getFacebook());
            userDto.setLinkedin(person.getLinkedin());
        }

        return userDto;
    }

    public static <T> BooleanExpression getBooleanExpression(String filter, BooleanExpression predicate, Class<T> entityClass, String alias, List<String> skipFields) {
        for (var field : entityClass.getDeclaredFields()) {
            field.setAccessible(true); // Ensure private fields are accessible
            if (skipFields != null && skipFields.contains(field.getName())) {
                continue; // Skip the field if it's in the skipFields list
            }
            if (field.getType().equals(String.class)) {
                String[] fieldNameParts = field.getName().split("\\.");
                String fieldName = fieldNameParts[fieldNameParts.length - 1];
                predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.likeMatch(entityClass, fieldName, filter, alias), false);
            } else if (field.getType().isAnnotationPresent(jakarta.persistence.Entity.class)) { // Check if the field is a child entity
                String[] fieldNameParts = field.getName().split("\\.");
                String fieldName = fieldNameParts[fieldNameParts.length - 1];
                predicate = AppUtils.getBooleanExpression(filter, predicate, field.getType(), alias + "." + fieldName, skipFields);
            }
        }
        return predicate;
    }

    public static LocalDate getRandom(LocalDate start, LocalDate end) {
        int year = AppUtils.getRandom(start.getYear(), end.getYear() + 1);
        int month;

        if (start.getYear() == end.getYear()) {
            month = AppUtils.getRandom(start.getMonthValue(), end.getMonthValue() + 1);
        } else if (year == start.getYear()) {
            month = AppUtils.getRandom(start.getMonthValue(), 13);
        } else if (year == end.getYear()) {
            month = AppUtils.getRandom(1, end.getMonthValue() + 1);
        } else {
            month = AppUtils.getRandom(1, 13);
        }

        int day;
        Month selectedMonth = Month.of(month);

        if (selectedMonth == Month.FEBRUARY) {
            day = AppUtils.getRandom(1, Year.isLeap(year) ? 30 : 29); // 30 exclusive
        } else {
            day = AppUtils.getRandom(1, selectedMonth.length(Year.isLeap(year)) + 1);
        }

        LocalDate randomDate = LocalDate.of(year, month, day);

        if (randomDate.isBefore(start)) {
            return start;
        } else if (randomDate.isAfter(end)) {
            return end;
        } else {
            return randomDate;
        }
    }


    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
