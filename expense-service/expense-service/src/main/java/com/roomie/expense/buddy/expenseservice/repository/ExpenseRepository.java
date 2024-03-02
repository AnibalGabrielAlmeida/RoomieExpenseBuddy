package com.roomie.expense.buddy.expenseservice.repository;

import com.roomie.expense.buddy.expenseservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long > {

}
