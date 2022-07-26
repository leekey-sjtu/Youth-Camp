package com.qxy.bitdance.logic.test

data class CatListResponse(
    val all: All,
    val categories: Categories,
    val code: Int,
    val sub: List<Sub>
)

data class All(
    val activity: Boolean,
    val category: Int,
    val hot: Boolean,
    val imgId: Int,
    val imgUrl: Any,
    val name: String,
    val resourceCount: Int,
    val resourceType: Int,
    val type: Int
)

data class Categories(
    val `0`: String,
    val `1`: String,
    val `2`: String,
    val `3`: String,
    val `4`: String
)

data class Sub(
    val activity: Boolean,
    val category: Int,
    val hot: Boolean,
    val imgId: Int,
    val imgUrl: Any,
    val name: String,
    val resourceCount: Int,
    val resourceType: Int,
    val type: Int
)