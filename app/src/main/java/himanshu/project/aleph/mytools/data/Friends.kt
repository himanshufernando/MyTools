package himanshu.project.aleph.mytools.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friend")
data class Friends (
    @PrimaryKey @ColumnInfo(name = "friend_id") val friendId: Long,
    @ColumnInfo(name = "friend_name") val friendName: String
)
{
}