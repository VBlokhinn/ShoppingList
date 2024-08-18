package com.example.shoppinglist.domain

import androidx.room.Entity

@Entity(tableName = "ShopList.db")
data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
