package com.example.testhazirlik.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    var name : String,
    var artistName : String,
    var year : Int,
    var mimageUrl : String,
    @PrimaryKey(autoGenerate = true)
    val mid : Int? = null
)