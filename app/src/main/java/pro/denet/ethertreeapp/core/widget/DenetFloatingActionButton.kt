package pro.denet.ethertreeapp.core.widget

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import pro.denet.ethertreeapp.core.designsystem.icon.TreeAppIcon
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme

@Composable
fun DenetFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = CircleShape,
        containerColor = TreeAppTheme.treeAppColor.primary,
        contentColor = TreeAppTheme.treeAppColor.onPrimary
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TreeAppTheme.treeAppColor.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DenetFloatingActionButtonPreview() {
    EtherTreeAppTheme {
        DenetFloatingActionButton(
            icon = TreeAppIcon.Add,
            onClick = {}
        )
    }
}
