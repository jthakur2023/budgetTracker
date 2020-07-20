package org.uc.budgettracker.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.uc.budgettracker.dto.Budget

class DatabaseFunctions {
    companion object {
        private lateinit var database: DatabaseReference

        /**
         * Save budget data to the firebase database
         *
         * @param budget Budget data object that has different information such as name, amount,
         * income, interval, and deviceId
         */
        internal fun saveBudget(budget: Budget){
            database = FirebaseDatabase.getInstance().getReference("/Budget")

            val budgetId = database.push().key!!
            database.child(budgetId).setValue(budget)
        }
    }
}