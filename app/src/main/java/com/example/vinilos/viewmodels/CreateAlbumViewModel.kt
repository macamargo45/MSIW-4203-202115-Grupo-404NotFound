package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.Musician
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.MusiciansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withContext


class CreateAlbumViewModel (application: Application) : AndroidViewModel(application){

    private val albumsRepository = AlbumRepository(application)
    var albumName: MutableLiveData<String> = MutableLiveData<String>()

    private var _album = MutableLiveData<Album>()
    private var _albumId = MutableLiveData<Int>()

    val album: MutableLiveData<Album>
        get() {
            if (_album == null) {
                _album = MutableLiveData<Album>()
            }
            return _album
        }

    val albumId: MutableLiveData<Int>
        get() {
            return _albumId
        }

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        //refreshDataFromNetwork()

    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                try {


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

    fun addAlbum(newAlbum: Album) {

        try {
            viewModelScope.launch (Dispatchers.Default) {

                try {
                    withContext(Dispatchers.IO) {
                        val data = albumsRepository.createAlbum(newAlbum)
                        _albumId.postValue(data)
                    }
                    _eventNetworkError.postValue(false)
                    _isNetworkErrorShown.postValue(false)
                } catch (e: Exception) {
                    _eventNetworkError.postValue(true)
                    _isNetworkErrorShown.postValue(true)
                }
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}