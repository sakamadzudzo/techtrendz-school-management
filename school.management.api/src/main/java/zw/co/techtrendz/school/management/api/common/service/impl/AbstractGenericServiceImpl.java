package zw.co.techtrendz.school.management.api.common.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.common.service.GenericService;

import java.util.List;

// I got tired of writing the same thing over and over again (boilerplate)
// Example useage:
//@Service  // Annotation goes HERE, on the implementation
//public class BankServiceImpl extends AbstractGenericService<Bank, Long> implements BankService {
//
//    private final BankRepository bankRepository; // Specific repository for custom methods
//
//    public BankServiceImpl(BankRepository repository) {
//        super(repository); // Pass to abstract class
//        this.bankRepository = repository;
//    }
//
//    @Override
//    public Page<Bank> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
//        // Custom implementation using repository method
//        // or Specifications, QueryDSL, or Criteria API
//        Sort sort = Sort.by(sortDir, sortBy.toArray(new String[0]));
//        PageRequest pageRequest = PageRequest.of(page, size, sort);
//
//        // Example: using a custom query method
//        return bankRepository.findByNameContainingIgnoreCase(filter, pageRequest);
//    }
//
//    @Override
//    public List<YearDropdown> getAllForDropdown(List<String> sortBy, Sort.Direction sortDir) {
//        // This is highly specific to Bank, so it stays here
//        return bankRepository.findDistinctYearsForDropdown();
//    }
//}
public abstract class AbstractGenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private final JpaRepository<T, ID> repository;

    // Constructor receives the specific repository
    protected AbstractGenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return entities.parallelStream().map(this::save).toList();
    }

    @Override
    public T getById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record with id " + id + "was not found"));
    }

    @Override
    public Page<T> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        Sort sort = Sort.by(sortDir, sortBy.toArray(new String[0]));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return repository.findAll(pageRequest);
    }

    @Override
    public List<T> getAll(List<String> sortBy, Sort.Direction sortDir) {
        Sort sort = Sort.by(sortDir, sortBy.toArray(new String[0]));
        return repository.findAll(sort);
    }

    @Override
    public Page<T> getAllByExample(T example, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (example == null) {
            return this.getAll(page, size, sortBy, sortDir);
        }
        Sort sort = Sort.by(sortDir, sortBy.toArray(new String[0]));
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Requires your repository to extend QueryByExampleExecutor
        // which JpaRepository already does
        Example<T> ex = Example.of(example);
        return repository.findAll(ex, pageRequest);
    }

    @Override
    public List<T> getAllByExample(T example, List<String> sortBy, Sort.Direction sortDir) {
        if (example == null) {
            return this.getAll(sortBy, sortDir);
        }
        Sort sort = Sort.by(sortDir, sortBy.toArray(new String[0]));

        // Requires your repository to extend QueryByExampleExecutor
        // which JpaRepository already does
        Example<T> ex = Example.of(example);
        return repository.findAll(ex, sort);
    }
}
