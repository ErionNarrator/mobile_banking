package com.example.myapplication.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.AccountSearchResult
import com.example.myapplication.data.ApiClient
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.TransferViewModel
import java.nio.file.WatchEvent

@Composable
fun HomeScreen(  onNavigateToTransfer: () -> Unit,
                 onLogout: () -> Unit,
                 navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val userProfile by authViewModel.userProfile.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            userProfile?.let { profile ->
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(100.dp),
                    tint = Color(102, 90, 240)

                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Добро пожаловать, ${profile.user.username}!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = profile.user.email,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(102, 90, 240),
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Баланс",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "%.2f ${profile.defaultCurrency.symbol}".format(profile.balance),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "%.2f ${profile.defaultCurrency.symbol}".format(profile.balance),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("USD", fontWeight = FontWeight.Medium,
                                    color = Color.White)
                                Text("%.2f".format(profile.balanceInUsd),
                                    color = Color.White)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("EUR", fontWeight = FontWeight.Medium,
                                    color = Color.White)
                                Text("%.2f".format(profile.balanceInEur),
                                    color = Color.White)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("RUB", fontWeight = FontWeight.Medium,
                                    color = Color.White)
                                Text("%.2f".format(profile.balanceInRub),
                                    color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Номер счета: ${profile.accountNumber}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Телефон: ${profile.phoneNumber}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    } ?: run {
                        // Если данные профиля не загружены
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Загрузка данных...")
                    }
                    errorMessage?.let { message ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            authViewModel.logout()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(102, 90, 240),
                            disabledContainerColor = Color(102, 90, 240).copy(alpha = 0.5f)

                        )


                    ) {
                        Text("Выйти")
                    }

                    Button(
                        onClick = { navController.navigate("transfer") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(102, 90, 240),
                            disabledContainerColor = Color(102, 90, 240).copy(alpha = 0.5f)
                        )
                    ) {
                        Icon(Icons.Default.AddCard, contentDescription = "Перевод")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Сделать перевод")
                    }
                }
            }
        }
    }
}

@Composable
fun TransferScreen(
    onBack: () -> Unit,
    onTransferSuccess: () -> Unit,
    authViewModel: AuthViewModel
){
    val authToken = remember {
        when (val state = authViewModel.authState.value) {
            is AuthViewModel.AuthState.Success -> state.response.accessToken
            else -> ""
        }
    }

    val transferViewModel: TransferViewModel = remember {
        TransferViewModel(
            apiService = ApiClient.apiService,
            authToken = authToken
        )
    }

    val transferState by transferViewModel.transferState.collectAsState()
    val searchResults by transferViewModel.searchResults.collectAsState()
    val errorMessage by transferViewModel.errorMessage.collectAsState()
    val isLoading by transferViewModel.isLoading.collectAsState()

    var recipientQuery by remember { mutableStateOf("") }
    var selectedRecipient by remember { mutableStateOf<AccountSearchResult?>(null) }
    var amount by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("USD") }
    var description by remember { mutableStateOf("") }

    val displayedResults = remember(searchResults) {
        searchResults.take(5)
    }


    LaunchedEffect(transferState) {
        if (transferState is TransferViewModel.TransferState.Success) {
            onTransferSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.Start)
            ) {
                Icon(Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Перевести",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = recipientQuery,
                onValueChange = {
                    recipientQuery = it
                    if (it.length > 2) {
                        transferViewModel.searchAccounts(it)
                    } else {
                        selectedRecipient = null
                    }
                },
                label = { Text("Получателя (номер счета или имя)") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    }
                }
            )
            if (searchResults.isNotEmpty()){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .verticalScroll(rememberScrollState())
                ){
                    displayedResults.forEach { account ->
                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedRecipient = account
                                    recipientQuery = "${account.user.username} (${account.accountNumber})"
                                }
                        ){
                            Column (modifier = Modifier
                                .padding(8.dp)){
                                Text(
                                    text = account.user.username,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = account.accountNumber,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = account.user.email,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it.takeWhile { char -> char.isDigit() || char == '.' } },
                label = { Text("Сумма перевода") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Text(
                        text = when (selectedCurrency) {
                            "USD" -> "$"
                            "EUR" -> "€"
                            else -> "₽"
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("USD", "EUR", "RUB").forEach { currency ->
                    FilterChip(
                        selected = selectedCurrency == currency,
                        onClick = { selectedCurrency = currency },
                        modifier = Modifier.padding(end = 8.dp),
                        label = { Text(currency) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Описание перевода
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание (необязательно)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (selectedRecipient == null) {
                        transferViewModel.setErrorMessage("Выберите получателя")
                        return@Button
                    }

                    if (amount.isBlank() || amount.toDoubleOrNull() == null || amount.toDouble() <= 0) {
                        transferViewModel.setErrorMessage("Введите корректную сумму")
                        return@Button
                    }

                    transferViewModel.transfer(
                        recipientId = selectedRecipient?.id,
                        recipientAccountNumber = selectedRecipient?.accountNumber,
                        amount = amount.toDouble(),
                        currencyCode = selectedCurrency,
                        description = description.ifBlank { null }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading && selectedRecipient != null
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Подтвердить перевод")
                }
            }

            // Сообщения об ошибках
//            errorMessage?.let { message ->
//                Text(
//                    text = message,
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(top = 16.dp)
//                )
//            }
        }
    }
}