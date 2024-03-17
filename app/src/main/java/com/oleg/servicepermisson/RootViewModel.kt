package com.oleg.servicepermisson

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch

class RootViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage as LiveData<String>

    fun checkPermission(permissionName: String) {
        val requester = PermissionRequester.instance()

        viewModelScope.launch {
            requester.request(permissionName).collect {
                when (it) {
                    is PermissionResult.Granted -> _errorMessage.value = "Permission granted"
                    is PermissionResult.Denied -> _errorMessage.value = "Permission denied"
                    is PermissionResult.Cancelled -> _errorMessage.value = "request cancelled"
                    // op canceled, repeat the request

                }
            }
        }
    }
}