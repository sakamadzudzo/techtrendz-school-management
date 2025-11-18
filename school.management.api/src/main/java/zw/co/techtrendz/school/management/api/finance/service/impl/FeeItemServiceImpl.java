package zw.co.techtrendz.school.management.api.finance.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.common.service.impl.AbstractGenericServiceImpl;
import zw.co.techtrendz.school.management.api.finance.entity.FeeItem;
import zw.co.techtrendz.school.management.api.finance.repository.FeeItemRepository;
import zw.co.techtrendz.school.management.api.finance.service.FeeItemService;

@Service
public class FeeItemServiceImpl extends AbstractGenericServiceImpl<FeeItem, Long> implements FeeItemService {

    public FeeItemServiceImpl(FeeItemRepository feeItemRepository) {
        super(feeItemRepository);
    }
}
