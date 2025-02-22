package com.github.junhee8649.androidstudy17.week4.Dessert.WithViewModel.data

import androidx.annotation.DrawableRes
import com.github.junhee8649.androidstudy17.week4.Dessert.WithViewModel.data.Datasource.dessertList

data class DessertUiState(
    val currentDessertIndex: Int = 0,
    val dessertsSold: Int = 0,
    val revenue: Int = 0,
    val currentDessertPrice: Int = dessertList[currentDessertIndex].price,
    @DrawableRes val currentDessertImageId: Int = dessertList[currentDessertIndex].imageId
)