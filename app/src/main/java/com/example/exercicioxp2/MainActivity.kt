package com.example.exercicioxp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exercicioxp2.ui.theme.ExercicioXP2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExercicioXP2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListaJogadores()
                }
            }
        }
    }
}

@Composable
fun ListaJogadores() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(6) { index ->
            JogadorItem(jogadorNumero = index + 1)
        }
    }
}

@Composable
fun JogadorItem(jogadorNumero: Int) {
    var nome by remember { mutableStateOf("") }
    var level by remember { mutableStateOf(1) }
    var bonus by remember { mutableStateOf(0) }
    var modificadores by remember { mutableStateOf(0) }

    val poderTotal = level + bonus + modificadores

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Jogador $jogadorNumero",
            style = MaterialTheme.typography.headlineSmall
        )

        // Campo de nome do jogador com teclado alfanumérico
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatControl("Level", level, 1..10) { newLevel -> level = newLevel }
            StatControl("Bônus", bonus, 0..99) { newBonus -> bonus = newBonus }
            StatControl("Modificadores", modificadores, -10..10) { newModificadores -> modificadores = newModificadores }
        }

        // Exibir o poder total centralizado
        Text(
            text = "Poder Total: $poderTotal",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun StatControl(label: String, value: Int, range: IntRange, onValueChange: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$label: $value")

        Row(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { onValueChange((value - 1).coerceIn(range)) }) {
                Icon(Icons.Default.Delete, contentDescription = "Diminuir $label")
            }
            IconButton(onClick = { onValueChange((value + 1).coerceIn(range)) }) {
                Icon(Icons.Default.Add, contentDescription = "Aumentar $label")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListaJogadores() {
    ExercicioXP2Theme {
        ListaJogadores()
    }
}
