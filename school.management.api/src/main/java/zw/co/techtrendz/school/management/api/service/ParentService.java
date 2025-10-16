package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.YearDropdown;
import zw.co.techtrendz.school.management.api.entity.Parent;
import zw.co.techtrendz.school.management.api.view.ParentDto;

import java.util.List;

@Service
public interface ParentService {

    Parent save(Parent parent);

    List<Parent> saveAll(List<Parent> parents);

    Parent getById(Long id);

    Parent getByPersonId(Long personId);

    Page<Parent> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    Page<Parent> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    ParentDto getViewById(Long id);

    ParentDto getViewByPersonId(Long personId);

    Page<ParentDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    Page<ParentDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    List<YearDropdown> getAllParentsForDropdown();

    Parent manageStudents(Long parentId, List<Long> studentIds);
}