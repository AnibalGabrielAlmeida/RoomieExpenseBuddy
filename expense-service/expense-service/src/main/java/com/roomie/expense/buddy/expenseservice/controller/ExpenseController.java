package com.roomie.expense.buddy.expenseservice.controller;

import com.roomie.expense.buddy.expenseservice.model.Expense;
import com.roomie.expense.buddy.expenseservice.service.IExpenseService;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    IExpenseService expenseService;


    //Refactor to ResponseEntity
    @PostMapping("/create")
    public ResponseEntity<Void> createExpense(@RequestBody Expense expense) {
        try {
            expenseService.createExpense(expense);

            return ResponseEntity.created(new URI("/expenses/" + expense.getId())).build();
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Expense>> listExpenses(){
        List<Expense> expenses = expenseService.getExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id){
        Expense expense = expenseService.findExpenseById(id);
        if (expense != null){
            return ResponseEntity.ok(expense);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id,
                                                 @RequestBody Expense updatedExpense) {
        Expense expense = expenseService.updateExpense(id, updatedExpense);
        if (expense != null) {
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
