package iuh_ad.nguyenthithanhhoa.ms_19429041.changemaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var value_board: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        value_board = arrayOf(
            val1, val2,
            val3, val4,
            val5, val6,
            val7, val8
        )

        var newPrice: String =
            if (txtInput.text.toString() == "0.00") "" else txtInput.text.toString()

        fun onDigit(view: TextView) {
            var value = view.text
            newPrice += value.toString()
            newPrice = newPrice.toInt().toString()

            var parsedNumber = newPrice.toDouble() / 100.0
            txtInput.text = parsedNumber.toString()

            val listPrices = arrayOf(20.0, 10.0, 5.0, 1.0, 0.25, 0.1, 0.05, 0.01)

            for (i in listPrices.indices) {
                var amounts = (parsedNumber / listPrices[i]).toInt()
                value_board[i].text = amounts.toString()
                parsedNumber -= amounts.toDouble() * listPrices[i]
                parsedNumber = parsedNumber.toBigDecimal().setScale(6, RoundingMode.UP).toDouble()
            }
        }

        fun onClear(view: View) {
            txtInput.text = "0.00"
            newPrice= if (txtInput.text.toString() == "0.00") "" else txtInput.text.toString()
            value_board.forEach { it -> it.text = "0" }
            return
        }
        arrayOf<TextView>(
            button1, button2, button3,
            button4, button5, button6,
            button7, button8, button9,
            button0
        ).forEach { it ->
            it.setOnClickListener() {
                onDigit(it as TextView)
            }
        }
        arrayOf<TextView>(button_clear).forEach { it ->
            it.setOnClickListener() {
                onClear(it as TextView)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var currPrice: TextView = txtInput
        var oldPrices: ArrayList<String> = ArrayList()

        for (i in value_board) {
            oldPrices.add(i.text.toString())
        }

        outState.putString("currPrice", currPrice.text.toString())
        outState.putStringArrayList("oldPrices", oldPrices)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        txtInput.text = savedInstanceState.getString("currPrice")
        var oldPrices = savedInstanceState.getStringArrayList("oldPrices")

        for (i in value_board.indices) {
            value_board[i].text = oldPrices?.get(i) ?: "0"
        }
    }
}