package pro.denet.ethertreeapp.app

import android.app.Application
import pro.denet.ethertreeapp.core.sl.ProvideInstances
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        val debugBuild: Boolean = false /* BuildConfig.DEBUG*/

        if (debugBuild) {
            ProvideInstances.Base(this)
        } else {
            ProvideInstances.Mock(this)
        }
    }
}
