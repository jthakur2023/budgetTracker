package org.uc.budgettracker.ui

import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
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

class AddBudget : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnDone).setOnClickListener {
                val budget: Budget = getBudgetData()
                DatabaseFunctions.saveBudget(budget)
                findNavController().navigate(R.id.action_AddBudget_Done)
        }
    }

    private fun getBudgetData() : Budget {
        var budget = Budget()

        try {
            if(budget.name.isNotEmpty())
                budget.name = ptBudgetName.text.toString()
            budget.amount = ptBudget.text.toString().toDouble()
            budget.income = ptIncome.text.toString().toDouble()
            budget.interval = enumValueOf(spTimeInterval.selectedItem.toString().toUpperCase())
            budget.deviceId = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception){e.printStackTrace()}


        return budget
    }
}