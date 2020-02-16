package himanshu.project.aleph.mytools.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tools")
data class Tools (
    @PrimaryKey @ColumnInfo(name = "tool_id") val toolId: Long,
    @ColumnInfo(name = "tool_name") val toolName: String,
    @ColumnInfo(name = "tool_image") val toolImageUrl: String,
    @ColumnInfo(name = "tool_count") val toolCount: Int,
    @ColumnInfo(name = "tool_borrowed_count") val toolBorrowedCount : Int?
)
{
}