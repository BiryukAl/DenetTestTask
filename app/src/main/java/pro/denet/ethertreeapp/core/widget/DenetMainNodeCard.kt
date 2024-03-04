package pro.denet.ethertreeapp.core.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.denet.ethertreeapp.R
import pro.denet.ethertreeapp.core.designsystem.icon.TreeAppIcon
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme
import pro.denet.ethertreeapp.core.widget.model.NodeUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DenetMainNodeCard(
    modifier: Modifier = Modifier,
    node: NodeUiModel,
    onTrashClick: () -> Unit,
    onPress: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = TreeAppTheme.treeAppColor.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { },
                    onLongClick = onPress
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            DenetMainNodeCardTitle(
                text = if (node.isRoot)
                    stringResource(R.string.root)
                else
                    stringResource(R.string.node),
                isRoot = node.isRoot,
                onTrashClick = onTrashClick,
            )
            Text(
                text = "${stringResource(R.string.id)}: ${node.id}",
                style = TreeAppTheme.treeAppTypography.cardTitle,
                color = TreeAppTheme.treeAppColor.onPrimary,
                overflow = TextOverflow.Visible
            )
            Text(
                text = "${stringResource(R.string.address)}: 0x${node.address}",
                style = TreeAppTheme.treeAppTypography.cardSupportingText,
                color = TreeAppTheme.treeAppColor.secondaryText,
                overflow = TextOverflow.Visible
            )
        }
    }
}

@Composable
private fun DenetMainNodeCardTitle(
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
            textAlign = TextAlign.Center,
            modifier = if (!isRoot)
                Modifier.weight(9f)
            else
                Modifier.weight(1f),
            text = text,
            style = TreeAppTheme.treeAppTypography.cardTitle,
            color = TreeAppTheme.treeAppColor.onPrimary,
        )
        if (!isRoot) {
            IconButton(
                modifier = Modifier.weight(1f),
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


@Preview(showBackground = true)
@Composable
private fun DenetMainNodeCardPreview() {
    val node = NodeUiModel(
        id = 113,
        address = "0x" + "cd2a3d9f938e13cd947ec05abc7fe734df8dd826",
        isRoot = false
    )

    EtherTreeAppTheme {
        DenetMainNodeCard(
            node = node,
            onTrashClick = {},
            onPress = {},
        )
    }
}