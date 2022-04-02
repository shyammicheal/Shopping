package com.vajro.shopping.ui.employees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mindorks.retrofit.coroutines.data.api.ApiHelper
import com.mindorks.retrofit.coroutines.data.api.RetrofitBuilder
import com.vajro.shopping.R
import com.vajro.shopping.databinding.FragmentProductDetailBinding
import com.vajro.shopping.model.Product
import com.vajro.shopping.room.ProductRepository
import com.vajro.shopping.room.ProductRoom
import com.vajro.shopping.service.network.ViewModelFactory
import com.vajro.shopping.ui.main.MainViewModel
import kotlin.math.abs

class ProductDetailFragment : Fragment() {

    lateinit var mViewBinding: FragmentProductDetailBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var productRepository: ProductRepository
    private lateinit var toolbar: Toolbar
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var appBarLayout: AppBarLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                MainViewModel::class.java
            )
        mViewBinding = FragmentProductDetailBinding.inflate(inflater)
        productRepository=ProductRepository(requireContext())
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product =
            arguments?.getSerializable("productData") as Product
        mViewBinding.tvProductName.text = product.name
        mViewBinding.tvProductPrice.text = "$${product.price}"
        mViewBinding.tvDescription.text = "$${product.description}"

        Glide.with(this).load(product.image)
            .placeholder(R.drawable.dummy_image).dontAnimate()
            .into(mViewBinding.collapsingImage)

        mViewBinding.favCart.setOnClickListener {
            findNavController().navigate(R.id.actionToCart)
        }

        mViewBinding.btnAdd.setOnClickListener {
            productRepository.insertOrUpdate(ProductRoom(product.id,product.description,product.href,product.image,product.name,product.price,product.product_id,product.quantity,product.sku,product.special,product.thumb,product.zoom_thumb),true)
        }
        mViewBinding.btnPlus.setOnClickListener {
            productRepository.insertOrUpdate(ProductRoom(product.id,product.description,product.href,product.image,product.name,product.price,product.product_id,product.quantity,product.sku,product.special,product.thumb,product.zoom_thumb),true)
        }
        mViewBinding.btnMinus.setOnClickListener {
            productRepository.insertOrUpdate(ProductRoom(product.id,product.description,product.href,product.image,product.name,product.price,product.product_id,product.quantity,product.sku,product.special,product.thumb,product.zoom_thumb),false)
        }
        mViewBinding.fabFav.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setupToolbar()

        productRepository?.getProductById(product.product_id.toString())!!.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                mViewBinding.tvItemCount.text=it.sumOf { it.quantity!! }.toString()
                mViewBinding.btnAdd.visibility = View.GONE
                mViewBinding.actionWithCount.visibility = View.VISIBLE
            } else {
                mViewBinding.btnAdd.visibility = View.VISIBLE
                mViewBinding.actionWithCount.visibility = View.GONE
            }
        }

        productRepository?.getAllProducts()!!.observe(requireActivity()) {
            mViewBinding.cartItemCount.text = it.sumOf { it.quantity!! }.toString()
        }

    }

    private fun setupToolbar() {
        toolbar = mViewBinding.toolbar
        collapsingToolbar = mViewBinding.toolbarLayout
        appBarLayout = mViewBinding.appBar
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                toolbar.visibility = View.VISIBLE
            } else {
                toolbar.visibility = View.GONE
            }
        })
    }

}