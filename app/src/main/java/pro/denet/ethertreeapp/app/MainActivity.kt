package pro.denet.ethertreeapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.feature.navigateOnTree.presentation.MainNavigationOnTreeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtherTreeAppTheme {
                MainNavigationOnTreeScreen()
            }
        }
    }
}
