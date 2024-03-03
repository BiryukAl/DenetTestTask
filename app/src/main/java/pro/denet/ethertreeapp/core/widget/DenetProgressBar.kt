package pro.denet.ethertreeapp.core.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme
import pro.denet.ethertreeapp.core.widget.model.NodeUiModel

@Composable
fun DenetProgressBar(
    modifier: Modifier = Modifier,
    shouldShow: Boolean
) {
    AnimatedVisibility(visible = shouldShow) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(TreeAppTheme.treeAppColor.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = TreeAppTheme.treeAppColor.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DenetProgressBarPreview() {
    EtherTreeAppTheme {
        DenetProgressBar(
            shouldShow = true
        )
    }
}
