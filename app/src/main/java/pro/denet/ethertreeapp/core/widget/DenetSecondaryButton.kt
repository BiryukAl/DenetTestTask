package pro.denet.ethertreeapp.core.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.denet.ethertreeapp.core.designsystem.icon.TreeAppIcon
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme
import pro.denet.ethertreeapp.core.widget.model.SideIcon

@Composable
fun DenetSecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    sideIcon: SideIcon = SideIcon.Left,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .border(
                width = 2.dp,
                color = TreeAppTheme.treeAppColor.primaryText,
                shape = RoundedCornerShape(7.dp)
            ),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(horizontal = 15.dp),
        onClick = onClick,
    ) {
        when (sideIcon) {
            SideIcon.Left -> {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = TreeAppTheme.treeAppColor.primaryText
                )
                Text(
                    text = text,
                    style = TreeAppTheme.treeAppTypography.buttonText
                )
            }

            SideIcon.Right -> {
                Text(
                    text = text,
                    color = TreeAppTheme.treeAppColor.primaryText,
                    style = TreeAppTheme.treeAppTypography.buttonText
                )
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = TreeAppTheme.treeAppColor.primaryText
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DenetSecondaryButtonPreview() {
    EtherTreeAppTheme {
        DenetSecondaryButton(
            text = "To Right Node",
            icon = TreeAppIcon.ArrowRight,
            sideIcon = SideIcon.Right,
            onClick = {}
        )
    }
}
