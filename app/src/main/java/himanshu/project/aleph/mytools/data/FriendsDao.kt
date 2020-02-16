package himanshu.project.aleph.mytools.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendsDao {


    @Query("SELECT * from friend")
    fun getFriendsList(): LiveData<List<Friends>>

    @Query("""SELECT *,sum(borrowed_tool_count) borrowed_tool_count From tool_borrowed INNER JOIN tools ON tool_borrowed.borrowed_tool_id = tools.tool_id WHERE borrowed_friend_id =:friendid AND borrowed_is_return !=1 GROUP BY borrowed_tool_id""")
    fun getAllToolsBorrowed(friendid: Long): LiveData<List<ToolsBorrowed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(friends: List<Friends>)





}