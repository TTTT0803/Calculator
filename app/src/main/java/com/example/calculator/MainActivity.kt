package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                CalculatorScreen() // Gọi hàm CalculatorScreen trong một context @Composable
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = input,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = result,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(32.dp))

        CalculatorButtons(input, onButtonClick = { button ->
            when (button) {
                "=" -> {
                    result = evaluateExpression(input)
                    input = result
                }
                "C" -> {
                    input = ""
                    result = ""
                }
                else -> input += button
            }
        })
    }
}

@Composable
fun CalculatorButtons(input: String, onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { button ->
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp), // 👈 tăng chiều cao nút (ví dụ 90.dp)
                        onClick = { onButtonClick(button) }
                    ) {
                        Text(
                            text = button,
                            style = MaterialTheme.typography.headlineMedium // 👈 chọn style chữ to hơn
                        )
                    }

                }
            }
        }
    }
}

fun evaluateExpression(expression: String): String {
    return try {
        val tokens = mutableListOf<String>()
        var number = ""

        // Phân tách số và toán tử
        for (char in expression) {
            if (char.isDigit() || char == '.') {
                number += char
            } else {
                if (number.isNotEmpty()) {
                    tokens.add(number)
                    number = ""
                }
                tokens.add(char.toString())
            }
        }
        if (number.isNotEmpty()) tokens.add(number)

        // Thực hiện nhân chia trước
        var i = 0
        while (i < tokens.size) {
            if (tokens[i] == "*" || tokens[i] == "/") {
                val left = tokens[i - 1].toDouble()
                val right = tokens[i + 1].toDouble()
                val result = if (tokens[i] == "*") left * right else left / right
                tokens[i - 1] = result.toString()
                tokens.removeAt(i) // remove toán tử
                tokens.removeAt(i) // remove số bên phải
                i -= 1
            } else {
                i++
            }
        }

        // Thực hiện cộng trừ
        var result = tokens[0].toDouble()
        i = 1
        while (i < tokens.size) {
            val op = tokens[i]
            val next = tokens[i + 1].toDouble()
            result = when (op) {
                "+" -> result + next
                "-" -> result - next
                else -> result
            }
            i += 2
        }

        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorAppTheme {
        CalculatorScreen()
    }
}
