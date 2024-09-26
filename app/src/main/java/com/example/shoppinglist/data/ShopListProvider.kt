package com.example.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.ShopApplication
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as ShopApplication).component
    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoppinglist", "shop_items", GET_SHOP_ITEM_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (uriMatcher.match(p0)) {
            GET_SHOP_ITEM_QUERY -> {
                shopListDao.getShopListCursor()
            }

            else -> {
                null
            }
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(p0)) {
            GET_SHOP_ITEM_QUERY -> {
                shopListDao.getShopListCursor()
                if (values == null) return null
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")
                val shopItem = ShopItem(id = id, name = name, count = count, enabled = enabled)
                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))
            }
        }
        return null
    }

    override fun delete(p0: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(p0)) {
            GET_SHOP_ITEM_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                shopListDao.deleteShopItemSync(id)
            }
        }
        return 0
    }

    override fun update(p0: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(p0)) {
            GET_SHOP_ITEM_QUERY -> {
                if (values == null) return 0
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")
                val shopItem = ShopItem(id = id, name = name, count = count, enabled = enabled)
                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))
                return 1
            }
        }
        return 0
    }

    companion object {
        private const val GET_SHOP_ITEM_QUERY = 100
    }
}