package com.example.noticeapp2.presentation.add_notice_screen

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.data.view_models.NoticeViewModel
import com.example.noticeapp2.navigation.Screens
import com.example.noticeapp2.ui.theme.Kanit
import com.example.noticeapp2.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoticeScreen(
    noticeViewModel: NoticeViewModel = hiltViewModel(), navController: NavController
) {
    val context = LocalContext.current
    var heading by remember { noticeViewModel.heading }
    var body by remember { noticeViewModel.body }

    val state = noticeViewModel.insertState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "ADD NOTICE", fontFamily = Kanit, fontWeight = FontWeight.ExtraLight, fontSize = 18.sp
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
            IconButton(onClick = {
                noticeViewModel.insertNotice(Notice(heading = heading, body = body))
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Insert",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
        )
    }) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(padding),
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
                    value = heading,
                    onValueChange = { heading = it },
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Light,
                    placeholder = {
                        Text(
                            text = "Heading",
                            fontFamily = Kanit,
                            fontSize = 27.sp,
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
                    value = body,
                    onValueChange = { body = it },
                    placeholder = {
                        Text(
                            text = "Body",
                            fontFamily = Kanit,
                            fontSize = 18.sp,
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
                        }
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            Toast.makeText(context, it.data, Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(0)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder : @Composable (() -> Unit)? = null,
    fontSize: TextUnit = 18.sp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    fontWeight: FontWeight = FontWeight.Normal
) {
    val interactionSource = remember { MutableInteractionSource() }
    // parameters below will be passed to BasicTextField for correct behavior of the text field,
    // and to the decoration box for proper styling and sizing
    val enabled = true
    val singleLine = false
    val visualTransformation = VisualTransformation.None
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = visualTransformation,
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        textStyle = LocalTextStyle.current.copy(
            color = LocalContentColor.current,
            fontSize = fontSize, fontFamily = Kanit,
            fontWeight = fontWeight),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = keyboardOptions
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value,
            colors = colors,
            visualTransformation = visualTransformation,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            placeholder = placeholder,
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 8.dp, end = 8.dp
            )
        )
    }
}