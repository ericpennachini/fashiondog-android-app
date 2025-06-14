package ar.com.ericpennachini.fashiondog.app.ui.screen.customerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.CUSTOMER_ID_KEY
import ar.com.ericpennachini.fashiondog.app.R
import ar.com.ericpennachini.fashiondog.app.ui.component.ScreenTopBar
import ar.com.ericpennachini.fashiondog.app.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class CustomerListFragment : Fragment() {

    private val viewModel: CustomerListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getAllCustomers()
        return ComposeView(requireContext()).apply {
            setContent {
                val customers = viewModel.customerList.value
                BaseAppTheme(
                    isLoading = viewModel.isLoading.value
                ) {
                    Scaffold(
                        topBar = {
                            ScreenTopBar(
                                text = "Lista de clientes",
                                onBackButtonClick = { findNavController().popBackStack() },
                                showRightAction = false
                            )
                        },
                        floatingActionButton = {
                            ExtendedFloatingActionButton(
                                text = {
                                    Text(
                                        text = "Nuevo cliente"
                                    )
                                },
                                onClick = {
                                    findNavController().navigate(R.id.fromCustomerListFragmentToCustomerFragment)
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "New customer"
                                    )
                                },
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = it.calculateTopPadding())
                        ) {
                            ConstraintLayout(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val (list, emptyState, fab) = createRefs()
                                if (customers.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .constrainAs(list) {
                                                top.linkTo(parent.top)
                                                start.linkTo(parent.start)
                                                end.linkTo(parent.end)
                                                bottom.linkTo(parent.bottom)
                                            }
                                            .fillMaxHeight()
                                    ) {
                                        itemsIndexed(customers) { index, item ->
                                            Surface(modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    val selectedCustomer = customers[index]
                                                    findNavController().navigate(
                                                        resId = R.id.fromCustomerListFragmentToCustomerFragment,
                                                        Bundle().apply {
                                                            putLong(
                                                                CUSTOMER_ID_KEY,
                                                                selectedCustomer.id
                                                            )
                                                        }
                                                    )
                                                }
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(16.dp)
                                                ) {
                                                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                                                        val (text, icon) = createRefs()
                                                        createHorizontalChain(
                                                            text,
                                                            icon,
                                                            chainStyle = ChainStyle.SpreadInside
                                                        )
                                                        Text(
                                                            text = "${item.firstName} ${item.lastName}",
                                                            modifier = Modifier.constrainAs(text) {
                                                                start.linkTo(parent.start)
                                                                top.linkTo(parent.top)
                                                                bottom.linkTo(parent.bottom)
                                                                end.linkTo(icon.start)
                                                            }
                                                        )
                                                        Icon(
                                                            imageVector = Icons.Default.ChevronRight,
                                                            contentDescription = "Chevron right",
                                                            modifier = Modifier.constrainAs(icon) {
                                                                end.linkTo(parent.end)
                                                                top.linkTo(parent.top)
                                                                bottom.linkTo(parent.bottom)
                                                                start.linkTo(text.end)
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Text(
                                        text = "(vacío)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.constrainAs(emptyState) {
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        },
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
