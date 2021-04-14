package com.exam.fintonic.service.mapper

import com.exam.fintonic.service.model.ListBeers
import com.exam.fintonic.service.model.Beer

class BeerMapper: Mapper<MutableList<Beer>, ListBeers> {
    override suspend fun map(input: MutableList<Beer>): ListBeers {
        return ListBeers(input.map {
            Beer(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    firstBrewed = it.firstBrewed,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    abv = it.abv,
                    ibu = it.ibu,
                    targetFg = it.targetFg,
                    targetOg = it.targetOg,
                    ebc = it.ebc,
                    srm = it.srm,
                    ph = it.ph,
                    attenuationLevel = it.attenuationLevel,
                    volume = it.volume,
                    boilVolume = it.boilVolume,
                    method = it.method,
                    ingredients = it.ingredients,
                    foodPairing = it.foodPairing,
                    brewersTips = it.brewersTips,
                    contributedBy = it.contributedBy
            )
        }.toMutableList()
        )
    }
}