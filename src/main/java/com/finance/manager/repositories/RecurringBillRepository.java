package com.finance.manager.repositories;

import com.finance.manager.models.RecurringBill;
import org.springframework.data.repository.CrudRepository;

public interface RecurringBillRepository extends CrudRepository<RecurringBill, Long> {
}
