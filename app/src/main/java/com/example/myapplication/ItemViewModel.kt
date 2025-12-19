package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel(private val dbHelper: DBHelper, private val username: String) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    val newItemName = MutableLiveData<String>()

    init {
        loadItems()
    }

    private fun loadItems() {
        val itemList = mutableListOf<Item>()
        val cursor = dbHelper.getItems(username)
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow("_id"))
                val itemName = it.getString(it.getColumnIndexOrThrow("name"))
                itemList.add(Item(id, itemName, username))
            }
        }
        _items.value = itemList
    }

    fun addItem() {
        val itemName = newItemName.value
        if (!itemName.isNullOrEmpty()) {
            if (dbHelper.insertItem(itemName, username)) {
                loadItems()
                newItemName.value = ""
            }
        }
    }

    fun updateItem(item: Item, updatedItemName: String) {
        if (dbHelper.updateItem(item.id.toString(), updatedItemName)) {
            loadItems()
        }
    }

    fun deleteItem(item: Item) {
        if (dbHelper.deleteItem(item.id.toString())) {
            loadItems()
        }
    }
}
