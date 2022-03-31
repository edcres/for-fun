package com.example.interestinflationcalc

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlin.math.pow

// interest rate on loan,
// interest rate on investment,
// inflation,
// tax bracket,
// percentage of a number (ie. what percentage is 5.8 out of 33)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }




        // example function call
        getCompoundInterestOnValue()
    }

    // Convert everything to years at the point of calling this function.
    // This function could apply to a loan or an investment.
    private fun getCompoundInterestOnValue(
        startingValue: Double,
        interestDecimal: Double,
        timesCompoundedInAYear: Int,
        timeInYears: Double
    ): Double {
        // https://www.youtube.com/watch?v=sWh2Amd3xgg
        // Compound interest (as opposed to simple interest)
        // A = P (1 + interestRate / timesCompoundedPerTime).pow(time * timesCompoundedPerTime)
        return startingValue * ( 1 + interestDecimal / timesCompoundedInAYear )
            .pow(timeInYears * timesCompoundedInAYear)
    }
}