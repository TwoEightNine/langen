package global.msnthrp.langenc.platform.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Phrase")
@Parcelize
data class PhraseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val text: String,

    @ColumnInfo(name = "lang_id")
    @ForeignKey(
        entity = LanguageEntity::class,
        parentColumns = ["id"],
        childColumns = ["lang_id"],
        onDelete = ForeignKey.CASCADE
    )
    val languageId: Int,

    val created: Long = System.currentTimeMillis()
) : Parcelable