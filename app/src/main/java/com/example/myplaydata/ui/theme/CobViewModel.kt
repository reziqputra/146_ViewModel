package com.example.myplaydata.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myplaydata.data.DataForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CobViewModel : ViewModel() {
    var namaUsr: String by mutableStateOf("")
        private set
    var alamatUsr: String by mutableStateOf("")
        private set
    var noTlp: String by mutableStateOf("")
        private set
    var namaEml: String by mutableStateOf("")
        private set
    var jenisKl: String by mutableStateOf("")
        private set
    var stattST: String by mutableStateOf("")
        private set
    private val _uiState = MutableStateFlow(DataForm())
    val uiState: StateFlow<DataForm> = _uiState.asStateFlow()

    fun insertData(nm: String, tlp: String,alm: String ,eml: String, jk: String, st: String){
        namaUsr = nm;
        alamatUsr = alm;
        noTlp = tlp;
        namaEml = eml;
        jenisKl = jk;
        stattST = st;
    }
    fun setJenis(pilihJK: String){
        _uiState.update { currentState -> currentState.copy(sex = pilihJK) }
    }

    fun setStatus(pilihStatus: String){
        _uiState.update { currentState -> currentState.copy(stat = pilihStatus) }
    }
}