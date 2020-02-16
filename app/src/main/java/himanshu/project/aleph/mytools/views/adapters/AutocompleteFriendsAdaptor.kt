package himanshu.project.aleph.mytools.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.LayoutRes
import himanshu.project.aleph.mytools.data.Friends
import kotlinx.android.synthetic.main.textview_autocomplete.view.*

class AutocompleteFriendsAdaptor (context: Context, @LayoutRes private val layoutResource: Int,
                                  private val list: List<Friends>
) :
    ArrayAdapter<Friends>(context, layoutResource, list),
    Filterable {
    private var friendsList: List<Friends> = list

    override fun getCount(): Int {
        return friendsList.size
    }

    override fun getItem(p0: Int): Friends? {
        return friendsList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(layoutResource, parent, false)
        view.lbl_name.text = "${friendsList[position].friendName}"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(context).inflate(layoutResource, parent, false)

        view.lbl_name.text = "${friendsList[position].friendName}"

        return super.getDropDownView(position, view, parent)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val results = FilterResults()
                val queryString = constraint?.toString()

                val query = if (queryString == null || queryString.isEmpty())
                    list
                else
                    list.filter { it.friendName.toLowerCase().contains(queryString.toLowerCase()) }
                results.values = query
                results.count = query.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
                friendsList = results.values as List<Friends>
                if (results.count > 0) notifyDataSetChanged()
                else notifyDataSetInvalidated()
            }

            override fun convertResultToString(result: Any) = (result as Friends).friendName

        }
    }
}
