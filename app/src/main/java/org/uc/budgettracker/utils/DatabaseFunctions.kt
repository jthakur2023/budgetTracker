package org.uc.budgettracker.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.uc.budgettracker.dto.Budget

class DatabaseFunctions {
    companion object {
        lateinit var firestore : FirebaseFirestore

        init {
            firestore = FirebaseFirestore.getInstance()
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        }

        /**
         * Save budget data to the firebase database
         *
         * @param budget Budget data object with budget data
         *
         * @return Boolean indicating success or failure
         */
        fun saveBudget(budget: Budget) : Boolean {
            var success = false

            firestore.collection("Budget")
                .document()
                .set(budget)
                .addOnSuccessListener {
                    success = true
                }
                .addOnFailureListener {
                    success = false
                }

            return success
        }
    }
}