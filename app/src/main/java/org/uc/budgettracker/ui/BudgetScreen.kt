package org.uc.budgettracker.ui

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.budget_screen.*
import org.uc.budgettracker.R
import org.uc.budgettracker.dao.OnBudgetItemClickListener
import org.uc.budgettracker.dto.Budget
import org.uc.budgettracker.utils.DatabaseFunctions

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BudgetScreen : Fragment(), OnBudgetItemClickListener{

    private var _budgets = MutableLiveData<List<Budget>>()
    private var budgetList = ArrayList<Budget>()

    companion object {
        var selectedBudget: Budget = Budget()
    }

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

        setupRvBudgets()
    }

    private fun setupRvBudgets() {
        _budgets = DatabaseFunctions.fetchBudgets(getDeviceId())

        budgets.observe(viewLifecycleOwner, Observer {
                budgets ->
            budgetList.removeAll(budgetList)
            budgetList.addAll(budgets)

            rvBudgets.adapter!!.notifyDataSetChanged()
        })

        // Link data to rvBudgets
        rvBudgets.hasFixedSize()
        rvBudgets.layoutManager = LinearLayoutManager(context)
        rvBudgets.itemAnimator = DefaultItemAnimator()
        rvBudgets.adapter = BudgetsAdapter(budgetList, R.layout.budget_layout, this)
    }

    inner class BudgetsAdapter(val budgets: List<Budget>, val itemLayout: Int, var action: OnBudgetItemClickListener) : RecyclerView.Adapter<BudgetScreen.BudgetViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
           val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)

            return BudgetViewHolder(view)
        }

        override fun getItemCount(): Int {
            return budgets.size
        }

        override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
            val budget = budgets.get(position)
            holder.updateBudget(budget, action)
        }

    }

    /**
     * Gets unique device id from phone for querying the database
     */
    fun getDeviceId() : String {
        return Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
    }

    internal var budgets : MutableLiveData<List<Budget>>
        get() {return _budgets}
        set(value) {_budgets = value}

    inner class BudgetViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var tvBudgetName : TextView = itemView.findViewById(R.id.tvBudgetName)
        private var tvAmountRemaining : TextView = itemView.findViewById(R.id.tvAmountRemaining)

        /**
         * Updates row in recycler view with passed in budget
         */
        fun updateBudget (budget: Budget, action: OnBudgetItemClickListener) {

            tvBudgetName.text = budget.name
            tvAmountRemaining.text = "$ ${budget.amount}"

            itemView.setOnClickListener {
                action.onItemClick(budget, adapterPosition)
            }
        }
    }

    override fun onItemClick(budget: Budget, position: Int) {
        selectedBudget = budget
        findNavController().navigate(R.id.action_BudgetScreen_to_ViewBudget)
    }
}