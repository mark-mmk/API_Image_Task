package com.example.api_image_task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.api_image_task.API.ImageResponseItem
import com.squareup.picasso.Picasso

class CustomAdapter(private var list: ArrayList<ImageResponseItem>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shape, parent, false)
        )
    }

    @SuppressLint("MissingInflatedId")
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        val currentItem = list[position]
        Picasso.get().load(currentItem.url)
            .placeholder(android.R.drawable.ic_popup_sync)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val dialogView =
                LayoutInflater.from(holder.itemView.context).inflate(R.layout.custom_dialog, null)
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setView(dialogView)
            val Dialog = builder.create()
            Dialog.setCancelable(false)
            val albumId = dialogView.findViewById<TextView>(R.id.albumId)
            albumId.text = "Album Id : " + currentItem.albumId.toString()
            val id = dialogView.findViewById<TextView>(R.id.id)
            id.text = "Id : " + currentItem.id.toString()
            var image_dialog = dialogView.findViewById<ImageView>(R.id.image_dialog)
            Picasso.get().load(currentItem.url).placeholder(android.R.drawable.ic_popup_sync)
                .into(image_dialog)
            val title = dialogView.findViewById<TextView>(R.id.title)
            title.text = "Title : " + currentItem.title
            val positiveButton = dialogView.findViewById<Button>(R.id.positive_button)
            val negativeButton = dialogView.findViewById<Button>(R.id.negative_button)
            positiveButton.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Yes", Toast.LENGTH_SHORT).show()
                Dialog.dismiss()
            }
            negativeButton.setOnClickListener {
                Dialog.dismiss()
                Toast.makeText(holder.itemView.context, "No", Toast.LENGTH_SHORT).show()
            }
            Dialog.show()
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
}