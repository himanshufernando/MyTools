package himanshu.project.aleph.mytools.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import himanshu.project.aleph.mytools.repo.ToolsRepository

class ToolsViewModelFactory (private val repository: ToolsRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = ToolsViewModel(repository) as T
}
