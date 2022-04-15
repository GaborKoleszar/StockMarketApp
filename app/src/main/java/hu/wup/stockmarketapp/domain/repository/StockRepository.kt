package hu.wup.stockmarketapp.domain.repository

import hu.wup.stockmarketapp.domain.model.CompanyListing
import hu.wup.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}