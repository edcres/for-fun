package com.example.interestinflationcalc

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlin.math.ceil
import kotlin.math.pow

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





    }

    fun getGasCost(
        miles: Double,
        
    ) {

    }

    fun getInflationRate(startingValue: Double, endValue: Double) =
        (endValue - startingValue) / startingValue * 100

    fun getPriceWithInflation(
        startingValue: Double,
        inflationRateDecimal: Double,
        years: Int,
        past: Boolean = false
    ): Double {
        return if (past) {
            // todo: close estimate, not necessarily accurate (let the user know)
            // result = ((1 + startingValue) / (1 + inflationRate) - 1)
            var endValue = startingValue
            for (i in 1..years) {
                endValue = ceil((1 + endValue) / (1 + inflationRateDecimal) - 1)
            }
            endValue
        } else {
            var endValue = startingValue
            for (i in 1..years) {
                endValue += endValue * inflationRateDecimal
            }
            endValue
        }
    }

    private fun getSimpleInterest(
        startingValue: Double,
        interestDecimal: Double,
        timeInYears: Double
    ) = startingValue * ( 1 + interestDecimal * timeInYears)

    // Convert everything to years at the point of calling this function.
    // This function could apply to a loan or an investment.
    private fun getCompoundInterest(
        startingValue: Double,
        interestDecimal: Double,
        timesCompoundedInAYear: Int,
        timeInYears: Double
    ) = startingValue * ( 1 + interestDecimal / timesCompoundedInAYear )
        .pow(timeInYears * timesCompoundedInAYear)
        // https://www.youtube.com/watch?v=sWh2Amd3xgg
        // Compound interest (as opposed to simple interest)
        // A = P (1 + interestRate / timesCompoundedPerTime).pow(time * timesCompoundedPerTime)
}