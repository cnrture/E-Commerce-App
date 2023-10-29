package com.canerture.ecommercecm.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canerture.ecommercecm.MR
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.theme.Gray
import com.canerture.ecommercecm.ui.components.ECEmptyScreen
import com.canerture.ecommercecm.ui.components.ECErrorScreen
import com.canerture.ecommercecm.ui.components.ECProgressBar
import com.canerture.ecommercecm.ui.components.ECScaffold
import com.canerture.ecommercecm.ui.components.ECSearchBar
import com.canerture.ecommercecm.ui.components.ECToolbar
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun HomeRoute(
    navigateToDetail: (Int) -> Unit
) {
    val viewModel = getViewModel(
        key = "home-screen",
        factory = viewModelFactory { HomeViewModel() }
    )
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onEventChanged = viewModel::onEvent,
        onProductClick = navigateToDetail
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onEventChanged: (HomeEvent) -> Unit = {},
    onProductClick: (Int) -> Unit
) {
    ECScaffold(
        topBar = {
            ECToolbar(title = stringResource(MR.strings.all_products))
        },
        content = {
            var searchQuery by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                ECSearchBar(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    value = searchQuery,
                    onValueChanged = { query ->
                        searchQuery = query
                        onEventChanged(HomeEvent.SearchProduct(query))
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                when (state) {
                    HomeState.Loading -> ECProgressBar()

                    is HomeState.AllProductsContent -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(180.dp),
                            contentPadding = PaddingValues(12.dp)
                        ) {
                            items(state.allProducts) { product ->
                                ProductItem(
                                    modifier = Modifier
                                        .padding(12.dp),
                                    product = product,
                                    onProductClick = onProductClick
                                )
                            }
                        }
                    }

                    is HomeState.Error -> {
                        ECErrorScreen(
                            desc = state.throwable.message ?: "Unknown Error",
                            buttonText = "Try Again",
                            onButtonClick = {
                                // TODO
                            }
                        )
                    }

                    is HomeState.EmptyScreen -> {
                        ECEmptyScreen(
                            text = state.message
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: ProductUI,
    onProductClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onProductClick(product.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = Gray
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                text = product.title,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 4.dp),
                text = product.price.toString(),
                textDecoration = TextDecoration.LineThrough
            )
            if (product.saleState) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp),
                    text = product.salePrice.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Red
                )
            }
        }
    }
}
