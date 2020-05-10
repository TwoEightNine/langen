package global.msnthrp.langen

import android.app.Application
import global.msnthrp.langen.db.AppDatabase
import global.msnthrp.langen.utils.Prefs

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        Prefs.init(this)
    }
}