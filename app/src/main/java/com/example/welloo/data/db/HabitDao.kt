package com.example.welloo.data.db

import androidx.room.*
import com.example.welloo.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Query("SELECT * FROM habit_table ORDER BY date DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Delete
    suspend fun deleteHabit(habit: Habit)
}