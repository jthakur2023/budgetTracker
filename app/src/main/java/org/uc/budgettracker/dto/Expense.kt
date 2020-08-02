package org.uc.budgettracker.dto

import com.google.firebase.firestore.DocumentId

data class Expense(@DocumentId var id: String = "", var name: String = "", var cost: Double = 0.0, var budgetId: String = "") {

}