package ar.com.ericpennachini.fashiondog.app.ui.screen.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.mapper.CustomerDTOMapper
import ar.com.ericpennachini.fashiondog.app.data.repository.FashionDogRepository
import ar.com.ericpennachini.fashiondog.app.domain.mapper.CustomerMapper
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone
import ar.com.ericpennachini.fashiondog.app.ui.theme.FashionDogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repo = FashionDogRepository(
            dataSource = RoomDataSource(
                context = requireContext(),
                mapper = CustomerDTOMapper
            ),
            mapper = CustomerMapper
        )

        CoroutineScope(Dispatchers.IO).launch {
            repo.addCustomer(
                Customer(
                    firstName = "Juan",
                    lastName = "Perez",
                    pets = listOf(),
                    phones = listOf(
                        Phone(number = "123456789", type = "personal")
                    ),
                    address = Address(
                        number = 1234,
                        street = "OHiggins",
                        description = "Entre las garzas y la picada",
                        city = "Paraná",
                        province = "Entre Rios",
                        country = "Argentina"
                    ),
                    isFromNeighborhood = true,
                    description = "Cliente infumable",
                    email = "cliente.infu@gmail.com"
                )
            )
        }

        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }

}