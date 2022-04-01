package com.example.interestinflationcalc

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                }   // todo: make sure the answer is in percent
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








        }
    }




    private fun getAmountOfTaxesUsingTaxBracket(
        annualIncome: Double
    ): Double {
        // todo: In the future, make this so the user can input their
        //  custom tax brackets with the amounts.
        var result = 0.0
        // todo: tax brackets updated April 2022
        // 10% (to $9,950), 12% (to $40,525), 22% (to $86,375), 24% (to $164,925),
        //   32% (to $209,425), 35% (to $523,600), 37% (to infinity)
        // $995 plus 12% of the amount over $9,950
        val newBracketsAndTaxes = mutableListOf(
            listOf(0.10, 0.0),
            listOf(0.12, 9_950.0),
            listOf(0.22, 40_525.0),
            listOf(0.24, 86_375.0),
            listOf(0.32, 164_925.0),
            listOf(0.35, 209_425.0),
            listOf(0.37, 523_600.0)
        )
        newBracketsAndTaxes.forEach { interestAndFloor ->
            if(annualIncome > interestAndFloor[1]) {
                result += (annualIncome - interestAndFloor[1]) * interestAndFloor[0]
            }
        }

//        // I chose not to use a mutableMap bc the items might not be in order.
//        val bracketsAndTaxes = mutableMapOf(
//            0.10 to 0.0,
//            0.12 to 9_950.0,
//            0.22 to 40_525.0,
//            0.24 to 86_375.0,
//            0.32 to 164_925.0,
//            0.35 to 209_425.0,
//            0.37 to 523_600.0    // null bc it's infinity
//        )
////        bracketsAndTaxes.put()
//        bracketsAndTaxes.forEach { (key, value) ->
//            if (annualIncome > value) {
//                result += (annualIncome - value) * key
//            }
//        }

//        if (annualIncome > 0) { // 10%
//            result += annualIncome * 0.10
//            if (annualIncome > 9_950) { // 12%
//                result += (annualIncome - 9_950) * .12
//                if (annualIncome > 40_525) {
//                    result += (annualIncome - 40_525) * .22
//                    if (annualIncome > 86_375) {
//                        result += (annualIncome - 86_375) * .24
//                        if (annualIncome > 164_925) {
//                            result += (annualIncome - 164_925) * .32
//                            if (annualIncome > 209_425) {
//                                result += (annualIncome - 209_425) * .35
//                                if (annualIncome > 523_600) {
//                                    result += (annualIncome - 523_600) * .37
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return result
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