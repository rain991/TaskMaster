package com.example.taskmaster.presentation.components.teacherComponents.taskDetailed.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText

@Composable
fun StudentAnswerCard(studentName: String, studentSurname: String, isAssigned: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CircleWithText(text = studentSurname.substring(0, 1) + studentName.substring(0, 1), modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.wrapContentWidth()){
                Text(text = studentName, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = studentSurname, style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            if(isAssigned){
                Box(modifier = Modifier.wrapContentWidth()) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "already submitted",
                        modifier = Modifier.scale(1.3f)
                    )
                }
            }else{
                Box(modifier = Modifier.wrapContentWidth()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "not submitted",
                        modifier = Modifier.scale(1.3f)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }

}