package com.vajro.shopping.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mindorks.retrofit.coroutines.data.api.ApiHelper
import com.mindorks.retrofit.coroutines.data.api.RetrofitBuilder
import com.vajro.shopping.R
import com.vajro.shopping.databinding.FragmentProductBinding
import com.vajro.shopping.model.Product
import com.vajro.shopping.room.ProductRepository
import com.vajro.shopping.room.ProductRoom
import com.vajro.shopping.service.network.ViewModelFactory
import com.vajro.shopping.showToast
import com.vajro.shopping.ui.main.MainViewModel
import com.vajro.shopping.utils.Status

class ProductFragment : Fragment() {
    lateinit var mViewBinding: FragmentProductBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var productRepository: ProductRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                MainViewModel::class.java
            )
        mViewBinding = FragmentProductBinding.inflate(inflater)
        mViewBinding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        productRepository=ProductRepository(requireContext())
        getMyFeedback()
        getAllProducts()
        mViewBinding.favCart.setOnClickListener {
            findNavController().navigate(R.id.actionToCart)
        }
        return mViewBinding.root
    }

    private fun getAllProducts() {
        productRepository.getAllProducts().observe(requireActivity()) {
            mViewBinding.cartItemCount.text=it.sumOf { it.quantity!! }.toString()
            if(mainViewModel.productListResponse.value!=null && !mainViewModel.productListResponse.value!!.products!!.isNullOrEmpty()) {
                for (item in mainViewModel.productListResponse.value!!.products!!) {
                    item.product_quantity=0
                    for (cart in it) {
                        if (item.product_id.equals(cart.product_id))
                            item.product_quantity = cart.quantity
                    }
                }
                mViewBinding.rvCategory.adapter?.notifyDataSetChanged()
            }
        }
    }
    private fun getMyFeedback() {
        mainViewModel.getProductList().observe(requireActivity()) {
            it.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        mainViewModel.productListResponse.value=response!!.data
                        getAllProducts()
                        mViewBinding.progressBar.visibility = View.GONE
                        if (!response.data?.products.isNullOrEmpty()) {
                            mViewBinding.rvCategory.visibility = View.VISIBLE
                            mViewBinding.tvNoServiceFound.visibility = View.GONE
                            mViewBinding.rvCategory.apply {
                                layoutManager =
                                    StaggeredGridLayoutManager(
                                        2,
                                        StaggeredGridLayoutManager.VERTICAL
                                    )
                                adapter = ProductAdapter(response.data?.products!!,
                                    object : IProductListener {
                                        override fun onProductClicked(product: Product) {
                                            val bundle = Bundle()
                                            bundle.putSerializable(
                                                "productData",
                                                product
                                            )
                                            findNavController().navigate(
                                                R.id.action_categoryFragment_to_employeesFragment,
                                                bundle
                                            )
                                        }

                                        override fun onAddClicked(product: Product) {
                                            productRepository.insertOrUpdate(ProductRoom(product.id,product.description,product.href,product.image,product.name,product.price,product.product_id,product.quantity,product.sku,product.special,product.thumb,product.zoom_thumb),true)
                                        }

                                        override fun onRemoveClicked(product: Product) {
                                            productRepository.insertOrUpdate(ProductRoom(product.id,product.description,product.href,product.image,product.name,product.price,product.product_id,product.quantity,product.sku,product.special,product.thumb,product.zoom_thumb),false)
                                        }
                                    })
                            }
                        } else {
                            mViewBinding.rvCategory.visibility = View.GONE
                            mViewBinding.tvNoServiceFound.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        mViewBinding.rvCategory.visibility = View.VISIBLE
                        mViewBinding.progressBar.visibility = View.GONE
                        showToast(it.message.toString(), false)
                    }
                    Status.LOADING -> {
                        mViewBinding.progressBar.visibility = View.VISIBLE
                    }

                }
            }
        }

    }
}