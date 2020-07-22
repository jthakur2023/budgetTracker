package org.uc.budgettracker.ui

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_budget.*
import org.uc.budgettracker.R
import org.uc.budgettracker.dto.Budget
import org.uc.budgettracker.utils.DatabaseFunctions
import org.uc.budgettracker.utils.NumberValidation.Companion.isNumber

class AddBudget : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** Inflate the layout for this fragment */
        return inflater.inflate(R.layout.add_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnDone).setOnClickListener {
            if(inputValid()) {
                var budget: Budget = getBudgetData()
                DatabaseFunctions.saveBudget(budget)
                /** let user know that the budget saved */
                Toast.makeText(context, "Budget Saved Successfully!", Toast.LENGTH_SHORT)
                findNavController().navigate(R.id.action_AddBudget_Done)
            }
            else {
                /** changed to provide the user with more details about what the error is */
                Toast.makeText(context, "Please verify your input is correct; only numeric values are accepted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBudgetData() : Budget {
        var budget = Budget()

        budget.amount = ptBudget.text.toString().toDouble()
        budget.income = ptIncome.text.toString().toDouble()
        budget.name = ptBudgetName.text.toString()
        budget.interval = enumValueOf(spTimeInterval.selectedItem.toString().toUpperCase())
        budget.deviceId = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)

        return budget
    }

    private fun inputValid() : Boolean {
        var amountValue = ptBudget.text.toString()
        var incomeValue = ptIncome.text.toString()

        return isNumber(amountValue) && isNumber(incomeValue)
    }
}