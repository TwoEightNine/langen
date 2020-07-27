package global.msnthrp.langenc.platform.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import global.msnthrp.langenc.platform.db.dao.LanguageDao
import global.msnthrp.langenc.platform.db.dao.PhraseDao
import global.msnthrp.langenc.platform.db.model.LanguageEntity
import global.msnthrp.langenc.platform.db.model.PhraseEntity

@Database(entities = [LanguageEntity::class, PhraseEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun languageDao(): LanguageDao

    abstract fun phraseDao(): PhraseDao

    companion object {

        private var db: AppDatabase? = null

        fun init(context: Context) {
            db ?: synchronized(this) {
                db ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_room.db"
                )
                    .build()
                    .let { db = it }
            }
        }

        fun get(): AppDatabase = db ?: throw IllegalStateException(
            "AppDatabase should be initialized before calling get()!" +
                    "Use init() for achieving this."
        )
    }
}