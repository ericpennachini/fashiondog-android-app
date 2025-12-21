package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeLarge
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

data class ItemAction<T>(
    val icon: ImageVector,
    val onClick: (item: T) -> Unit
)

@Composable
fun <T> CustomListDialog(
    title: String,
    items: List<T>,
    itemDescription: (T) -> String,
    onItemClick: (T) -> Unit,
    itemExtraAction: ItemAction<T>? = null,
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
                            ConstraintLayout(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val (itemContent, itemAction) = createRefs()

                                Surface(
                                    modifier = Modifier
                                        .constrainAs(itemContent) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(itemAction.start, margin = 4.dp, goneMargin = 0.dp)
                                            height = Dimension.wrapContent
                                            width = Dimension.fillToConstraints
                                        }
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
                                itemExtraAction?.let {
                                    Surface(
                                        modifier = Modifier
                                            .constrainAs(itemAction) {
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                end.linkTo(parent.end)
                                                height = Dimension.fillToConstraints
                                                width = Dimension.wrapContent
                                            }
                                            .clickable { it.onClick(item) },
                                        shape = ShapeSmall,
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(

                                                imageVector = it.icon,
                                                contentDescription = null,
                                                tint = lerp(Color.Red, Color.White, 0.2f)
                                            )
                                        }
                                    }
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
