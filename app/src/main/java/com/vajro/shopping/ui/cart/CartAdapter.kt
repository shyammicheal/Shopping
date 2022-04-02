package com.vajro.shopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vajro.shopping.R
import com.vajro.shopping.databinding.CartItemBinding
import com.vajro.shopping.room.ProductRoom
import com.vajro.shopping.ui.product.IProductListener

class CartAdapter(private val products:MutableList<ProductRoom>, private val listener: ICartProductListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(val item :CartItemBinding):RecyclerView.ViewHolder(item.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.apply {
            tvProductName.text =
                products[position]!!.name.toString()
            tvQuantity.text = "x  ${products[position]!!.quantity.toString()}"
            tvPrice.text =products[position]!!.price!!.drop(1).replace(",", "").toDouble().times(products[position]!!.quantity!!).toString()
            itemCount.text = products[position]!!.quantity.toString()
            Glide.with(holder.itemView.context).load(products[position]!!.thumb)
                .error(R.drawable.image_placeholder)
                .into(imgProduct)

            btnPlus.setOnClickListener {
                listener.onAddClicked(products[position])
            }
            btnMinus.setOnClickListener {
                listener.onRemoveClicked(products[position])
            }
        }

    }

    override fun getItemCount(): Int {
            return products.size
    }

}