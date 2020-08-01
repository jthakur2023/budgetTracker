package org.uc.budgettracker.utils

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import org.uc.budgettracker.dto.Budget

class DatabaseFunctions {
    companion object {
        lateinit var firestore : FirebaseFirestore
        private var budgetCollectionPath = "Budget"

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
           return firestore.collection(budgetCollectionPath)
                .document()
                .set(budget)
                .isSuccessful
        }

        /**
         * Fetches budgets associated with the current device
         *
         * @param deviceId String value representing unique id attached to device
         *
         * @return Mutable live data collection of the queried data
         */
        fun fetchBudgets(deviceId : String) : MutableLiveData<List<Budget>> {
            var budgets = MutableLiveData<List<Budget>>()
            var budgetsCollection = firestore.collection("Budget").whereEqualTo("deviceId", deviceId)

            budgetsCollection.addSnapshotListener { value: QuerySnapshot?, error: FirebaseFirestoreException? ->
                var innerBudgets = value?.toObjects(Budget::class.java)
                budgets.postValue(innerBudgets)
            }

            return budgets
        }
    }
}