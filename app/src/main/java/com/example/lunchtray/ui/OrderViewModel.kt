package com.example.lunchtray.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val TAX_RATE = 0.08

class OrderViewModel : ViewModel() {

    // Menu items and prices
    private val _entreeOptions = MutableLiveData<Map<String, MenuItem>>()
    val entreeOptions: LiveData<Map<String, MenuItem>> = _entreeOptions

    private val _sideOptions = MutableLiveData<Map<String, MenuItem>>()
    val sideOptions: LiveData<Map<String, MenuItem>> = _sideOptions

    private val _accompanimentOptions = MutableLiveData<Map<String, MenuItem>>()
    val accompanimentOptions: LiveData<Map<String, MenuItem>> = _accompanimentOptions

    // Order details
    private val _selectedEntree = MutableLiveData<String>()
    val selectedEntree: LiveData<String> = _selectedEntree

    private val _selectedSide = MutableLiveData<String>()
    val selectedSide: LiveData<String> = _selectedSide

    private val _selectedAccompaniment = MutableLiveData<String>()
    val selectedAccompaniment: LiveData<String> = _selectedAccompaniment

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private val _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    private val _dateOptions = MutableLiveData<List<String>>()
    val dateOptions: LiveData<List<String>> = _dateOptions

    init {
        resetOrder()
        initMenuItems()
        updateDateOptions()
    }

    private fun initMenuItems() {
        // Initialize entree options
        val entreeItems = mapOf(
            "Cauliflower" to MenuItem("Cauliflower", "Whole cauliflower, brined, roasted, and deep fried", 7.00),
            "Three Bean Chili" to MenuItem("Three Bean Chili", "Black beans, red beans, kidney beans, slow cooked, topped with onion", 4.00),
            "Mushroom Pasta" to MenuItem("Mushroom Pasta", "Penne pasta, mushrooms, basil, with plum tomatoes cooked in garlic and olive oil", 5.50),
            "Spicy Black Bean Skillet" to MenuItem("Spicy Black Bean Skillet", "Seasonal vegetables, black beans, house spice blend, served with avocado and quick pickled onions", 5.50)
        )
        _entreeOptions.value = entreeItems

        // Initialize side options
        val sideItems = mapOf(
            "Summer Salad" to MenuItem("Summer Salad", "Mixed tomatoes, butter lettuce, peaches, avocado, balsamic dressing", 2.50),
            "Butternut Squash Soup" to MenuItem("Butternut Squash Soup", "Roasted butternut squash, roasted peppers, chili oil", 3.00),
            "Spicy Potatoes" to MenuItem("Spicy Potatoes", "Yukon potatoes, roasted, and fried in house spice blend", 2.00),
            "Coconut Rice" to MenuItem("Coconut Rice", "Rice, coconut milk, lime, and sugar", 1.10)
        )
        _sideOptions.value = sideItems

        // Initialize accompaniment options
        val accompanimentItems = mapOf(
            "Lunch Roll" to MenuItem("Lunch Roll", "Fresh baked roll made in house", 0.50),
            "Mixed Berries" to MenuItem("Mixed Berries", "Strawberries, blueberries, raspberries, and blackberries", 1.00),
            "Pickled Veggies" to MenuItem("Pickled Veggies", "Pickled cucumbers and carrots, made in house", 0.50)
        )
        _accompanimentOptions.value = accompanimentItems
    }

    fun setEntree(entreeName: String) {
        _selectedEntree.value = entreeName
        updatePrice()
    }

    fun setSide(sideName: String) {
        _selectedSide.value = sideName
        updatePrice()
    }

    fun setAccompaniment(accompanimentName: String) {
        _selectedAccompaniment.value = accompanimentName
        updatePrice()
    }

    fun setDate(date: String) {
        _selectedDate.value = date
    }

    private fun updatePrice() {
        var calculatedPrice = 0.0

        // Add entree price if selected
        _selectedEntree.value?.let { entreeName ->
            _entreeOptions.value?.get(entreeName)?.let { menuItem ->
                calculatedPrice += menuItem.price
            }
        }

        // Add side price if selected
        _selectedSide.value?.let { sideName ->
            _sideOptions.value?.get(sideName)?.let { menuItem ->
                calculatedPrice += menuItem.price
            }
        }

        // Add accompaniment price if selected
        _selectedAccompaniment.value?.let { accName ->
            _accompanimentOptions.value?.get(accName)?.let { menuItem ->
                calculatedPrice += menuItem.price
            }
        }

        _price.value = calculatedPrice
    }

    fun calculateTax(): Double {
        return _price.value?.times(TAX_RATE) ?: 0.0
    }

    fun calculateTotal(): Double {
        return (_price.value ?: 0.0) + calculateTax()
    }

    private fun updateDateOptions() {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("EEE MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Add next dates as options
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        _dateOptions.value = dateOptions
    }

    fun resetOrder() {
        _selectedEntree.value = ""
        _selectedSide.value = ""
        _selectedAccompaniment.value = ""
        _selectedDate.value = ""
        _price.value = 0.0
    }

    data class MenuItem(
        val name: String,
        val description: String,
        val price: Double
    )
}