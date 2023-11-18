package com.example.noticeapp2.presentation.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.data.view_models.NoticeViewModel
import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.ContainerBackgroundDark
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.ui.theme.WhitePink
import com.example.noticeapp2.util.Resource
import com.example.noticeapp2.util.timeParse
import com.google.firebase.Timestamp
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.Locale

@Composable
fun NoticeBoard(
    noticeViewModel: NoticeViewModel = hiltViewModel(),
    navController: NavController
) {
    val res by noticeViewModel.res.collectAsState()
    when (res) {
        is Resource.Error -> {
            Text(text = "There was an error")
        }

        is Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(13.dp)) {
                item { Spacer(modifier = Modifier.height(10.dp)) }

                items(res.data!!) { item ->
                    NoticeCard(notice = item) {

                        val bodyString = Base64.getUrlEncoder().encodeToString(item.body.toByteArray())
                        navController.navigate("${Screens.EditNoticeScreen.route}?heading=${item.heading}&body=${bodyString}&noticeId=${item.noticeId}"){
                            popUpTo(Screens.AddNoticeScreen.route){
                                inclusive = true
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(10.dp)) }
            }
        }
    }
}

@Composable
fun NoticeCard(
    modifier: Modifier = Modifier, notice: Notice, onUpdate: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = if(isSystemInDarkTheme()) ContainerBackgroundDark else WhitePink ),
        border = BorderStroke(width = 1.dp, MaterialTheme.colorScheme.onPrimaryContainer.copy(0.35f))
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .clickable { onUpdate() }) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val time = timeParse(notice.timestamp?.let { notice.timestamp as Timestamp })
                Text(
                    text = if(notice.isEdited) "Edited $time" else time,
                    fontFamily = Kanit,
                    fontWeight = FontWeight.W300,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.75f)
                )
                Text(
                    text = notice.heading.ifEmpty { "(No heading)" },
                    fontFamily = Kanit,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = notice.body.ifBlank { "(No body)" },
                    fontFamily = Kanit,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W300
                )
            }
        }
    }
}


