package com.example.mobile_pt.planner

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.xwray.groupie.ViewHolder
import com.example.mobile_pt.planner.Todo
import kotlinx.android.synthetic.main.item_todo.view.*
import com.example.mobile_pt.R
import org.w3c.dom.Text

class plannerArrayAdapter(context: Context, todo : ArrayList<Todo>) : BaseAdapter() {

    val todo : ArrayList<Todo> = todo
    val inflater = LayoutInflater.from(context)


    override fun getView(position : Int, view : View?, viewGroup: ViewGroup?): View {
        lateinit var viewHolder: ViewHolder
        var planview = view
        if(view == null) {
            viewHolder = ViewHolder()
            planview = inflater.inflate(R.layout.item_todo, viewGroup , false)

            viewHolder.textdate = planview.findViewById(R.id.date)
            viewHolder.texttitle = planview.findViewById(R.id.title)
            Log.d("plannerarrayadapter", todo.get(position).title)

            planview.tag = viewHolder

            viewHolder.textdate.text = todo[position].date.toLong().toString()
            viewHolder.texttitle.text = todo[position].title
            return planview
        }
        else{
            viewHolder = planview!!.tag as ViewHolder
        }
        viewHolder.textdate.text = todo[position].date.toLong().toString()
        viewHolder.texttitle.text = todo[position].title
        return planview!!
    }

    override fun getItem(position: Int): Any {
        val selectItem = todo.get(position)
        return selectItem
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return todo.size
    }

    inner class ViewHolder{
        lateinit var textdate : TextView
        lateinit var texttitle : TextView
    }

}