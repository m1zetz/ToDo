package com.example.todo.ui.Composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.utils.calculateDays

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCard(
    title: String,
    description: String,
    importance: String,
    dateOfAnnouncement: String,
    dateOfComplete: String,
    deleteTask: () -> Unit
)
{

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                    deleteTask()
                },
                    Modifier.size(
                        25.dp
                    )
                ) {
                    Icon(Icons.Filled.Delete,
                        contentDescription = "Удалить задачу",
                        Modifier.size(25.dp))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    description,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text("Важность: $importance",
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    dateOfAnnouncement.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF949494)
                )

                Text(
                    "осталось дней: ${calculateDays(dateOfComplete)}",
                    fontSize = 16.sp
                )
            }
        }
    }
}