package global.msnthrp.langen.platform.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Language")
@Parcelize
data class LanguageEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String = "",

    val longWords: Boolean,

    @ColumnInfo(name = "rep_rules")
    val replacementRules: String,

    @ColumnInfo(name = "let_rules")
    val letterRules: String,

    @ColumnInfo(name = "alphabet")
    val alphabet: String,

    val created: Long = System.currentTimeMillis()

) : Parcelable