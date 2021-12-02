package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.Musician
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.MusiciansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val albumRepository = AlbumRepository(application)
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val musiciansRepository = MusiciansRepository(application)
    private val _musicians = MutableLiveData<List<Musician>>()
    val musicians: LiveData<List<Musician>>
        get() = _musicians

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
                        val musicians = musiciansRepository.refreshData()
                        _musicians.postValue(musicians)

                        val albums = albumRepository.refreshData()
                        _albums.postValue(albums)
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
            if (modelClass.isAssignableFrom(CollectorDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorDetailsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}