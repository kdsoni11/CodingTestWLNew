package com.codingtestwl.list.view.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.codingtestwl.R
import com.codingtestwl.databinding.MainListItemBinding
import com.codingtestwl.databinding.PopUpBinding
import com.codingtestwl.list.model.Model
import com.codingtestwl.list.view.MainActivity
import com.codingtestwl.util.ImageCache
import com.codingtestwl.util.Utility
import com.google.gson.Gson
import java.util.*

class ListRecyclerAdapter(var mutableList: MutableList<Model>, var filterList: MutableList<Model>) :
    RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {

    private var customFilter: CustomFilter? = null
    private var searchText:String=""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mutableList[position]
        //  item.itemImage = holder.image

        val heightColor: Int = ResourcesCompat.getColor(holder.context.resources,R.color.appColorPrimary,null)
        holder.titleText.text =Utility.highlightText(searchText,item.author,heightColor)
        holder.descText.text = item.url
        val cache = ImageCache.instance
        val bitmap = cache?.retrieveBitmapFromCache(item.download_url)
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap)
            holder.downloadImageLay.visibility = View.GONE
            holder.showProgress.visibility = View.GONE
        } else {
            holder.showProgress.visibility = View.GONE
            holder.image.setImageResource(R.mipmap.no_item)
            holder.downloadImageLay.visibility = View.VISIBLE
            holder.downloadImageLay.setOnClickListener { view ->
                if (view != null) {
                    holder.showProgress.visibility = View.VISIBLE
                    holder.downloadImageLay.visibility = View.GONE
                    val tag = view.tag as Tag
                    (holder.context as MainActivity).listViewModel.downloadImageFromUrl(tag.item.download_url)
                        .observe((holder.context as MainActivity), {
                            if (it is String) {
                                holder.showProgress.visibility = View.GONE
                                holder.downloadImageLay.visibility = View.VISIBLE
                                Toast.makeText((tag.viewHolder.context), it, Toast.LENGTH_LONG)
                                    .show()
                            } else if (it is Bitmap) {
                                holder.showProgress.visibility = View.GONE
                                holder.downloadImageLay.visibility = View.GONE
                                tag.item.bitmap = it
                                tag.viewHolder.image.setImageBitmap(it)
                                notifyDataSetChanged()
                            }
                        })
                }
            }
        }
        holder.itemView.setOnClickListener { view ->
            if (view != null) {
                val tag = view.tag as Tag
                descriptionPopUp(tag)
            }
        }
        val tag = Tag()
        tag.viewHolder = holder
        tag.item = item
        holder.downloadImageLay.tag = tag
        holder.itemView.tag = tag
    }

    inner class Tag {
        lateinit var viewHolder: ViewHolder
        lateinit var item: Model
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    inner class ViewHolder(var context: Context, binding: MainListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var titleText = binding.titleText
        var descText = binding.description
        var image = binding.itemImage
        var showProgress = binding.showProgress
        var downloadImageLay = binding.downloadImageLay
    }

    //Custom search filter INNER CLASS
    inner class CustomFilter : Filter() {
        override fun performFiltering(searchText: CharSequence): FilterResults {
            val constraint: CharSequence = searchText
            val results = FilterResults()
            val filters: MutableList<Model> = ArrayList()
            this@ListRecyclerAdapter.searchText = constraint.toString()
            if (constraint.isNotEmpty()) {
                val searchStr = constraint.toString().uppercase().trim()

                //get specific items
                for (listItem in filterList) {

                    val isLNameTrue =
                        listItem.author.uppercase().contains(searchStr.trim { it <= ' ' })

                    try {
                        if (isLNameTrue) {
                            val clone: Model = clone(listItem)
                            filters.add(clone)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                //FOR END
                results.count = filters.size
                results.values = filters
            } else {
                results.count = filterList.size
                results.values = filterList
            }
            return results
        }

        private fun clone(listItem: Model): Model {
            val stringListItem = Gson().toJson(listItem, Model::class.java)
            return Gson().fromJson(stringListItem, Model::class.java)
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            //unchecked cast
            mutableList = results.values as MutableList<Model>
            notifyDataSetChanged()
        }
    }

    fun getFilter(): Filter? {
        if (customFilter == null) {
            customFilter = CustomFilter()
        }
        return customFilter
    }

    /*Content description pop up for current row */
    private fun descriptionPopUp(tag:Tag) {
        //Initialize dialog instanse
        val dialog  = AlertDialog.Builder(tag.viewHolder.context)
        //Bind xml view
        val binding = PopUpBinding.inflate(LayoutInflater.from(tag.viewHolder.context))
        binding.descriptionText.text = tag.item.download_url

        //Set Custom Xml view to dialog
        dialog.setView(binding.root)

        //Create alert
        val alert = dialog.create()

        if (tag.item.bitmap!=null)
            binding.imageHeader.setImageBitmap(tag.item.bitmap)
        binding.titleText.text = tag.item.author

        binding.okBtn.setOnClickListener {
            alert.dismiss()
        }

        //Make transparent background of dialog
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()
    }
}