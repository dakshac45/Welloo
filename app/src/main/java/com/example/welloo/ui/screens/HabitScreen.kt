package com.example.welloo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.welloo.model.Habit
import com.example.welloo.viewmodel.HabitViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HabitScreen(viewModel: HabitViewModel) {

    // Collect the list of habits from ViewModel
    val habitList by viewModel.habitList.collectAsState()

    var waterInput by remember { mutableStateOf("") }
    var sleepInput by remember { mutableStateOf("") }
    var moodInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }


    Column(modifier = Modifier.padding(16.dp)) {

        // Title
        Text("Track Your Habits", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields
        OutlinedTextField(
            value = waterInput,
            onValueChange = { waterInput = it },
            label = { Text("Water Intake (glasses)") },
            leadingIcon = { Icon(Icons.Default.WaterDrop, contentDescription = "Water") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sleepInput,
            onValueChange = { sleepInput = it },
            label = { Text("Sleep Hours") },
            leadingIcon = { Icon(Icons.Default.Hotel, contentDescription = "Sleep") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = moodInput,
            onValueChange = { moodInput = it },
            label = { Text("Mood") },
            leadingIcon = { Icon(Icons.Default.Mood, contentDescription = "Mood") },
            modifier = Modifier.fillMaxWidth()
        )

        if(errorMessage.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Add button
        Button(
            onClick = {
                if (waterInput.isBlank() && sleepInput.isBlank() && moodInput.isBlank()) {
                    errorMessage = "Please fill in all fields."
                } else if (waterInput.toIntOrNull() == null) {
                    errorMessage = "Water intake must contain a valid input"
                } else if (sleepInput.toFloatOrNull() == null) {
                    errorMessage = "Sleep hours must contain a valid input"
                } else if (moodInput.isBlank() || !moodInput.all { it.isLetter() || it.isWhitespace() }) {
                    errorMessage = "Mood must contain a valid input."
                } else {
                    errorMessage = ""
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val habit = Habit(
                        date = currentDate,
                        waterIntake = waterInput.toInt(),
                        sleepHours = sleepInput.toFloat(),
                        mood = moodInput
                    )
                    viewModel.addHabit(habit)
                    waterInput = ""
                    sleepInput = ""
                    moodInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
            Spacer(Modifier.width(8.dp))
            Text("Add Habit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        // List of habits
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(Icons.Default.History, contentDescription = "History")
            Spacer(modifier = Modifier.height(8.dp))
            Text("History", style = MaterialTheme.typography.titleMedium)
        }


        LazyColumn {
            items(habitList) { habit ->
                HabitItem(habit = habit, onDelete = { viewModel.deleteHabit(it) })
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onDelete: (Habit) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Date: ${habit.date}")
            Text("Water: ${habit.waterIntake} glasses")
            Text("Sleep: ${habit.sleepHours} hrs")
            Text("Mood: ${habit.mood}")
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { onDelete(habit) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}
