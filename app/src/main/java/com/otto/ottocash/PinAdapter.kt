//package com.otto.ottocash
//
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import org.w3c.dom.Text
//
//class PinAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
//        var view: View
//        if (position == 11 || position == 9) {
//            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_pin_image, viewGroup, false)
//            return imageViewHolder(view)
//        } else {
//            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_pin_number, viewGroup, false)
//            return numberViewHolder(view)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return 12
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is numberViewHolder) {
//            holder.textViewNumber.text = position.toString()
//        } else if (holder is imageViewHolder) {
//            holder.imageViewItem.visibility = View.VISIBLE
//        }
//    }
//
//    class numberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var textViewNumber = itemView.findViewById<TextView>(R.id.textViewNumber)
//    }
//
//    class imageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var imageViewItem = itemView.findViewById<ImageView>(R.id.imageViewItem)
//    }
//}