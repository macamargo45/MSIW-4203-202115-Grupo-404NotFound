package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Collector
import com.example.vinilos.repositories.CollectorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorsListViewModel(application: Application) : AndroidViewModel(application) {
    private val collectorRepository = CollectorsRepository(application)

    private val _collectors = MutableLiveData<List<Collector>>()

    val collectors: LiveData<List<Collector>>
        get() = _collectors

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
       try {
           viewModelScope.launch (Dispatchers.Default){
               try {
                   withContext(Dispatchers.IO) {
                       val data = collectorRepository.refreshData()
                       _collectors.postValue(data)
                   }
                   _eventNetworkError.postValue(false)
                   _isNetworkErrorShown.postValue(false)

               } catch (e: Exception) {
                   _eventNetworkError.postValue(true)
                   _isNetworkErrorShown.postValue(true)
               }
           }
       }catch (e:Exception){

           _eventNetworkError.value = true
       }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorsListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorsListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}