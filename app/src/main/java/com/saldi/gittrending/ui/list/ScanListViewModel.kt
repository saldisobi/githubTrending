package com.saldi.gittrending.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saldi.gittrending.data.model.ApiResponse
import com.saldi.gittrending.data.model.ScanParserItem
import com.saldi.gittrending.data.repository.ScanRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanListViewModel @Inject constructor(private val scanRepository: ScanRepository) :
    ViewModel() {
    private val _scanLiveData = MutableLiveData<ApiResponse<List<ScanParserItem>>>()

    val scanLiveData: LiveData<ApiResponse<List<ScanParserItem>>>
        get() = _scanLiveData

    private val _scanItem = MutableLiveData<ScanParserItem>()

    val scanItem: LiveData<ScanParserItem>
        get() = _scanItem


    init {
        getScanItems()
    }

    fun getScanItems() {
        viewModelScope.launch {
            scanRepository.getScanData().collect {

                _scanLiveData.value = it
            }
        }
    }

    fun setScanItem(scanItem: ScanParserItem) {
        _scanItem.value = scanItem
    }
}
