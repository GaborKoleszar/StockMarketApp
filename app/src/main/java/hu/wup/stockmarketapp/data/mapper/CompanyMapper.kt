package hu.wup.stockmarketapp.data.mapper

import hu.wup.stockmarketapp.data.local.CompanyListingEntity
import hu.wup.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.convertFromEntityToCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.convertFromCompanyListingToEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}