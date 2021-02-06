package com.barney.gnbapp.features.navigation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barney.gnbapp.R
import com.barney.gnbapp.base.getGNBAppApplication
import com.barney.gnbapp.dagger.DaggerViewModelFactory
import com.barney.gnbapp.data.repository.datasource.entity.ProductTransactionResponse
import com.barney.gnbapp.data.repository.entity.ProductTransaction
import com.barney.gnbapp.features.navigation.viewmodel.MainViewModel
import com.barney.gnbapp.tools.AmountRounder
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getGNBAppApplication().component.inject(this)

        viewModel = ViewModelProvider(this, daggerViewModelFactory).get(MainViewModel::class.java)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.transactions.observe(this,
            { transactions ->
                Toast.makeText(this,"Numero de transacciones: "+transactions.size, Toast.LENGTH_LONG).show()
            })

        viewModel.rates.observe(this,
            { rates ->
                //Toast.makeText(this,"Numero de rates: "+rates.size,Toast.LENGTH_LONG).show()
            })
    }

    private fun setListeners() {
        main_button.setOnClickListener {
            viewModel.loadTransactions()
            viewModel.loadRates()
            AmountRounder.roundHalfToEven(BigDecimal("12.5451")).let {
                Toast.makeText(this, "Value: $it",Toast.LENGTH_LONG).show()
            }
        }
    }
}