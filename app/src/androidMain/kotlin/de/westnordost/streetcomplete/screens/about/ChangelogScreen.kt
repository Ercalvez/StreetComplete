package de.westnordost.streetcomplete.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.changelog.Changelog
import de.westnordost.streetcomplete.ui.common.BackIcon
import de.westnordost.streetcomplete.ui.common.HtmlText
import de.westnordost.streetcomplete.ui.ktx.plus
import de.westnordost.streetcomplete.ui.theme.titleLarge

/** Shows the full changelog */
@Composable
fun ChangelogScreen(
    viewModel: ChangelogViewModel,
    onClickBack: () -> Unit
) {
    val changelog by viewModel.changelog.collectAsState()

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(R.string.about_title_changelog)) },
            windowInsets = AppBarDefaults.topAppBarWindowInsets,
            navigationIcon = { IconButton(onClick = onClickBack) { BackIcon() } },
        )
        changelog?.let { changelog ->
            SelectionContainer {
                val insets = WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                ).asPaddingValues()
                ChangelogList(
                    changelog = changelog,
                    paddingValues = insets + PaddingValues(16.dp),
                    modifier = Modifier.consumeWindowInsets(insets)
                )
            }
        }
    }
}

@Composable
fun ChangelogList(
    changelog: Changelog,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues
    ) {
        itemsIndexed(
            items = changelog.entries.toList(),
            key = { index, _ -> index }
        ) { index, (version, html) ->
            if (index > 0) Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = version,
                style = MaterialTheme.typography.titleLarge
            )
            HtmlText(
                html = html,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
