import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pembukuan_keuangan.*

class PembukuanKeuangan : AppCompatActivity() {

    private val transactionsList = mutableListOf<Transaction>()
    private lateinit var adapter: ArrayAdapter<Transaction>

    private var balance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembukuan_keuangan)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transactionsList)
        list_view_transactions.adapter = adapter

        button_add_transaction.setOnClickListener {
            addTransaction()
        }

        // Dummy data for spinner (transaction types)
        val transactionTypes = resources.getStringArray(R.array.transaction_types)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, transactionTypes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_type.adapter = spinnerAdapter
    }

    private fun addTransaction() {
        val description = edit_text_description.text.toString().trim()
        val amountStr = edit_text_amount.text.toString().trim()
        val type = spinner_type.selectedItem.toString()

        if (description.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter description and amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDouble()

        // Create Transaction object
        val transaction = Transaction(description, amount, type)
        transactionsList.add(transaction)
        adapter.notifyDataSetChanged()

        // Update balance
        if (type == "Income") {
            balance += amount
        } else {
            balance -= amount
        }

        updateBalance()

        // Clear input fields
        edit_text_description.text.clear()
        edit_text_amount.text.clear()
    }

    private fun updateBalance() {
        text_view_balance.text = "Balance: $${String.format("%.2f", balance)}"
    }
}

data class Transaction(val description: String, val amount: Double, val type: String) {
    override fun toString(): String {
        return "$description: ${if (type == "Income") "+$" else "-$"}${String.format("%.2f", amount)}"
    }
}