package com.roomie.expense.buddy.expenseservice.service;

import com.roomie.expense.buddy.expenseservice.model.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IExpenseService {
    public void createExpense(Expense expense);
    public List<Expense> getExpenses();
    public Expense findExpenseById(Long id);
    public Expense updateExpense(Long id, Expense expense);
    public void deleteExpense (Long id);


}
