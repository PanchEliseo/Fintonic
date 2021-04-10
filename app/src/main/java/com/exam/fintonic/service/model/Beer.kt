package com.exam.fintonic.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Beer(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("first_brewed")
    val firstBrewed: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("abv")
    val abv: Float? = null,

    @SerializedName("ibu")
    val ibu: Float? = null,

    @SerializedName("target_fg")
    val targetFg: Float? = null,

    @SerializedName("target_og")
    val targetOg: Float? = null,

    @SerializedName("ebc")
    val ebc: Float? = null,

    @SerializedName("srm")
    val srm: Float? = null,

    @SerializedName("ph")
    val ph: Float? = null,

    @SerializedName("attenuation_level")
    val attenuationLevel: Float? = null,

    @SerializedName("volume")
    val volume: Volume? = null,

    @SerializedName("boil_volume")
    val boilVolume: BoilVolume? = null,

    @SerializedName("method")
    @Expose
    val method: Method? = null,

    @SerializedName("ingredients")
    val ingredients: Ingredients? = null,

    @SerializedName("food_pairing")
    val foodPairing: List<String>? = null,

    @SerializedName("brewers_tips")
    val brewersTips: String? = null,

    @SerializedName("contributed_by")
    val contributedBy: String? = null

) {
    var foodDataBase: String = ""
}