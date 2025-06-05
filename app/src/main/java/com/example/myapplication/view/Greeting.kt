package com.example.myapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R


@Composable
fun Greeting(navController: NavController,){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center,){
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column (
                Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ){
                Image(
                    painter = painterResource(id = R.drawable.graphic),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
                Spacer(Modifier.height(50.dp))

                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier =  Modifier.size(100.dp)
                )
                Spacer(Modifier.height(50.dp))

                Text("Добро пожаловать", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold )
                Spacer(Modifier.height(10.dp))
                Text("Мобильный банк PWBank", textAlign = TextAlign.Center, fontSize = 15.sp, color = Color.Gray)
                Spacer(Modifier.height(50.dp))

                Button(
                    onClick = {navController.navigate("GreetingB")},
                    modifier = Modifier .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(102, 90, 240, 255)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Далее", fontSize = 18.sp, color = Color.White)
                }
                Spacer(Modifier.height(10.dp))

                TextButton(onClick = {navController.navigate("login")}) {
                    Text("Пропустить", color = Color.Gray, fontSize = 18.sp)
                }
            }
        }

    }

}

@Composable
fun GreetingB(navController: NavController,){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center,){
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column (
                Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ){
                Image(
                    painter = painterResource(id = R.drawable.graphic),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
                Spacer(Modifier.height(50.dp))

                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = null,
                    modifier =  Modifier.size(100.dp)
                )
                Spacer(Modifier.height(50.dp))
                Text("Онлайн-оплата", textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold  )
                Spacer(Modifier.height(10.dp))
                Text("Оплата потребительского кредита, оплата счетов\n" +
                        "и многие другие услуги", textAlign = TextAlign.Center, fontSize = 15.sp, color = Color.Gray)
                Spacer(Modifier.height(30.dp))

                Button(
                    onClick = {navController.navigate("register")},
                    modifier = Modifier .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(102, 90, 240, 255)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Далее", fontSize = 18.sp, color = Color.White)
                }
                Spacer(Modifier.height(40.dp))


            }
        }

    }

}
