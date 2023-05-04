package com.david.hackro.products.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CategoryEntity {

    @PrimaryKey
    var category: String = ""
}