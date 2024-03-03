package pro.denet.ethertreeapp.core.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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

@Composable
fun DenetSecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    isActive: Boolean = true,
    noActiveText: String = text,
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
        enabled = isActive,
        onClick = onClick,
    ) {
        if (isActive) {
            Icon(
                modifier = Modifier.padding(end = 10.dp),
                imageVector = icon,
                contentDescription = null,
                tint = TreeAppTheme.treeAppColor.primaryText,

            )
        }
        Text(
            text = if (isActive) text else noActiveText,
            style = TreeAppTheme.treeAppTypography.buttonText,
            color = if (isActive) TreeAppTheme.treeAppColor.primaryText else TreeAppTheme.treeAppColor.secondaryText
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DenetSecondaryButtonPreview() {
    EtherTreeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = TreeAppTheme.treeAppColor.background
        ) {
            DenetSecondaryButton(
                modifier = Modifier.fillMaxWidth().height(55.dp).padding(15.dp),
                text = "To Parent",
                isActive = false,
                icon = TreeAppIcon.ArrowRight,
                noActiveText = "No Parent",
                onClick = {}
            )
        }
    }
}
