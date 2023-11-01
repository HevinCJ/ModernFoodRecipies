package com.example.modernfoodrecipe.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.modernfoodrecipe.models.ResultX
import com.example.modernfoodrecipe.utils.Constants.Companion.FAVOURITE_TABLE


@Entity(tableName = FAVOURITE_TABLE )
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result: ResultX

    )
