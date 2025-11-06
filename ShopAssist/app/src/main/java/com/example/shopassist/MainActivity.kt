package com.example.shopassist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopAssistApp()
        }
    }
}

@Composable
fun ShopAssistApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            GroceryScreen()
        }
    }
}

@Composable
fun GroceryScreen() {
    var showList by remember { mutableStateOf(false) }

    // Track permission state
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    // Launcher to request a single permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
    }

    // Check permission on composition and show dialog immediately if not granted
    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        permissionGranted = granted
        showPermissionDialog = !granted
    }

    val groceries = remember {
        listOf(
            "Milk",
            "Eggs",
            "Bread",
            "Butter",
            "Cheese",
            "Tomatoes",
            "Onions",
            "Apples",
            "Bananas",
            "Rice",
            "Pasta",
            "Fish"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Button(
            onClick = { showList = !showList },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (showList) Color(0xFF4CAF50) else MaterialTheme.colors.primary
            )
        ) {
            Text(text = if (showList) "Hide Grocery List" else "Show Grocery List")
        }

        if (showList) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(groceries) { item ->
                    Text(
                        text = "• $item",
                        style = MaterialTheme.typography.body2.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    // Permission request dialog (shown immediately when app opens if permission not granted)
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Allow Location Tracking") },
            text = { Text("This app would like to access your device location to provide location-based features. Do you allow tracking your location?") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    // Launch the runtime permission request for fine location
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("Deny")
                }
            }
        )
    }
}
