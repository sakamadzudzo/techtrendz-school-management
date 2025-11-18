package zw.co.techtrendz.school.management.api.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

// I got tired of writing the same thing over and over again (boilerplate)
// Example useage:
// public interface BankService extends GenericService<Bank, Long> {
//      // BankService-specific methods only
// }
public interface GenericService<T, ID> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

    T getById(ID id);

    Page<T> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    List<T> getAll(List<String> sortBy, Sort.Direction sortDir);

    Page<T> getAllByExample(T example, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    List<T> getAllByExample(T example, List<String> sortBy, Sort.Direction sortDir);
}
