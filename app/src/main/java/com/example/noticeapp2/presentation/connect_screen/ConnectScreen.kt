package com.example.noticeapp2.presentation.connect_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noticeapp2.R
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.ui.theme.PrimaryColorLight
import com.example.noticeapp2.ui.theme.WhitePink

@Composable
fun ConnectScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 16.dp, end = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.size(8.dp))

        ImageCard(Modifier.align(CenterHorizontally))

        Spacer(modifier = Modifier.size(30.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Let others ",
                fontFamily = Kanit,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Text(
                text = "know.",
                fontFamily = Kanit,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                color = PrimaryColorLight
            )
        }
        Text(
            text = "An app for managing notifications tailored to a user-friendly experience.",
            fontFamily = Kanit,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(38.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(
                onClick = {
                    navController.navigate(Screens.SignInScreen.route){
                        popUpTo(Screens.SignInScreen.route){
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.width(125.dp)
            ) {
                Text(text = "Sign In",  fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                onClick = {
                    navController.navigate(Screens.SignUpScreen.route){
                        popUpTo(Screens.SignUpScreen.route){
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.width(125.dp)
            ) {
                Text(text = "Sign Up", fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        OrSection(color = LocalContentColor.current.copy(alpha = 0.4f))

        Text(
            text = "Connect using different method",
            modifier = Modifier.align(CenterHorizontally),
            fontFamily = Kanit,
            fontSize = 12.sp,
            color = LocalContentColor.current.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp), horizontalArrangement = Arrangement.Center) {
            GoogleConnectOption(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(16.dp))
            FacebookConnectOption(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        modifier = modifier.shadow(10.dp, shape = RoundedCornerShape(28.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.connect_image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun OrSection(
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .background(color)
                    .weight(1f)
            )
            Text(
                text = "or",
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                color = color,
                fontFamily = Kanit,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp)
                    .weight(1f)
                    .background(color)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleConnectOption(modifier: Modifier = Modifier) {
    Card(
        onClick = {

        },
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "google",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Google", fontFamily = Kanit, color = LocalContentColor.current.copy(0.75f))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacebookConnectOption(
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {

        },
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = "facebook",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Facebook", fontFamily = Kanit, color = LocalContentColor.current.copy(0.75f))
        }
    }
}