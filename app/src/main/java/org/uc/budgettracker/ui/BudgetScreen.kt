package org.uc.budgettracker.ui

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.budget_screen.*
import org.uc.budgettracker.R
import org.uc.budgettracker.dto.Budget

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BudgetScreen : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.budget_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAddBudget).setOnClickListener {
            findNavController().navigate(R.id.action_BudgetScreen_to_AddBudget)
        }

        var budgets : ArrayList<Budget> = ArrayList()
        var budget = Budget("Electric Bill", 100.0, 120.0, Budget.TimeInterval.MONTHLY, Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID))

        budgets.add(budget)
        budgets.add(budget)
        budgets.add(budget)
        budgets.add(budget)
        budgets.add(budget)

        // Link data to rvBudgets
        rvBudgets.hasFixedSize()
        rvBudgets.layoutManager = LinearLayoutManager(context)
        rvBudgets.itemAnimator = DefaultItemAnimator()
        rvBudgets.adapter = BudgetsAdapter(budgets, R.layout.budget_layout)
    }


    inner class BudgetsAdapter(val budgets: List<Budget>, val itemLayout: Int) : RecyclerView.Adapter<BudgetScreen.BudgetViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
           val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)

            return BudgetViewHolder(view)
        }

        override fun getItemCount(): Int {
            return budgets.size
        }

        override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
            val budget = budgets.get(position)
            holder.updateBudget(budget)
        }

    }

    inner class BudgetViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var tvBudgetName : TextView = itemView.findViewById(R.id.tvBudgetName)
        private var tvAmountRemaining : TextView = itemView.findViewById(R.id.tvAmountRemaining)

        /**
         * Updates row in recycler view with passed in budget
         */
        fun updateBudget (budget : Budget) {
            tvBudgetName.text = budget.name
            tvAmountRemaining.text = budget.amount.toString()
        }
    }
}