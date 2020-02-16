package himanshu.project.aleph.mytools.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToolsDao {

    @Query("SELECT t.tool_id,t.tool_name,t.tool_image,t.tool_count,(SELECT sum(tb.borrowed_tool_count) FROM tool_borrowed tb WHERE tb.borrowed_tool_id = t.tool_id and tb.borrowed_is_return = 0) tool_borrowed_count from tools t")
    fun getTools(): LiveData<List<Tools>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tools: List<Tools>)

    @Query("SELECT * from friend")
    fun getFriendsList(): LiveData<List<Friends>>



}