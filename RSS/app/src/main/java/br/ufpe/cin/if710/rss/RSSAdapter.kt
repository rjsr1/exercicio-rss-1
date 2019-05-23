package br.ufpe.cin.if710.rss

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlista.view.*
import java.net.URI

class RSSAdapter (private val listRSS:List<ItemRSS>,private val context:Context): RecyclerView.Adapter<RSSAdapter.RSSViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RSSViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.itemlista,parent,false)
        return RSSViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRSS.count()
    }

    override fun onBindViewHolder(holder: RSSViewHolder, position: Int) {
        val item=listRSS.get(position)
        holder.tv_item_data.setText(item.link)
        holder.tv_item_titulo.setText(item.toString())
    }

    class RSSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_item_titulo=itemView.item_titulo
        val tv_item_data=itemView.item_data

        init {
            itemView.setOnClickListener{_->
                val i = Intent(Intent.ACTION_VIEW)
                i.data= Uri.parse(tv_item_data.text.toString())
                startActivity(itemView.context,i,null)
            }
        }
    }
}