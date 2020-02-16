package himanshu.project.aleph.mytools.repo

import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import himanshu.project.aleph.mytools.data.Tools
import himanshu.project.aleph.mytools.data.ToolsBorrowed
import himanshu.project.aleph.mytools.data.ToolsBorrowedDao
import himanshu.project.aleph.mytools.data.ToolsDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class ToolsRepository private constructor(private val toolsDao: ToolsDao,private val toolsBorrowedDao: ToolsBorrowedDao) {

    private var cachedTasks: ConcurrentMap<String, ToolsBorrowed>? = null
    companion object {
        @Volatile private var instance: ToolsRepository? = null
        fun getInstance(toolsDao: ToolsDao,ToolsBorrowedDao : ToolsBorrowedDao) =
            instance ?: synchronized(this) {
                instance ?: ToolsRepository(toolsDao,ToolsBorrowedDao).also { instance = it }
            }
    }

    fun getTool() = toolsDao.getTools()
    fun getFriends() = toolsDao.getFriendsList()



    suspend fun toolLoaningInsert(toolsBorrowed: ToolsBorrowed){
        cacheAndPerform(toolsBorrowed) {
            coroutineScope {
                launch { toolsBorrowedDao.insertToolsBorrowed(it) }
            }
        }

    }

    suspend fun setAllToolReturnd(){
        coroutineScope {
                launch { toolsBorrowedDao.updateBorrowedAllTool() }

        }

    }




    private fun cacheTask(toolsborrowed: ToolsBorrowed): ToolsBorrowed {
        val cachedTask = ToolsBorrowed(toolsborrowed.toolBorrowedId, toolsborrowed.borrowedToolId, toolsborrowed.borrowedFriendId,toolsborrowed.borrowedToolCount,
            toolsborrowed.borrowedDate,toolsborrowed.borrowedReturn,toolsborrowed.tools)
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
