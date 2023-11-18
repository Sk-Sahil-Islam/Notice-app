package com.example.noticeapp2.presentation.notice_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.data.NoticeUiEvent
import com.example.noticeapp2.data.view_models.NoticeViewModel
import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.DisabledColorLight
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.util.Resource
import com.example.noticeapp2.util.checkForButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoticeScreen(
    noticeViewModel: NoticeViewModel = hiltViewModel(),
    navController: NavController,
    heading: String?,
    body: String?,
    noticeId: String?
) {
    val initialHeading = heading ?: ""
    val initialBody = body ?: ""

    val context = LocalContext.current
    val noticeState by remember { noticeViewModel.noticeState }

    var isButtonEnabled by remember {
        mutableStateOf(
            checkForButton(
                initialHeading,
                initialBody,
                noticeState.heading,
                noticeState.body
            )
        )
    }

    val state = noticeViewModel.insertState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "EDIT NOTICE",
                fontFamily = Kanit,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(0)
                }
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }, actions = {
            IconButton(
                onClick = {
                    if (checkForButton(
                            initialHeading,
                            initialBody,
                            noticeState.heading,
                            noticeState.body
                        )
                    ) {
                        noticeViewModel.insertNotice(
                            Notice(
                                heading = noticeState.heading,
                                body = noticeState.body,
                                noticeId = noticeId ?: ""
                            )
                        )
                        isButtonEnabled = false
                    }
                },
                enabled = isButtonEnabled,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Insert",
                    modifier = Modifier.size(30.dp),
                    tint = if (isButtonEnabled) MaterialTheme.colorScheme.primary else DisabledColorLight
                )
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
        )
    }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.Start,
            ) {

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value =
                    if (noticeState.heading.isEmpty() && !noticeViewModel.isEditingStartedHead.value)
                        heading!!
                    else noticeState.heading,
                    onValueChange = {
                        noticeViewModel.isEditingStartedHead.value = true
                        noticeViewModel.onEvent(NoticeUiEvent.HeadingChange(it))
                        isButtonEnabled =
                            checkForButton(
                                initialHeading,
                                initialBody,
                                noticeState.heading,
                                noticeState.body
                            )
                    },
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Light,
                    placeholder = {
                        Text(
                            text = "Heading",
                            fontFamily = Kanit,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        disabledIndicatorColor = LocalContentColor.current.copy(alpha = 0.2f),
                        unfocusedIndicatorColor = LocalContentColor.current.copy(alpha = 0.2f)
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                CustomTextField(
                    modifier = Modifier.fillMaxSize(),
                    value =
                    if (noticeState.body.isEmpty() && !noticeViewModel.isEditingStartedBody.value)
                        body!!
                    else noticeState.body,
                    fontSize = 17.sp,
                    onValueChange = {
                        noticeViewModel.isEditingStartedBody.value = true
                        noticeViewModel.onEvent(NoticeUiEvent.BodyChange(it))
                        isButtonEnabled =
                            checkForButton(
                                initialHeading,
                                initialBody,
                                noticeState.heading,
                                noticeState.body
                            )
                    },
                    placeholder = {
                        Text(
                            text = "Body",
                            fontFamily = Kanit,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W300
                        )
                    },
                    fontWeight = FontWeight.W300,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Default
                    )
                )
            }


            state.value?.let {
                when (it) {
                    is Resource.Error -> {
                        LaunchedEffect(state.value is Resource.Error) {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            isButtonEnabled =
                                checkForButton(
                                    initialHeading,
                                    initialBody,
                                    noticeState.heading,
                                    noticeState.body
                                )
                        }
                        isButtonEnabled =
                            checkForButton(
                                initialHeading,
                                initialBody,
                                noticeState.heading,
                                noticeState.body
                            )
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(context, it.data, Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(0)
                            }
                            isButtonEnabled =
                                checkForButton(
                                    initialHeading,
                                    initialBody,
                                    noticeState.heading,
                                    noticeState.body
                                )
                        }
                    }
                }
            }
        }
    }
}

