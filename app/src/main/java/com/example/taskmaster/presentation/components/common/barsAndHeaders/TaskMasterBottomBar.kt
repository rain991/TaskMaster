package com.example.taskmaster.presentation.components.common.barsAndHeaders

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.data.components.converters.convertNavigationItemToScreen
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskMasterBottomBar(items: List<NavigationItem>, selectedItem: NavigationItem, navController: NavController, userType: UserTypes) {
   val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    NavigationBar(modifier = Modifier.fillMaxWidth())
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = item == selectedItem
                val currentScreen = convertNavigationItemToScreen(item)
                BottomNavigationItem(isSelected = isSelected, navigationItem = item) {
                    navController.navigate(currentScreen.route)
                    screenManagerViewModel.setScreen(userType, currentScreen)
                }
            }
        }
    }
}


@Composable
private fun BottomNavigationItem(isSelected: Boolean, navigationItem: NavigationItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = navigationItem.icon),
                contentDescription = stringResource(id = navigationItem.titleResId),
                modifier = Modifier
                    .animateContentSize()
                    .scale(
                        if (!isSelected) {
                            1.0f
                        } else {
                            1.2f
                        }
                    )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = navigationItem.titleResId),
                modifier = Modifier
                    .animateContentSize()
                    .scale(
                        if (!isSelected) {
                            1.0f
                        } else {
                            1.1f
                        }
                    ) .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = if (isSelected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}
