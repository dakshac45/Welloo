package com.example.welloo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.welloo.data.repository.HabitRepository
import com.example.welloo.model.Habit
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {

    // Backing state
    private val _habitList = MutableStateFlow<List<Habit>>(emptyList())
    val habitList: StateFlow<List<Habit>> = _habitList.asStateFlow()

    init {
        // Collect data from repository into our StateFlow
        viewModelScope.launch {
            repository.allHabits.collect { habits ->
                _habitList.value = habits
            }
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insert(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.delete(habit)
        }
    }
}
