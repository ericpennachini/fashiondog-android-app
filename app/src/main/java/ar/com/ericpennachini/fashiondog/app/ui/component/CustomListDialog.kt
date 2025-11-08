package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeLarge
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

@Composable
fun <T> CustomListDialog(
    title: String,
    items: List<T>,
    itemDescription: (T) -> String,
    onItemClick: (T) -> Unit,
    dismissButtonText: String,
    onDismiss: () -> Unit,
    extraButtonText: String,
    onExtraButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (items.isNotEmpty()) {
                    LazyColumn {
                        items(items) { item ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemClick(item) },
                                shape = ShapeSmall,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            ) {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)) {
                                    Text(
                                        text = itemDescription(item),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            if (items.size > 1) {
                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "(Vacío)",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        shape = ShapeLarge,
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = dismissButtonText)
            }
        },
        confirmButton = {
            FilledTonalButton(onClick = onExtraButtonClick) {
                Text(text = extraButtonText)
            }
        }
    )
}
