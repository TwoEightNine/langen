package global.msnthrp.langen.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Phrase(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val text: String,

    @ColumnInfo(name = "lang_id")
    @ForeignKey(
        entity = Language::class,
        parentColumns = ["id"],
        childColumns = ["lang_id"],
        onDelete = ForeignKey.CASCADE
    )
    val languageId: Int,

    val created: Long = System.currentTimeMillis()
) : Parcelable