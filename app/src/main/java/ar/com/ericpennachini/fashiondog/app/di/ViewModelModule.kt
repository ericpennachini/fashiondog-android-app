package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.ui.screen.customer.CustomerViewModel
import ar.com.ericpennachini.fashiondog.app.ui.screen.customerlist.CustomerListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CustomerViewModel)
    viewModelOf(::CustomerListViewModel)
}
