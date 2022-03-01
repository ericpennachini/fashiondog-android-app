package ar.com.ericpennachini.fashiondog.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.*
//import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ar.com.ericpennachini.fashiondog.app.ui.theme.ShapeSmall

@ExperimentalMaterial3Api
//@ExperimentalMaterialApi
@Composable
fun SwitchRow(
    isChecked: Boolean,
    mainText: String,
    onCardClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    val stateTextColor: MutableState<Color> = remember { mutableStateOf(Color.Gray) }
    val stateText: MutableState<String> = remember { mutableStateOf("") }
    if (isChecked) {
        stateText.value = "Si"
        stateTextColor.value = MaterialTheme.colorScheme.primary
    } else {
        stateText.value = "No"
        stateTextColor.value = MaterialTheme.colorScheme.outline
    }
    ElevatedCard(
        modifier = Modifier.clickable { onCardClick() },
        shape = ShapeSmall
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (mainTextRef, stateTextRef, switchRef) = createRefs()
            Text(
                text = mainText,
                modifier = Modifier.constrainAs(mainTextRef) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stateText.value,
                modifier = Modifier.constrainAs(stateTextRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(switchRef.start, 4.dp)
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = stateTextColor.value
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.constrainAs(switchRef) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
