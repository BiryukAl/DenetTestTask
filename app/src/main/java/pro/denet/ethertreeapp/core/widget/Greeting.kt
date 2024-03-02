package pro.denet.ethertreeapp.core.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EtherTreeAppTheme {
        Greeting("Android")
    }
}
