package com.ical.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ical.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.Exception
import kotlin.math.round

/**
 * @method to round Double down to decimals digits after dot
 * @param n - number of digits to be left
 * @return Double - rounded Double
 */
fun Double.round(n: Int): Double {
    var multiplier = 1.0
    repeat(n) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

class MainActivity : AppCompatActivity() {
    val invalidMessage = "Invalid Expression!"

    /**
     * @property binding - to access views from layout
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * method that is called on when activity is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get bindings
        binding = ActivityMainBinding.inflate(layoutInflater)
        // initialize activity
        val view = binding.root
        setContentView(view)

        // set all the event listeners
        // they all call @method evaluateExpression to calculate and update result and expression

        // numbers

        binding.tvOne.setOnClickListener {
            evaluateExpression("1", equals = false)
        }

        binding.tvTwo.setOnClickListener {
            evaluateExpression("2", equals = false)
        }

        binding.tvThree.setOnClickListener {
            evaluateExpression("3", equals = false)
        }
        binding.tvFour.setOnClickListener {
            evaluateExpression("4", equals = false)
        }

        binding.tvFive.setOnClickListener {
            evaluateExpression("5", equals = false)
        }

        binding.tvSix.setOnClickListener {
            evaluateExpression("6", equals = false)
        }

        binding.tvSeven.setOnClickListener {
            evaluateExpression("7", equals = false)
        }

        binding.tvEight.setOnClickListener {
            evaluateExpression("8", equals = false)
        }

        binding.tvNine.setOnClickListener {
            evaluateExpression("9", equals = false)
        }

        binding.tvZero.setOnClickListener {
            evaluateExpression("0", equals = false)
        }

        // operators

        binding.tvPlus.setOnClickListener {
            evaluateExpression("+", equals = false)
        }

        binding.tvMinus.setOnClickListener {
            evaluateExpression("-", equals = false)
        }

        binding.tvMul.setOnClickListener {
            evaluateExpression("*", equals = false)
        }

        binding.tvDivide.setOnClickListener {
            evaluateExpression("/", equals = false)
        }

        binding.tvDot.setOnClickListener {
            evaluateExpression(".", equals = false)
        }

        binding.tvLeftParenthesis.setOnClickListener {
            evaluateExpression("(", equals = false)
        }

        binding.tvRightParenthesis.setOnClickListener {
            evaluateExpression(")", equals = false)
        }

        // clear all
        binding.tvClear.setOnClickListener {
            binding.tvExpression.text = ""
            binding.tvResult.text = ""
        }

        // equals
        binding.tvEquals.setOnClickListener {
            evaluateExpression(equals = true);
        }

        // delete button (drops last character)
        binding.tvDelete.setOnClickListener {
            val text = binding.tvExpression.text.toString()
            if(text.isNotEmpty()) {
                binding.tvExpression.text = text.dropLast(1)
                evaluateExpression()
            }
        }
    }

    /**
     * Calculates the result and sets all the TextView's
     *
     * @param string - character to add to expression
     * @param equals - true if equals button is pressed
     */
    private fun evaluateExpression(string: String = "", equals: Boolean = false) {
        // if regular key is pressed
        if(!equals) {
            // calculate the result
            binding.tvExpression.append(string)

            val text = binding.tvExpression.text.toString()

            val result = try {
                ExpressionBuilder(text).build().evaluate()
            } catch (e: Exception) {
                // if wrong expression clear result text
                binding.tvResult.text = ""
                return
            }

            // if parsable expression update result text
            val longResult = result.toLong()
            if (result == longResult.toDouble()) {
                binding.tvResult.text = longResult.toString()
            } else {
                binding.tvResult.text = result.round(3).toString()
            }
        } else {
            // if result is valid
            if (binding.tvResult.text != "" && binding.tvResult.text != invalidMessage) {
                // move value from result to expression
                binding.tvExpression.text = binding.tvResult.text.toString()
                // clear result
                binding.tvResult.text = ""
            } else {
                // show error
                binding.tvResult.text = invalidMessage
            }
        }
    }
}