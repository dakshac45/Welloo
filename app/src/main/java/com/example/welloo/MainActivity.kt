package com.example.welloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import com.example.welloo.ui.theme.WellooTheme
import com.example.welloo.data.db.HabitDatabase
import com.example.welloo.data.repository.HabitRepository
import com.example.welloo.ui.screens.HabitScreen
import com.example.welloo.viewmodel.HabitViewModel
import com.example.welloo.viewmodel.HabitViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Step 1: Get DAO from Room
        val dao = HabitDatabase.getDatabase(applicationContext).habitDao()

        // Step 2: Create Repository
        val repository = HabitRepository(dao)

        // Step 3: Create ViewModel using the factory
        val viewModelFactory = HabitViewModelFactory(repository)

//        enableEdgeToEdge()
        setContent {
            WellooTheme {
                // Step 4: Use viewModel from factory
                val habitViewModel: HabitViewModel = viewModel(factory = viewModelFactory)

                // Step 5: Show the screen
                HabitScreen(viewModel = habitViewModel)
            }
        }
    }
}