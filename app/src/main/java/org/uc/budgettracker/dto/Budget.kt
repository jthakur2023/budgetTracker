package org.uc.budgettracker.dto

data class Budget(var name: String, var budgetAmt: Double, var incomeAmt: Double, var interval: TimeInterval) {
    enum class TimeInterval {
        // DAILY, WEEKLY, MONTHLY, YEARLY
        //commented out this line until the dto is utilized
    }

    //function not used
    override fun toString(): String {
        return super.toString()
    }
}