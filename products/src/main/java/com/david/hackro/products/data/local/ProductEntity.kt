package com.david.hackro.products.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductEntity {

    @PrimaryKey
    var id: Int? = null

    @ColumnInfo
    var image: String? = null

    @ColumnInfo
    var price: Double? = null

    @ColumnInfo
    var rate: Double? = null

    @ColumnInfo
    var count: Int? = 0

    @ColumnInfo
    var description: String? = null

    @ColumnInfo
    var title: String? = null

    @ColumnInfo
    var category: String? = null
}