package himanshu.project.aleph.mytools.utilities

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import himanshu.project.aleph.mytools.data.AppDatabase
import himanshu.project.aleph.mytools.data.Friends
import himanshu.project.aleph.mytools.data.Tools
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val database = AppDatabase.getInstance(applicationContext)

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(TOOLS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->

                   val toolsType = object : TypeToken<List<Tools>>() {}.type
                    val toolsList: List<Tools> = Gson().fromJson(jsonReader, toolsType)
                    database.toolsDao().insertAll(toolsList)




                }
            }
            applicationContext.assets.open(FRIENDS_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->

                    val friendType = object : TypeToken<List<Friends>>() {}.type
                    val friendList: List<Friends> = Gson().fromJson(jsonReader, friendType)

                    database.friendsDao().insertAll(friendList)

                    Result.success()
                }
            }




        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}