package com.sehatq.ui.base

import androidx.lifecycle.ViewModel
import com.sehatq.data.error.mapper.ErrorMapper
import com.sehatq.usecase.errors.ErrorManager





abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    val errorManager: ErrorManager = ErrorManager(ErrorMapper())
}
