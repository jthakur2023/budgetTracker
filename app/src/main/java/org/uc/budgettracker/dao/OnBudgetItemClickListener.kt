package org.uc.budgettracker.dao

import org.uc.budgettracker.dto.Budget

interface OnBudgetItemClickListener {
    fun onItemClick(budget: Budget, position: Int)
}