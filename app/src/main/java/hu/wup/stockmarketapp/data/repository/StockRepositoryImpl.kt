package hu.wup.stockmarketapp.data.repository

import hu.wup.stockmarketapp.data.local.StockDatabase
import hu.wup.stockmarketapp.data.mapper.convertFromEntityToCompanyListing
import hu.wup.stockmarketapp.data.remote.StockApi
import hu.wup.stockmarketapp.domain.model.CompanyListing
import hu.wup.stockmarketapp.domain.repository.StockRepository
import hu.wup.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading())
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.convertFromEntityToCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
            } catch (e: IOException) {
                emit(Resource.Error("Could not access the internet ${e.localizedMessage}"))
            } catch (e: HttpException) {
                emit(Resource.Error("Could not access the internet ${e.localizedMessage}"))
            }
        }
    }
}