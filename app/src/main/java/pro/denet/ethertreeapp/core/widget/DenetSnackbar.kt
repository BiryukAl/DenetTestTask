package pro.denet.ethertreeapp.core.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme


@Composable
fun DenetSnackbar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit = {}
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier.padding(bottom = 64.dp),
        snackbar = { snackbarData ->
            Snackbar(
                content = {
                    Text(
                        text = snackbarData.visuals.message,
                        style = TreeAppTheme.treeAppTypography.errorText
                    )
                },
                action = {
                    snackbarData.visuals.actionLabel?.let { actionLabel ->
                        TextButton(
                            modifier = modifier,
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TreeAppTheme.treeAppColor.background,
                                contentColor = TreeAppTheme.treeAppColor.primary
                            )
                        ) {
                            Text(
                                text = actionLabel,
                                style = TreeAppTheme.treeAppTypography.buttonText
                            )
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DenetSnackbarPreview() {
    EtherTreeAppTheme {
        DenetSnackbar(
            snackbarHostState = SnackbarHostState(),
            onDismiss = {}
        )
    }
}
