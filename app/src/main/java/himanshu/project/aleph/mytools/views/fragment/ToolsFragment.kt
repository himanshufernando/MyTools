package himanshu.project.aleph.mytools.views.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar

import himanshu.project.aleph.mytools.R

import himanshu.project.aleph.mytools.data.Tools
import himanshu.project.aleph.mytools.databinding.DialogBorrowedToolBinding

import himanshu.project.aleph.mytools.utilities.InjectorUtils
import himanshu.project.aleph.mytools.viewmodels.ToolsViewModel
import himanshu.project.aleph.mytools.views.adapters.AutocompleteFriendsAdaptor
import himanshu.project.aleph.mytools.views.adapters.ToolsAdapter

import kotlinx.android.synthetic.main.dialog_borrowed_tool.*
import kotlinx.android.synthetic.main.friends_fragment.*
import kotlinx.android.synthetic.main.tools_fragment.*
import kotlinx.android.synthetic.main.tools_fragment.view.*

class ToolsFragment : Fragment(){


    private val viewModel: ToolsViewModel by viewModels {
        InjectorUtils.provideToolsViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.tools_fragment, container, false)

        val adapter = ToolsAdapter()
        root.recyclerview_tool_list.adapter = adapter
        subscribeToolsToUi(adapter)

        setupSnackbar()




        viewModel.allToolreturnResponsText.observe(viewLifecycleOwner) { toolsList ->
            Toast.makeText(requireActivity(),toolsList, Toast.LENGTH_SHORT).show()
        }

        return root

    }

    private fun setupSnackbar() {
        viewModel.snackbarText.observe(viewLifecycleOwner) { toolsList ->
            Toast.makeText(requireActivity(),toolsList, Toast.LENGTH_SHORT).show()
        }
    }



    private fun subscribeToolsToUi(adapter: ToolsAdapter) {
        viewModel.getAllTools().observe(viewLifecycleOwner) { toolsList ->
            adapter.submitList(toolsList)
            adapter.setOnItemClickListener(object : ToolsAdapter.ClickListener {
                override fun onClick(tool: Tools, aView: View) {
                    viewModel.selectTool(tool)
                    toolBorrowedDialog()
                }
            })
        }
    }


    private fun toolBorrowedDialog(){
        var dialogToolBorrowed : Dialog = Dialog(requireContext())
        dialogToolBorrowed.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogToolBorrowed.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogToolBorrowed.setCancelable(true)

        var bindingDialog: DialogBorrowedToolBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_borrowed_tool, null, false)
        dialogToolBorrowed.setContentView(bindingDialog.root)
        bindingDialog.viewmodel = ViewModelProviders.of(this).get(ToolsViewModel::class.java)


        viewModel.getAllFriends().observe(viewLifecycleOwner) { friendsList ->
            val adapter = AutocompleteFriendsAdaptor(
                requireContext(),
                R.layout.textview_autocomplete,
                friendsList
            )
            dialogToolBorrowed.autoCompleteTextView_friends.setAdapter(adapter)
        }


        viewModel.toolLoaningResponsText.observe(viewLifecycleOwner) { toolsList ->
            Toast.makeText(requireActivity(),toolsList, Toast.LENGTH_SHORT).show()
            dialogToolBorrowed.dismiss()
        }

        dialogToolBorrowed.show()


    }

}
