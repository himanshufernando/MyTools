package himanshu.project.aleph.mytools.repo
import himanshu.project.aleph.mytools.data.Friends
import himanshu.project.aleph.mytools.data.FriendsDao
import himanshu.project.aleph.mytools.data.ToolsBorrowed
import himanshu.project.aleph.mytools.data.ToolsBorrowedDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


class FriendsRepository private constructor(private val friendsDao: FriendsDao,private val toolsBorrowedDao: ToolsBorrowedDao) {

    private var cachedTasks: ConcurrentMap<String, ToolsBorrowed>? = null

    companion object {
        @Volatile private var instance: FriendsRepository? = null
        fun getInstance(friendsDao: FriendsDao,toolsBorrowedDao: ToolsBorrowedDao) =
            instance ?: synchronized(this) {
                instance ?: FriendsRepository(friendsDao,toolsBorrowedDao).also { instance = it }
            }
    }


    fun getFriend() = friendsDao.getFriendsList()
    fun getToolsBorrowed(friendID : Long) = friendsDao.getAllToolsBorrowed(friendID)


    suspend fun getUpdateTool(toolsBorrowedID: ToolsBorrowed){
         cacheAndPerform(toolsBorrowedID) {
             coroutineScope {
                 launch { toolsBorrowedDao.updateBorrowedToolCount(toolsBorrowedID.toolBorrowedId) }
             }
         }

    }


    private fun cacheTask(toolsborrowed: ToolsBorrowed): ToolsBorrowed {
        val cachedTask = toolsborrowed
        if (cachedTasks == null) {
            cachedTasks = ConcurrentHashMap()
        }
        cachedTasks?.put(cachedTask.toolBorrowedId.toString(), cachedTask)
        return cachedTask
    }

    private inline fun cacheAndPerform(toolsBorrowed: ToolsBorrowed, perform: (ToolsBorrowed) -> Unit) {
        val cachedTask = cacheTask(toolsBorrowed)
        perform(cachedTask)
    }
}
