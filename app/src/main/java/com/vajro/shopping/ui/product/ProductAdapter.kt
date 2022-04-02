package com.vajro.shopping.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vajro.shopping.databinding.ProductItemBinding
import com.vajro.shopping.model.Product

class ProductAdapter(private val list: List<Product>, private val listener: IProductListener) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(val item: ProductItemBinding) : RecyclerView.ViewHolder(item.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.item.apply {
            if (position % 2 == 0)
                rlItemImage.layoutParams.height = 370
            else
                rlItemImage.layoutParams.height = 450
            tvName.text = "${item.name}"
            tvPrice.text = "${item.price}"

            if (item?.product_quantity!=0) {
                tvItemCount.text = item?.product_quantity?.toString()
                btnAdd.visibility = View.GONE
                actionWithCount.visibility = View.VISIBLE
            } else {
                btnAdd.visibility = View.VISIBLE
                actionWithCount.visibility = View.GONE
            }

            Glide.with(holder.itemView.context).load(list[position].image).dontAnimate()
                .into(imgProduct)
            cardItem.setOnClickListener {
                listener.onProductClicked(item)
            }
            btnAdd.setOnClickListener {
                listener.onAddClicked(item)
            }
            btnPlus.setOnClickListener {
                listener.onAddClicked(item)
            }
            btnMinus.setOnClickListener {
                listener.onRemoveClicked(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}