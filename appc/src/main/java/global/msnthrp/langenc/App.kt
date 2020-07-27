package global.msnthrp.langenc

import android.app.Application
import global.msnthrp.langenc.platform.db.AppDatabase
import global.msnthrp.langenc.ui.utils.Prefs

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        Prefs.init(this)
    }
}