package com.example.taskmaster.presentation.components.common.barsAndHeaders

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText
import com.google.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject

@Composable
fun SimplifiedTopBar(onPersonIconClick: () -> Unit) {
    val auth = koinInject<FirebaseAuth>()
    val currentUserDisplayName = auth.currentUser?.displayName
    val currentUserName = currentUserDisplayName?.substringBefore(" ")
    val currentUserSurname = currentUserDisplayName?.substringAfter(" ")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier
            .wrapContentSize()
            .clickable {
                onPersonIconClick()
            }) {
            if (currentUserName != null && currentUserSurname != null) {
                CircleWithText(
                    text = (currentUserName.substring(0, 1).substring(0, 1) + currentUserSurname.substring(0, 1).substring(0, 1)),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.wrapContentHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = currentUserSurname, style = MaterialTheme.typography.titleMedium)
                    Text(text = currentUserName, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.appicon1),
            contentDescription = stringResource(R.string.app_icon),
            modifier = Modifier.size(48.dp)
        )
    }
}
