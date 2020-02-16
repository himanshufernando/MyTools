package himanshu.project.aleph.mytools.views.fragment

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar

import himanshu.project.aleph.mytools.R
import himanshu.project.aleph.mytools.data.Friends
import himanshu.project.aleph.mytools.data.Tools
import himanshu.project.aleph.mytools.data.ToolsBorrowed
import himanshu.project.aleph.mytools.databinding.DialogBorrowedToolByFriendBinding
import himanshu.project.aleph.mytools.utilities.InjectorUtils
import himanshu.project.aleph.mytools.viewmodels.FriendsViewModel
import himanshu.project.aleph.mytools.views.adapters.AllBorrowedAdapter
import himanshu.project.aleph.mytools.views.adapters.FriendsAdapter
import kotlinx.android.synthetic.main.dialog_borrowed_tool_by_friend.*
import kotlinx.android.synthetic.main.friends_fragment.*
import kotlinx.android.synthetic.main.friends_fragment.view.*
import kotlinx.android.synthetic.main.tools_fragment.*


class FriendsFragment : Fragment() {


    private val viewModel: FriendsViewModel by viewModels {
        InjectorUtils.provideFriendsViewModelFactory(requireContext())
    }


    lateinit var dialogToolBorrowed : Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.friends_fragment, container, false)


        val adapter = FriendsAdapter()
        root.recyclerview_friend_list.adapter = adapter
        subscribeFriendsToUi(adapter)

        setupToolReturnRespons()



        return root
    }




    private fun subscribeFriendsToUi(adapter: FriendsAdapter) {
        viewModel.getAllFriends().observe(viewLifecycleOwner) { friendsList ->
            adapter.submitList(friendsList)
            adapter.setOnItemClickListener(object : FriendsAdapter.ClickListener {
                override fun onClick(friends: Friends, aView: View) {
                    viewModel.selectFriend(friends)

                    toolBorrowedDialog()


                }
            })
        }
    }





    private fun setupToolReturnRespons(){
        viewModel.toolLoaningReturnedResponsText.observe(viewLifecycleOwner) { toolsList ->
            Toast.makeText(requireActivity(), toolsList, Toast.LENGTH_SHORT).show()
        }
    }


    private fun toolBorrowedDialog(){
        dialogToolBorrowed= Dialog(requireContext())
        dialogToolBorrowed.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogToolBorrowed.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogToolBorrowed.setCancelable(true)
        var bindingDialog: DialogBorrowedToolByFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_borrowed_tool_by_friend, null, false)
        dialogToolBorrowed.setContentView(bindingDialog.root)
        bindingDialog.viewmodel = ViewModelProviders.of(this).get(FriendsViewModel::class.java)


        val adapter = AllBorrowedAdapter()
        dialogToolBorrowed.recyclerview_friend_tools_list.adapter = adapter
        viewModel.getAllToolsBorrowedList().observe(viewLifecycleOwner) { result ->

            if(result.isEmpty()){
                if(dialogToolBorrowed.isShowing){
                }else{
                    Toast.makeText(requireActivity(), R.string.tool_loaning_no_tool_borrowed, Toast.LENGTH_SHORT).show()
                }

                dialogToolBorrowed.dismiss()

            }else{
                adapter.submitList(result)
                adapter.setOnItemClickListener(object : AllBorrowedAdapter.ClickListener {
                    override fun onClick(toolsBorrowed: ToolsBorrowed, aView: View) {
                        viewModel.toolreturned(toolsBorrowed)

                    }
                })
                dialogToolBorrowed.show()

            }


        }




    }

}
