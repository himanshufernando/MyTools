package himanshu.project.aleph.mytools.viewmodels

import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import himanshu.project.aleph.mytools.data.Friends
import himanshu.project.aleph.mytools.data.Tools
import himanshu.project.aleph.mytools.R
import himanshu.project.aleph.mytools.data.ToolsBorrowed
import himanshu.project.aleph.mytools.repo.ToolsRepository
import kotlinx.coroutines.launch
import java.util.*

class ToolsViewModel internal constructor(toolsRepository: ToolsRepository) : ViewModel() {

    var repo = toolsRepository

    private val toolsListTrigger = MutableLiveData<Boolean>()
    private val friendListTrigger = MutableLiveData<Boolean>()


    var toolCount = MutableLiveData<String>()

    private val _snackbarText = MutableLiveData<Int>()
    val snackbarText: LiveData<Int> = _snackbarText


    private val _toolLoaningResponsText = MutableLiveData<Int>()
    val toolLoaningResponsText: LiveData<Int> = _toolLoaningResponsText


    private val _allToolreturnResponsText = MutableLiveData<Int>()
    val allToolreturnResponsText: LiveData<Int> = _allToolreturnResponsText



    val selectedTool = ObservableField<Tools>()
    private var selectedFriendID: Long = 0


    private val toolList: LiveData<List<Tools>> = Transformations.switchMap(toolsListTrigger) {
        repo.getTool()
    }
    private val friendList: LiveData<List<Friends>> = Transformations.switchMap(friendListTrigger) {
        repo.getFriends()
    }


    init {
        toolsListTrigger.value = true
        friendListTrigger.value = true
    }


    fun getAllTools(): LiveData<List<Tools>> = toolList
    fun getAllFriends(): LiveData<List<Friends>> = friendList


    fun selectTool(tool: Tools) {
        selectedTool.set(tool)
    }


    fun onFriendItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedFriend = parent!!.getItemAtPosition(position) as Friends
        selectedFriendID = selectedFriend.friendId
    }

    fun toolLoaning() {

        var totalItemCount = selectedTool.get()!!.toolCount

        var totalBorrowdCount = if(selectedTool.get()!!.toolBorrowedCount==null){
            0
        }else{
            selectedTool.get()!!.toolBorrowedCount!!
        }


        var totalRemainingItemCount = totalItemCount - totalBorrowdCount!!
        var requestToolCount = 1

        if (selectedFriendID == 0L) {
            _snackbarText.value = R.string.empty_friend_borrowed_message
            return
        }

        if (totalRemainingItemCount < requestToolCount) {
            _snackbarText.value = R.string.tool_count_message
            return
        }


        var toolBorrowed = ToolsBorrowed(
            0, selectedTool.get()!!.toolId, selectedFriendID, requestToolCount,
            Calendar.getInstance(), 0, selectedTool.get()!!
        )


        insertToolBorrowed(toolBorrowed)
    }

    private fun insertToolBorrowed(toolsBorrowed: ToolsBorrowed) {
        viewModelScope.launch {
            repo.toolLoaningInsert(toolsBorrowed)
            _toolLoaningResponsText.value = R.string.tool_loaning_success_message
            selectedFriendID = 0L
        }
    }


    fun setAlltools(){
        viewModelScope.launch {
            repo.setAllToolReturnd()
            _allToolreturnResponsText.value = R.string.all_tool_return_success_message

        }

    }

}
