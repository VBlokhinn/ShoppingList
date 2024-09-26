package com.example.shoppinglist.domain

import javax.inject.Inject


data class ShopItem @Inject constructor(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
