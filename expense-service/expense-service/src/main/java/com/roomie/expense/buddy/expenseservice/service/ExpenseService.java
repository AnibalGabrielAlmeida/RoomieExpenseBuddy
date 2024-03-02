package com.roomie.expense.buddy.expenseservice.service;

import com.roomie.expense.buddy.expenseservice.model.Expense;
import com.roomie.expense.buddy.expenseservice.repository.ExpenseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExpenseService implements IExpenseService{


    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    public void createExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense findExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public Expense updateExpense(Long id, Expense updatedExpense) {
        // Retrieve the existing product from the repository using its ID
        Expense existingExpense = expenseRepository.findById(id).orElse(null);
        // Check if both existing product and updated product are not null
        if (existingExpense != null && updatedExpense != null) {
            // Copy non-null properties from the updated product to the existing product
            existingExpense = copyNonNullProperties(updatedExpense, existingExpense);
            // Save the updated existing product to the repository
            expenseRepository.save(existingExpense);
        }
        // Return the existing product (updated or not)
        return existingExpense;
    }

    // Method to copy non-null properties from a source object to a target object
    private Expense copyNonNullProperties(Expense source, Expense target) {
        // Use BeanUtils.copyProperties() from Spring Framework to copy non-null properties
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        // Return the modified target object
        return target;
    }

    // Method to get the names of null properties of an object
    private String[] getNullPropertyNames(Object source) {
        // Create a BeanWrapper for the source object
        final BeanWrapper src = new BeanWrapperImpl(source);
        // Get all properties of the source object
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        // Create a set to store the names of null properties
        Set<String> emptyNames = new HashSet<>();
        // Iterate over all properties of the source object
        for (PropertyDescriptor pd : pds) {
            // Get the value of the current property
            Object srcValue = src.getPropertyValue(pd.getName());
            // If the value of the property is null, add the property name to the set
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        // Convert the set of null property names into an array of strings
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
