package com.example.dicodingapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingapp.R
import com.example.dicodingapp.data.local.room.FavoriteDatabase
import com.example.dicodingapp.data.local.room.FavoriteRepository
import com.example.dicodingapp.data.remote.retrofit.ApiConfig
import com.example.dicodingapp.databinding.FragmentFavoriteBinding
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        val database = FavoriteDatabase.getDatabase(requireContext())
        ApiConfig.getApiService()
        val repository = FavoriteRepository(database.favoriteDao())
        FavoriteViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteAdapter = FavoriteAdapter(emptyList()) { event ->
            val bundle = Bundle().apply {
                putSerializable("event", event)
            }
            findNavController().navigate(R.id.action_to_detailFragment, bundle)
        }

        binding.rvFavoriteEvents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        binding.progressBar.visibility = View.VISIBLE

        favoriteViewModel.favoriteEvents.observe(viewLifecycleOwner) { favoriteEvents ->
            binding.progressBar.visibility =
                View.GONE

            if (favoriteEvents.isEmpty()) {
                binding.rvFavoriteEvents.visibility = View.GONE
                binding.tvEmptyFavorites.visibility = View.VISIBLE
            } else {
                binding.rvFavoriteEvents.visibility = View.VISIBLE
                binding.tvEmptyFavorites.visibility = View.GONE

                eventAdapter.updateEvents(favoriteEvents.map {
                    ListEventItem(
                        id = it.id,
                        name = it.name,
                        beginTime = it.beginTime ?: "",
                        ownerName = it.ownerName ?: "",
                        imageLogo = it.imageLogo ?: "",
                        summary = it.summary ?: "",
                        description = it.description ?:"",
                        mediaCover = it.mediaCover ?:"",
                        category = it.category ?: "",
                        cityName = it.cityName ?:"",
                        quota = it.quota,
                        registrants = it.registrants,
                        endTime = it.endTime ?:"",
                        link = it.link ?:""
                    )
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}