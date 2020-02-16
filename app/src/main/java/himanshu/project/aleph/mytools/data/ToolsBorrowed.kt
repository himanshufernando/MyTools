package himanshu.project.aleph.mytools.data

import androidx.room.*
import java.util.*


@Entity(
    tableName = "tool_borrowed",
    foreignKeys = [
        ForeignKey(entity = Tools::class, parentColumns = ["tool_id"], childColumns = ["borrowed_tool_id"]),
        ForeignKey(entity = Friends::class, parentColumns = ["friend_id"], childColumns = ["borrowed_friend_id"])
    ],
    indices = [Index("borrowed_tool_id"),Index("borrowed_friend_id")]
)
data class ToolsBorrowed (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "tool_borrowed_id") val toolBorrowedId: Long,
    @ColumnInfo(name = "borrowed_tool_id") val borrowedToolId: Long,
    @ColumnInfo(name = "borrowed_friend_id") val borrowedFriendId: Long,
    @ColumnInfo(name = "borrowed_tool_count") val borrowedToolCount: Int,
    @ColumnInfo(name = "borrowed_date") val borrowedDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "borrowed_is_return") val borrowedReturn: Int,
    @Embedded var tools: Tools

)
{


}