package pro.denet.ethertreeapp.core.widget

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DenetTopBar(
    modifier: Modifier = Modifier,
    title: String,
) = CenterAlignedTopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = title,
            style = TreeAppTheme.treeAppTypography.screenHeading,
            color = TreeAppTheme.treeAppColor.primaryText,
        )
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = TreeAppTheme.treeAppColor.background,
        scrolledContainerColor = TreeAppTheme.treeAppColor.background,
    )
)


@Preview(showBackground = true)
@Composable
private fun DenetTopBarPreview() {
    EtherTreeAppTheme {
        DenetTopBar(
            title = "Ether Tree App"
        )
    }
}
