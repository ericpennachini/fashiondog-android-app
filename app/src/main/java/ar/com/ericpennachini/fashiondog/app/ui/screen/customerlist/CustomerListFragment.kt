package ar.com.ericpennachini.fashiondog.app.ui.screen.customerlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
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
@ExperimentalMaterial3Api
internal class CustomerListFragment : Fragment() {

    private val viewModel: CustomerListViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getAllCustomers()
        return ComposeView(requireContext()).apply {
            setContent {
                val customers = viewModel.customerList.value
                val scrollState = rememberScrollState()
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
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                val (list, emptyState, fab) = createRefs()
                                if (customers.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .verticalScroll(
                                                state = scrollState,
                                                enabled = true
                                            )
                                            .constrainAs(list) {
                                                top.linkTo(parent.top)
                                                start.linkTo(parent.start)
                                                end.linkTo(parent.end)
                                                bottom.linkTo(parent.bottom)
                                            }
                                    ) {
                                        itemsIndexed(customers) { index, item ->
                                            Surface(modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    val selectedCustomer = customers[index]
                                                    findNavController().navigate(
                                                        resId = R.id.fromCustomerListFragmentToCustomerFragment,
                                                        Bundle().also {
                                                            it.putLong(
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
                                ExtendedFloatingActionButton(
                                    text = { Text(text = "Nuevo cliente") },
                                    onClick = {
                                        findNavController().navigate(R.id.fromCustomerListFragmentToCustomerFragment)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "New customer"
                                        )
                                    },
                                    modifier = Modifier.constrainAs(fab) {
                                        bottom.linkTo(parent.bottom, 16.dp)
                                        end.linkTo(parent.end, 16.dp)
                                    },
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
