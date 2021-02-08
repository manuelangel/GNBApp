package com.barney.gnbapp.features.transactions.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.barney.gnbapp.R
import com.barney.gnbapp.base.getGNBAppApplication
import com.barney.gnbapp.dagger.DaggerViewModelFactory
import com.barney.gnbapp.features.transactions.viewmodel.ProductTransactionsVM
import kotlinx.android.synthetic.main.activity_product_transactions.*
import javax.inject.Inject

class ProductTransactionsActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_CODE_BUNDLE_KEY = "PRODUCT_CODE_BUNDLE_KEY"
    }

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: ProductTransactionsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_transactions)

        getGNBAppApplication().component.inject(this)

        viewModel =
            ViewModelProvider(this, daggerViewModelFactory).get(ProductTransactionsVM::class.java)

        setObservers()
        loadBundleData()
    }

    private fun setObservers() {
        viewModel.transactionsLiveData.observe(this, { transactions ->
            Toast.makeText(this,"Transaction count: ${transactions.size}",Toast.LENGTH_LONG).show()
        })
    }

    private fun loadBundleData() {
        intent.extras?.getString(PRODUCT_CODE_BUNDLE_KEY)?.let {
            sample_text.text = it
            viewModel.loadProductTransactions(it)
        }

    }
}