package com.david.hackro.products.domain

import com.david.hackro.products.data.local.CategoryEntity

data class Category(val category: String, val categoryIcon: Int)

fun CategoryEntity.toDomain(categoryIcon: Int): Category {
    return Category(category = category, categoryIcon)
}