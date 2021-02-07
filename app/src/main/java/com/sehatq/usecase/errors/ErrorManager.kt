package com.sehatq.usecase.errors

import com.sehatq.data.error.Error
import com.sehatq.data.error.mapper.ErrorMapper
import javax.inject.Inject



class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorFactory {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
