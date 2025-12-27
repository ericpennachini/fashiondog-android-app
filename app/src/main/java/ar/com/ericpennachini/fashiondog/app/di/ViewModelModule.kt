package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.ui.screen.customer.CustomerViewModel
import ar.com.ericpennachini.fashiondog.app.ui.screen.customerlist.CustomerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CustomerViewModel(
            repository = get()
        )
    }

    viewModel {
        CustomerListViewModel(
            repository = get()
        )
    }
}
