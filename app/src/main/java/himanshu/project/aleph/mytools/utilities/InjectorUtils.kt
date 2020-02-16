package himanshu.project.aleph.mytools.utilities

import android.content.Context
import himanshu.project.aleph.mytools.data.AppDatabase
import himanshu.project.aleph.mytools.repo.FriendsRepository
import himanshu.project.aleph.mytools.repo.ToolsRepository
import himanshu.project.aleph.mytools.viewmodels.FriendsViewModelFactory
import himanshu.project.aleph.mytools.viewmodels.ToolsViewModelFactory

object InjectorUtils {

    private fun getToolsRepository(context: Context): ToolsRepository {
        return ToolsRepository.getInstance(AppDatabase.getInstance(context.applicationContext).toolsDao(),AppDatabase.getInstance(context.applicationContext).toolsBorrowedDao())
    }

    fun provideToolsViewModelFactory(context: Context): ToolsViewModelFactory {
        val repository = getToolsRepository(context)
        return ToolsViewModelFactory(repository)
    }


    private fun getFriendsRepository(context: Context): FriendsRepository {
        return FriendsRepository.getInstance(AppDatabase.getInstance(context.applicationContext).friendsDao(),AppDatabase.getInstance(context.applicationContext).toolsBorrowedDao())
    }


    fun provideFriendsViewModelFactory(context: Context): FriendsViewModelFactory {
        val repository = getFriendsRepository(context)
        return FriendsViewModelFactory(repository)
    }
}