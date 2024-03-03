package pro.denet.ethertreeapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import pro.denet.ethertreeapp.R
import pro.denet.ethertreeapp.core.designsystem.theme.EtherTreeAppTheme
import pro.denet.ethertreeapp.core.designsystem.theme.TreeAppTheme
import pro.denet.ethertreeapp.core.widget.DenetTopBar
import pro.denet.ethertreeapp.core.widget.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtherTreeAppTheme {
                Scaffold(
                    topBar = {
                        DenetTopBar(
                            title = getString(R.string.app_name)
                        )
                    },
                    containerColor = TreeAppTheme.treeAppColor.background
                ) {
                    Surface(
                        modifier = Modifier.padding(it)
                    ) {
                        Greeting("Denet company")
                    }
                }
            }
        }
    }
}
