package com.example.welloo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,         // Example: "2025-06-06"
    val waterIntake: Int,     // In glasses
    val sleepHours: Float,    // Hours slept
    val mood: String          // "Happy", "Tired", etc.
)
