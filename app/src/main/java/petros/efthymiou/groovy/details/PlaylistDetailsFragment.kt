package petros.efthymiou.groovy.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import kotlinx.android.synthetic.main.fragment_playlist_detail.playlist_name
import kotlinx.android.synthetic.main.playlist_item.*
import petros.efthymiou.groovy.R
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    val args: PlaylistDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id = args.playlistId

        setupViewModel()

        viewModel.getPlaylistDetails(id)

        observePlaylistDetail()
        observerLoader()

        return view
    }

    private fun observePlaylistDetail() {
        viewModel.playlistDetails.observe(viewLifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUIDetails(playlistDetails)
            } else {
                Snackbar.make(
                    playlist_details_root,
                    R.string.generic_error_message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupUIDetails(playlistDetails: Result<PlaylistDetails>) {
        playlist_name.text = playlistDetails.getOrNull()!!.name
        playlist_details.text = playlistDetails.getOrNull()!!.details
    }

    private fun observerLoader() {
        viewModel.loaderDetails.observe(viewLifecycleOwner) { loading ->
            when(loading) {
                true -> loader_details.visibility = View.VISIBLE
                else -> loader_details.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PlaylistDetailsViewModel::class.java)
    }

    companion object {
        fun newInstance() =
            PlaylistDetailsFragment()
    }
}