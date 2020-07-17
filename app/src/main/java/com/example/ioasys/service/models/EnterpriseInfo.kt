package com.example.ioasys.service.models

import com.example.ioasys.service.models.EnterpriseType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EnterpriseInfo(

    @SerializedName("id")
    val id: Int,
    @SerializedName("enterprise_name")
    val enterpriseName: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("enterprise_type")
    val enterpriseType: EnterpriseType
): Serializable