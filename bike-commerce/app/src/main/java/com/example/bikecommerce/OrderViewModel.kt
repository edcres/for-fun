package com.example.bikecommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

//private const val PRICE_FOR_SAME_DAY_DELIVERY = 20.00
//private const val MOUNTAIN_BIKE = "mountain"
//private const val BMX_BIKE = "bmx"
//private const val ROAD_BIKE = "road"
//private const val CRUISER_BIKE = "cruiser"

class OrderViewModel: ViewModel() {

    private val _bikeType = MutableLiveData<String>()
    val bikeType: LiveData<String> = _bikeType
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    private val _price = MutableLiveData<Double>()
    // format price according to the respective country
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    companion object {
        const val PRICE_FOR_SAME_DAY_DELIVERY = 20.00
        const val MOUNTAIN_BIKE = "mountain"
        const val BMX_BIKE = "bmx"
        const val ROAD_BIKE = "road"
        const val CRUISER_BIKE = "cruiser"
    }

    val bikesAndPrices = mapOf(
        MOUNTAIN_BIKE to 450.00,
        BMX_BIKE to 200.00,
        ROAD_BIKE to 350.00,
        CRUISER_BIKE to 150.00
    )

    val dateOptions = getDeliveryOptions()

    init {
        resetOrder()
    }

    fun setBikeType(desiredType: String) {
        _bikeType.value = desiredType
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoTypeSet(): Boolean {
        // returns true if null
        return _bikeType.value.isNullOrEmpty()
    }

    private fun getDeliveryOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun resetOrder() {
        _bikeType.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    private fun updatePrice() {
        var calculatedPrice = (bikesAndPrices[_bikeType.value] ?: 0.0)
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_DELIVERY
        }
        _price.value = calculatedPrice
    }
}









































