package petros.efthymiou.groovy.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistDetailsViewModel @Inject constructor(
    private val service: PlaylistDetailService
): ViewModel() {

    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()

    val loaderDetails = MutableLiveData<Boolean>(true)

    fun getPlaylistDetails(id: String) {
        loaderDetails.value = true
        viewModelScope.launch {
            loaderDetails.postValue(true)
            service.fetchPlaylistDetails(id)
                .onEach {
                    loaderDetails.postValue(false)
                }
                .collect {
                    playlistDetails.postValue(it)
                }
        }
    }

}
