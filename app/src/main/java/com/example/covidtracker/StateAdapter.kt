package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import kotlinx.android.synthetic.main.item_list.view.*

class StateAdapter(val list: List<StatewiseItem>):BaseAdapter() {
    override fun getCount(): Int {
     return list.size
    }

    override fun getItem(position: Int): Any {
      return list[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView?:LayoutInflater.from(parent?.context).inflate(R.layout.item_list,parent,false)
        val item= list[position]
        view.confirmTV.apply {
            text = spanableDelta(
                "${item.confirmed}\n ↑ ${item.deltaconfirmed ?: "0"}",
                "#D32F2F",
                item.confirmed?.length ?: 0
            )
        }
        view.acitveitem.text = spanableDelta(
            "${item.active}\n ↑ ${item.deltaactive ?: "0"}",
            "#1976D2",
            item.confirmed?.length ?: 0
        )
        view.recovredTv1.text = spanableDelta(
            "${item.recovered}\n ↑ ${item.deltarecovered ?: "0"}",
            "#388E3C",
            item.recovered?.length ?: 0
        )
        view.deaths.text = spanableDelta(
            "${item.deaths}\n ↑ ${item.deltadeaths ?: "0"}",
            "#FBC02D",
            item.deaths?.length ?: 0
        )
        view.stateTV.text = item.state
        return view
        return view
    }
}