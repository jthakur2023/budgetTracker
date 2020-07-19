package org.uc.budgettracker.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.uc.budgettracker.dto.Budget

class DatabaseFunctions {
    companion object{
        lateinit var database: DatabaseReference

        fun saveBudget(budget: Budget){
            database = FirebaseDatabase.getInstance().getReference("/Budget")

            val budgetId = database.push().key!!
            database.child(budgetId).setValue(budget)
        }
    }
}