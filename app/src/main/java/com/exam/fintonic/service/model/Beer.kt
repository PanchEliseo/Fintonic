package com.exam.fintonic.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Beer(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("tagline")
    val tagline: String = "",

    @SerializedName("first_brewed")
    val firstBrewed: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("image_url")
    val imageUrl: String = "",

    @SerializedName("abv")
    val abv: Float = 0F,

    @SerializedName("ibu")
    val ibu: Float = 0F,

    @SerializedName("target_fg")
    val targetFg: Float = 0F,

    @SerializedName("target_og")
    val targetOg: Float = 0F,

    @SerializedName("ebc")
    val ebc: Float = 0F,

    @SerializedName("srm")
    val srm: Float = 0F,

    @SerializedName("ph")
    val ph: Float = 0F,

    @SerializedName("attenuation_level")
    val attenuationLevel: Float = 0F,

    @SerializedName("volume")
    val volume: Volume,

    @SerializedName("boil_volume")
    val boilVolume: BoilVolume,

    @SerializedName("method")
    @Expose
    val method: Method,

    @SerializedName("ingredients")
    val ingredients: Ingredients,

    @SerializedName("food_pairing")
    val foodPairing: List<String>,

    @SerializedName("brewers_tips")
    val brewersTips: String = "",

    @SerializedName("contributed_by")
    val contributedBy: String = ""

) {
    var foodDataBase: String = ""
}