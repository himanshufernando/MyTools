package himanshu.project.aleph.mytools.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import himanshu.project.aleph.mytools.R
import himanshu.project.aleph.mytools.data.Friends
import himanshu.project.aleph.mytools.data.ToolsBorrowed
import himanshu.project.aleph.mytools.repo.FriendsRepository
import kotlinx.coroutines.launch

class FriendsViewModel internal constructor(toolsRepository: FriendsRepository) : ViewModel() {

    var repo = toolsRepository
    private val friendListTrigger = MutableLiveData<Boolean>()

    private val _toolLoaningReturnedResponsText = MutableLiveData<Int>()
    val toolLoaningReturnedResponsText: LiveData<Int> = _toolLoaningReturnedResponsText

    val selectedFriends = ObservableField<Friends>()



    private val friendList: LiveData<List<Friends>> = Transformations.switchMap(friendListTrigger) {
        repo.getFriend()
    }



    init {
        friendListTrigger.value = true
    }


    fun getAllFriends(): LiveData<List<Friends>> = friendList


    fun selectFriend(friends: Friends) {
        selectedFriends.set(friends)
    }

    fun getAllToolsBorrowedList():  LiveData<List<ToolsBorrowed>> {
        return repo.getToolsBorrowed(selectedFriends.get()!!.friendId)
    }

     fun toolreturned(toolsBorrowed : ToolsBorrowed) {
         viewModelScope.launch {
            repo.getUpdateTool(toolsBorrowed)
            _toolLoaningReturnedResponsText.value = R.string.tool_loaning_return_success_message
        }

    }




}
