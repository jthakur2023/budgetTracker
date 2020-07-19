package org.uc.budgettracker.dto

data class Budget(var name: String, var budgetAmt: Double, var incomeAmt: Double, var interval: TimeInterval) {
    enum class TimeInterval {
        WEEKLY, MONTHLY, YEARLY
    }

    override fun toString(): String {
        return super.toString()
    }
}