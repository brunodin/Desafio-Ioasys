package com.example.ioasys.service.listeners

import com.example.ioasys.service.models.EnterpriseInfo

class ValidationListeners(private val list: List<EnterpriseInfo>? = null, private val int: Int) {

    fun onValidation() = arrayListOf(list, int)
}