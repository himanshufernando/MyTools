package himanshu.project.aleph.mytools.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToolsBorrowedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToolsBorrowed(toolsBorrowed: ToolsBorrowed): Long


    @Query("UPDATE tool_borrowed SET borrowed_is_return = 1 WHERE tool_borrowed_id = :borrowedID")
    suspend fun updateBorrowedToolCount(borrowedID: Long)

    @Query("UPDATE tool_borrowed SET borrowed_is_return = 1")
    suspend fun updateBorrowedAllTool()


}