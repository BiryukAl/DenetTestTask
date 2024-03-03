package pro.denet.ethertreeapp.core.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.denet.ethertreeapp.R
import pro.denet.ethertreeapp.core.designsystem.icon.TreeAppIcon
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme
import pro.denet.ethertreeapp.core.widget.model.NodeUiModel

@Composable
fun DenetNodeCard(
    modifier: Modifier = Modifier,
    node: NodeUiModel,
    onTrash: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = TreeAppTheme.treeAppColor.surface),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            DenetNodeCardTitle(
                node.id.toString(),
                isRoot = node.isRoot,
                onTrashClick = onTrash,
            )
            DenetNodeCardSupportingText(
                title = stringResource(R.string.address),
                content = node.address
            )
        }
    }

}


@Composable
private fun DenetNodeCardTitle(
    text: String,
    isRoot: Boolean,
    onTrashClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.id_with_id, text),
            style = TreeAppTheme.treeAppTypography.cardTitle,
            color = TreeAppTheme.treeAppColor.primaryText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (isRoot) {
            IconButton(
                onClick = onTrashClick
            ) {
                Icon(
                    imageVector = TreeAppIcon.Trash,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.align(Alignment.Top),
                )
            }
        }
    }

}

@Composable
private fun DenetNodeCardSupportingText(
    title: String,
    content: String,
) {
    Text(
        text = "$title: $content",
        style = TreeAppTheme.treeAppTypography.cardSupportingText,
        color = TreeAppTheme.treeAppColor.secondaryText,
        overflow = TextOverflow.Visible
    )
}

@Preview(showBackground = true)
@Composable
private fun DenetNodeCardPreview() {
    val node = NodeUiModel(
        id = 113,
        address = "0x" + "cd2a3d9f938e13cd947ec05abc7fe734df8dd826",
        isRoot = true
    )

    EtherTreeAppTheme {
        DenetNodeCard(
            node = node,
            onTrash = {},
            onClick = {},
        )
    }
}