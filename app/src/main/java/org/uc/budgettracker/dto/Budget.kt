package org.uc.budgettracker.dto

import com.google.firebase.firestore.DocumentId

data class Budget(@DocumentId var id: String = "", var name: String = "", var amount: Double = 0.0, var income: Double = 0.0, var interval: TimeInterval = TimeInterval.WEEKLY, var deviceId : String = "") {
    enum class TimeInterval {
        WEEKLY, MONTHLY, ANNUALLY
    }

    override fun toString(): String {
        return "$name $amount $income $interval $deviceId"
    }

    fun noBlankFields(): Boolean {
        return (name.isNotBlank() && amount.toString().isNotBlank() && income.toString().isNotBlank())
    }
}