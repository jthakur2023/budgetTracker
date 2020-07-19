package org.uc.budgettracker.dto

data class Budget(var name: String = "", var amount: Double = 0.0, var income: Double = 0.0, var interval: TimeInterval = TimeInterval.WEEKLY, var deviceId : String = "") {
    enum class TimeInterval {
        WEEKLY, MONTHLY, ANNUALLY
    }

    override fun toString(): String {
        return super.toString()
    }
}