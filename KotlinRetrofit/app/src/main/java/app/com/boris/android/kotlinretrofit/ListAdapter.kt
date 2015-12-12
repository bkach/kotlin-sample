package app.com.boris.android.kotlinretrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import app.com.boris.android.kotlinretrofit.Model.Result
import java.util.*

/**
 * Created by bkach on 11/24/15.
 */

class ListAdapter : BaseAdapter {

    var results : List<Result> = emptyList();
    var ctx : Context? = null;

    constructor(ctx : Context, results : ArrayList<Result>) {
        this.results = results;
        this.ctx = ctx;
    }

    override fun getCount(): Int {
        return results.size
    }

    override fun getItem(position: Int): Result {
        return results[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var result : Result = getItem(position)
        android.R.layout.simple_list_item_1
        var view = LayoutInflater.from(this.ctx).inflate(R.layout.list_item, parent, false);

        var tv = view.findViewById(R.id.listViewItem) as TextView;
        tv.text = result.name;

        return view;
    }
}