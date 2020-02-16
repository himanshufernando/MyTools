package himanshu.project.aleph.mytools.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import himanshu.project.aleph.mytools.repo.FriendsRepository


class FriendsViewModelFactory(private val repository: FriendsRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = FriendsViewModel(repository) as T
}
