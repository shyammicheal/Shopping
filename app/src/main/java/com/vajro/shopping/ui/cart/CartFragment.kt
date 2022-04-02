package com.vajro.shopping.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vajro.shopping.databinding.FragmentCartBinding
import com.vajro.shopping.ui.main.MainViewModel
import com.vajro.shopping.service.network.ViewModelFactory
import com.mindorks.retrofit.coroutines.data.api.ApiHelper
import com.mindorks.retrofit.coroutines.data.api.RetrofitBuilder
import com.vajro.shopping.model.Product
import com.vajro.shopping.room.ProductRepository
import com.vajro.shopping.room.ProductRoom
import com.vajro.shopping.ui.product.IProductListener

class CartFragment : Fragment() {
    lateinit var mViewBinding: FragmentCartBinding
    lateinit var mainViewModel: MainViewModel

    private var productRepository: ProductRepository? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
                MainViewModel::class.java
            )
        mViewBinding = FragmentCartBinding.inflate(inflater)
        productRepository= ProductRepository(requireContext())
        mViewBinding.llBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        productRepository?.getAllProducts()!!.observe(requireActivity()) {
            if(!it.isNullOrEmpty()){
                mViewBinding.tvTotalCartCount.text = it.sumOf { it.quantity!! }.toString()
                mViewBinding.rvCartList.visibility = View.VISIBLE
                mViewBinding.cartView.visibility = View.VISIBLE
                mViewBinding.imgEmptyCart.visibility = View.GONE
                val finalList: MutableList<ProductRoom> = mutableListOf()
                val list=it.groupBy { it.product_id }
                var totalPrice=0.0
                for (item in list){
                    finalList.add(item.value.get(0).also { it.quantity=item.value.sumOf {it.quantity!!}})
                    totalPrice+=item.value.get(0).price!!.drop(1).replace(",", "").toDouble().times(item.value.sumOf {it.quantity!!})
                }

                mViewBinding.totalAmount.text="₹"+totalPrice.toString()

                mViewBinding.rvCartList.apply {
                    adapter = CartAdapter(finalList,object :ICartProductListener{
                        override fun onAddClicked(product: ProductRoom) {
                            productRepository?.insertOrUpdate(product, true)
                        }

                        override fun onRemoveClicked(product: ProductRoom) {
                            productRepository?.insertOrUpdate(product,false)
                        }
                    })
                }

            }
            else{
                mViewBinding.cartView.visibility = View.GONE
                mViewBinding.rvCartList.visibility = View.GONE
                mViewBinding.imgEmptyCart.visibility = View.VISIBLE
            }
        }
        return mViewBinding.root
    }

}
