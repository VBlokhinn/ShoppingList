package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID

@Entity(tableName = "shopItems")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)