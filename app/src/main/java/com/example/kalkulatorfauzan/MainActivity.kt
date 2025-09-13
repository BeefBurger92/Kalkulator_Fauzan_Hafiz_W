package com.example.kalkulatorfauzan

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var currentNumber = ""
    private var previousNumber = ""
    private var operator = ""
    private var isOperatorPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayTextView = findViewById(R.id.displayTextView)

        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val buttonDecimal = findViewById<Button>(R.id.buttonDecimal)

        button0.setOnClickListener { appendNumber("0") }
        button1.setOnClickListener { appendNumber("1") }
        button2.setOnClickListener { appendNumber("2") }
        button3.setOnClickListener { appendNumber("3") }
        button4.setOnClickListener { appendNumber("4") }
        button5.setOnClickListener { appendNumber("5") }
        button6.setOnClickListener { appendNumber("6") }
        button7.setOnClickListener { appendNumber("7") }
        button8.setOnClickListener { appendNumber("8") }
        button9.setOnClickListener { appendNumber("9") }

        buttonAdd.setOnClickListener { setOperator("+") }
        buttonSubtract.setOnClickListener { setOperator("-") }
        buttonMultiply.setOnClickListener { setOperator("×") }
        buttonDivide.setOnClickListener { setOperator("÷") }

        buttonEquals.setOnClickListener { calculateResult() }
        buttonClear.setOnClickListener { clearAll() }
        buttonDecimal.setOnClickListener { appendDecimal() }
    }
    private fun appendNumber(number: String) {
        if (isOperatorPressed) {
            currentNumber = ""
            isOperatorPressed = false
        }

        if (currentNumber == "0") {
            currentNumber = number
        } else {
            currentNumber += number
        }

        updateDisplay()
    }
    private fun appendDecimal() {
        if (isOperatorPressed) {
            currentNumber = "0"
            isOperatorPressed = false
        }

        if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0."
            } else {
                currentNumber += "."
            }
            updateDisplay()
        }
    }
    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && !isOperatorPressed) {
            calculateResult()
        }

        if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            operator = op
            isOperatorPressed = true
            updateDisplay()
        }
    }

    private fun calculateResult() {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty() && operator.isNotEmpty()) {
            val prev = previousNumber.toDoubleOrNull()
            val curr = currentNumber.toDoubleOrNull()

            if (prev != null && curr != null) {
                val result = when (operator) {
                    "+" -> prev + curr
                    "-" -> prev - curr
                    "×" -> prev * curr
                    "÷" -> {
                        if (curr != 0.0) {
                            prev / curr
                        } else {
                            displayTextView.text = "Error: Tidak bisa dibagi nol"
                            clearAll()
                            return
                        }
                    }
                    else -> return
                }
                currentNumber = if (result % 1 == 0.0) {
                    result.toInt().toString()
                } else {
                    result.toString()
                }

                previousNumber = ""
                operator = ""
                updateDisplay()
            }
        }
    }

    private fun clearAll() {
        currentNumber = ""
        previousNumber = ""
        operator = ""
        isOperatorPressed = false
        displayTextView.text = "0"
    }

    private fun updateDisplay() {
        val displayText = if (currentNumber.isEmpty()) {
            "0"
        } else {
            currentNumber
        }
        displayTextView.text = displayText
    }
}