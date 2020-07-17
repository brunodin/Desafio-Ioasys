package com.example.ioasys.service.models

import com.example.ioasys.service.models.EnterpriseInfo
import com.google.gson.annotations.SerializedName

data class EnterpriseList(

    @SerializedName ("enterprises")
    val empresas: List<EnterpriseInfo>
)