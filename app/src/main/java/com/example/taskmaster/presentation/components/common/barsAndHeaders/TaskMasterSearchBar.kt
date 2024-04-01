package com.example.taskmaster.presentation.components.common.barsAndHeaders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskMasterSearchBar(searchText: String, onSearchTextChange: (String) -> Unit, onSearch: (String) -> Unit, isSearching: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        SearchBar(
            query = searchText,
            onQueryChange = onSearchTextChange,    //viewModel::onSearchTextChange   TRIM!!
            onSearch = onSearch,       // viewModel::onSearchTextChange  search trigger
            active = isSearching,
            onActiveChange = { /* viewModel.onToogleSearch()*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

        }
    }
}
