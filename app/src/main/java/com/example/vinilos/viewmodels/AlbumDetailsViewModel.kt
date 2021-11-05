package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album

class AlbumDetailsViewModel (application: Application) :  AndroidViewModel(application) {
    private val _album = MutableLiveData<Album>()

    val album: LiveData<Album>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        val album = Album(1,"URL","28 of march 1985",
            "A good one", "Rock", "One of those","One of many")
        //NetworkServiceAdapter.getInstance(getApplication()).getAlbums({

        //    _albums.postValue(list)
        //    _eventNetworkError.value = false
        //    _isNetworkErrorShown.value = false
        //},{
        //    _eventNetworkError.value = true
        //})
        _album.postValue(album)
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}