package com.example.lunchtray

import android.icu.text.CaseMap.Title

enum class Route (val title: String) {

    START("Lunch Tray"),
    ENTREE("Choose Entree"),
    SIDE("Choose Side Dish"),
    ACCOMPANIMENT("Choose Accompaniment"),
    CHECKOUT("Order Checkout")
}