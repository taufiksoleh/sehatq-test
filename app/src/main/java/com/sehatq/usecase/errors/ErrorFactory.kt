package com.sehatq.usecase.errors

import com.sehatq.data.error.Error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
