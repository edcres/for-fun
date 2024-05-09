package com.example.interestinflationcalc

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.interestinflationcalc.databinding.ActivityMainBinding
import kotlin.math.ceil
import kotlin.math.pow

// inflation,
// tax bracket,
// percentage of a number (ie. what percentage is 5.8 out of 33)

// todo: test the app features

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }

        binding.apply {
            annualIncomeBtn.setOnClickListener {
                val annualIncomeEtStr = annualIncomeEt.text.toString()
                if (annualIncomeEtStr.isNotEmpty()) {
                    annualIncomeAnswerTxt.text = getAmountOfTaxesUsingTaxBracket(
                        annualIncomeEtStr.toDouble()
                    ).toString()
                }
            }

            gasCostBtn.setOnClickListener {
                val gasMilesEtStr = gasMilesEt.text.toString()
                val milesPerGallonEtStr = milesPerGallonEt.text.toString()
                val gasPricePerGallonEtStr = gasPricePerGallonEt.text.toString()
                if (gasMilesEtStr.isNotEmpty() && milesPerGallonEtStr.isNotEmpty()
                    && gasPricePerGallonEtStr.isNotEmpty()) {
                    gasCostAnswerTxt.text = getGasCost(
                        gasMilesEtStr.toDouble(),
                        milesPerGallonEtStr.toDouble(),
                        gasPricePerGallonEtStr.toDouble()
                    ).toString()
                }
            }

            inflationRateBtn.setOnClickListener {
                val inflationRateStartStr = inflationRateStartEt.text.toString()
                val inflationRateEndStr = inflationRateEndEt.text.toString()
                if (inflationRateStartStr.isNotEmpty() && inflationRateEndStr.isNotEmpty()) {
                    inflationRateAnswerTxt.text = getInflationRate(
                        inflationRateStartStr.toDouble(),
                        inflationRateEndStr.toDouble()
                    ).toString()
                }
            }

            inflationValueBtn.setOnClickListener {
                val inflationValStartStr = inflationValStartEt.text.toString()
                val inflationRateStr = inflationRateEt.text.toString()
                val inflationYearsStr = inflationYearsEt.text.toString()
                if (inflationValStartStr.isNotEmpty() && inflationRateStr.isNotEmpty()
                    && inflationYearsStr.isNotEmpty()
                ) {
                    inflationValueAnswerTxt.text = getPriceWithInflation(
                        inflationValStartStr.toDouble(),
                        inflationRateStr.toDouble() / 100,
                        inflationYearsStr.toInt(),
                        pastInflationToggleBtn.isChecked    // if true is past
                    ).toString()
                }
            }

            simpleInterestBtn.setOnClickListener {
                val simpleInterestStartStr = simpleInterestStartEt.text.toString()
                val simpleInterestRateStr = simpleInterestRateEt.text.toString()
                val simpleInterestYearsStr = simpleInterestYearsEt.text.toString()
                if (simpleInterestStartStr.isNotEmpty() && simpleInterestRateStr.isNotEmpty()
                    && simpleInterestYearsStr.isNotEmpty()
                ) {
                    simpleInterestAnswerTxt.text = getSimpleInterest(
                        simpleInterestStartStr.toDouble(),
                        simpleInterestRateStr.toDouble() / 100,
                        simpleInterestYearsStr.toDouble()
                    ).toString()
                }
            }

            compoundInterestBtn.setOnClickListener {
                val compoundInterestStartStr = compoundInterestStartEt.text.toString()
                val compoundInterestRateStr = compoundInterestRateEt.text.toString()
                val compoundInterestCompoundedYearsStr = compoundInterestCompoundedYearsEt
                    .text.toString()
                val compoundInterestYearsStr = compoundInterestYearsEt.text.toString()
                if (compoundInterestStartStr.isNotEmpty() && compoundInterestRateStr.isNotEmpty()
                    && compoundInterestCompoundedYearsStr.isNotEmpty()
                    && compoundInterestYearsStr.isNotEmpty()
                ) {
                    compoundInterestTxt.text = getCompoundInterest(
                        compoundInterestStartStr.toDouble(),
                        compoundInterestRateStr.toDouble() / 100,
                        compoundInterestCompoundedYearsStr.toInt(),
                        compoundInterestYearsStr.toDouble()
                    ).toString()
                }
            }
        }
    }

    private fun getAmountOfTaxesUsingTaxBracket(
        annualIncome: Double
    ): Double {
        // todo: Make this so the user can input their
        //  custom tax brackets with the amounts.
        // Tax brackets of 2023-2024
        val brackets = listOf(0.0, 11000.0, 44725.0, 95375.0, 182100.0, 231250.0, 578125.0, 0.0)
        val percents = listOf(0.0, 0.10,    0.12,    0.22,    0.24,     0.32,     0.35,     0.37)
        var taxedIn = 0.0
        var testCounter = 0
        for (bracket in 1..brackets.size) {
            testCounter ++
            if (annualIncome <= brackets[bracket]) {
                Log.d("testTAG-1", "getAmountOfTaxesUsingTaxBracket: $taxedIn")
                Log.d("testTAG0", "getAmountOfTaxesUsingTaxBracket: loop $testCounter: ($annualIncome - ${brackets[bracket-1]}) * ${percents[bracket]}")
                Log.d("testTAG1", "getAmountOfTaxesUsingTaxBracket: ${(annualIncome - brackets[bracket-1]) * percents[bracket]}")
                taxedIn += (annualIncome - brackets[bracket-1]) * percents[bracket]
                return taxedIn
            } else taxedIn += (brackets[bracket] - brackets[bracket-1]) * percents[bracket]
//            } else taxedIn += (annualIncome-taxedIn) * percents[bracket]
//            } else taxedIn += (brackets[bracket]-taxedIn) * percents[bracket]
        }


//
//        if (annualIncome <= brackets[0]) {
//            taxedIn += annualIncome-taxedIn * 0.10
//        } else taxedIn += brackets[0] * 0.10
//        if (annualIncome <= brackets[1]) {
//            taxedIn += annualIncome-taxedIn * 0.12
//        } else taxedIn += brackets[1] * 0.12
//
//        if (annualIncome <= brackets[0]) {
//            taxedIn += annualIncome * 0.10
//        } else taxedIn += brackets[0] * 0.10
//
//        when {
//            annualIncome <= 11275 -> annualIncome * 0.10
//            annualIncome <= 43975 -> 1127.50 + (annualIncome - 11275) * 0.12
//            annualIncome <= 93675 -> 5045.50 + (annualIncome - 43975) * 0.22
//            annualIncome <= 182575 -> 16165.50 + (annualIncome - 93675) * 0.24
//            annualIncome <= 231600 -> 37853.50 + (annualIncome - 182575) * 0.32
//            annualIncome <= 578125 -> 53313.50 + (annualIncome - 231600) * 0.35
//            else -> 166156.50 + (annualIncome - 578125) * 0.37
//        }



        return taxedIn
    }

    private fun getGasCost(
        miles: Double,
        milesPerGallon: Double,
        gasPricePerGallon: Double
    ): Double {
        val gallons = miles / milesPerGallon
        return gallons * gasPricePerGallon
    }

    private fun getInflationRate(startingValue: Double, endValue: Double) =
        (endValue - startingValue) / startingValue * 100

    private fun getPriceWithInflation(
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