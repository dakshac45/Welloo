package com.example.welloo.data.repository

import com.example.welloo.data.db.HabitDao
import com.example.welloo.model.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun insert(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    suspend fun delete(habit: Habit) {
        habitDao.deleteHabit(habit)
    }
}
