package org.uc.budgettracker.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_budget.*
import org.uc.budgettracker.R
import org.uc.budgettracker.dto.Budget
import org.uc.budgettracker.dto.Expense
import org.uc.budgettracker.utils.DatabaseFunctions


class ViewBudget() : Fragment() {

    private var _expenses = MutableLiveData<List<Expense>>()
    private var expenseList = ArrayList<Expense>()

    companion object {
        var formBudget: Budget = Budget()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnDoneViewBudget).setOnClickListener {
            findNavController().navigate(R.id.action_ViewBudget_Done)
        }

        view.findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            addExpense(view)
        }

        view.findViewById<Button>(R.id.btnDeleteBudget).setOnClickListener {
            DatabaseFunctions.deleteBudget(formBudget)
            findNavController().navigate(R.id.action_ViewBudget_Done)
        }

        formBudget = BudgetScreen.selectedBudget
        loadBudgetData(formBudget)

        setupRvExpenses()
    }

    private fun setupRvExpenses() {
        _expenses = DatabaseFunctions.fetchExpenses(formBudget.id)

        expenses.observe(viewLifecycleOwner, Observer {
                expenses ->
            expenseList.removeAll(expenseList)
            expenseList.addAll(expenses)

            rvExpenses.adapter!!.notifyDataSetChanged()
        })

        // Link data to rvBudgets
        rvExpenses.hasFixedSize()
        rvExpenses.layoutManager = LinearLayoutManager(context)
        rvExpenses.itemAnimator = DefaultItemAnimator()
        rvExpenses.adapter = ExpensesAdapter(expenseList, R.layout.budget_layout)
    }

    private fun loadBudgetData(budget: Budget) {
        val adapter = spTimeInterval.adapter as ArrayAdapter<String>

        txtBudgetName.text = budget.name
        txtAmountRemaining.text = "$ ${budget.amount}"
        spTimeInterval.setSelection(adapter.getPosition(formatIntervalText(budget.interval)))
    }

    private fun formatIntervalText(interval: Budget.TimeInterval) : String {
        var result = interval.toString().toLowerCase()

        result = result[0].toUpperCase() + result.substring(1)

        return result
    }

    fun addExpense(view: View){
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add_expense, null)
        val etExpenseName = dialogLayout.findViewById<EditText>(R.id.etExpenseName)
        val etExpenseCost = dialogLayout.findViewById<EditText>(R.id.etExpenseCost)

        with(builder)
        {
            setTitle("Add Expense")
            setPositiveButton("Add") { dialogInterface, i -> addExpense(etExpenseName.text.toString(), etExpenseCost.text.toString().toDouble()) }
            setNegativeButton("Cancel", null)
            setView(dialogLayout)
            show()
        }
    }

    fun addExpense(expenseName: String, expenseCost: Double ) {
        var expense = Expense()

        with(expense) {
            name = expenseName
            cost = expenseCost
            budgetId = formBudget.id
        }

        formBudget.amount = formBudget.amount - expense.cost

        DatabaseFunctions.saveExpense(expense)
        DatabaseFunctions.updateBudget(formBudget)
        loadBudgetData(formBudget)
    }

    internal var expenses : MutableLiveData<List<Expense>>
        get() {return _expenses}
        set(value) {_expenses = value}

    inner class ExpensesAdapter(val expenses: List<Expense>, val itemLayout: Int) : RecyclerView.Adapter<ViewBudget.ExpenseViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBudget.ExpenseViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)

            return ExpenseViewHolder(view)
        }

        override fun getItemCount(): Int {
            return expenses.size
        }

        override fun onBindViewHolder(holder: ViewBudget.ExpenseViewHolder, position: Int) {
            val expense = expenses.get(position)
            holder.updateExpense(expense)
        }
    }

    inner class ExpenseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var tvExpenseName : TextView = itemView.findViewById(R.id.tvBudgetName)
        private var tvCost : TextView = itemView.findViewById(R.id.tvAmountRemaining)

        /**
         * Updates row in recycler view with passed in budget
         */
        fun updateExpense (expense: Expense) {
            tvExpenseName.text = expense.name
            tvCost.text = "$ ${expense.cost}"
        }
    }
}