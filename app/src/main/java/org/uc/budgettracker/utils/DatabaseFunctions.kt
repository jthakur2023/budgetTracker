package org.uc.budgettracker.utils

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import org.uc.budgettracker.dto.Budget
import org.uc.budgettracker.dto.Expense

class DatabaseFunctions {
    companion object {
        lateinit var firestore : FirebaseFirestore
        private var budgetCollectionPath = "Budget"
        private var expensesCollectionPath = "Expenses"

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
         * Update budget in the firebase database
         *
         * @param budget Budget data object with budget data
         *
         * @return Boolean indicating success or failure
         */
        fun updateBudget(budget: Budget) : Boolean {
            return firestore.collection(budgetCollectionPath)
                .document(budget.id)
                .set(budget)
                .isSuccessful
        }

        /**
         * Delete budget in the firebase database
         *
         * @param budget Budget data object with budget data
         *
         * @return Boolean indicating success or failure
         */
        fun deleteBudget(budget: Budget) : Boolean {
            return firestore.collection(budgetCollectionPath)
                .document(budget.id)
                .delete()
                .isSuccessful
        }

        /**
         * Save Expense data to the firebase database
         *
         * @param expense Expense data object with expense data
         *
         * @return Boolean indicating success or failure
         */
        fun saveExpense(expense: Expense) : Boolean {
            return firestore.collection(expensesCollectionPath)
                .document()
                .set(expense)
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
            var budgetsCollection = firestore.collection(budgetCollectionPath).whereEqualTo("deviceId", deviceId)

            budgetsCollection.addSnapshotListener { value: QuerySnapshot?, error: FirebaseFirestoreException? ->
                var innerBudgets = value?.toObjects(Budget::class.java)
                budgets.postValue(innerBudgets)
            }

            return budgets
        }

        fun fetchExpenses(budgetId: String): MutableLiveData<List<Expense>> {
            var expenses = MutableLiveData<List<Expense>>()
            var expensesCollection = firestore.collection(expensesCollectionPath).whereEqualTo("budgetId", budgetId)

            expensesCollection.addSnapshotListener { value: QuerySnapshot?, error: FirebaseFirestoreException? ->
                var innerExpenses = value?.toObjects(Expense::class.java)
                expenses.postValue(innerExpenses)
            }

            return expenses
        }
    }
}