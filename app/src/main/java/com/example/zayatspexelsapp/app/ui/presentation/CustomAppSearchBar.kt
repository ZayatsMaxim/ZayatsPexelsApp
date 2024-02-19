package com.example.zayatspexelsapp.app.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zayatspexelsapp.R

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun CustomAppSearchBar(
    text: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    var isActive by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = 24.dp)
            .onFocusChanged {
                isActive = it.isFocused
            },
        value = text,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            containerColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(R.string.search_placeholder),
                fontSize = 14.sp
            )
        },
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_search),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = stringResource(R.string.search_icon_description)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(50.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(text) }
        ),
        trailingIcon = {
            if (isActive) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.search_icon_description),
                    modifier = Modifier.clickable {
                        if (text.isNotBlank()) {
                            onQueryChange("")
                        } else {
                            isActive = false
                        }
                    }
                )
            }
        }
    )
}