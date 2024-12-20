package com.example.dicodingapp.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingapp.databinding.FragmentFinishedBinding
import com.example.dicodingapp.ui.EventAdapter
import com.example.dicodingapp.ui.EventViewModel

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventViewModel by viewModels()

    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventAdapter = EventAdapter(object : EventAdapter.OnItemClickListener {
            override fun onItemClick(eventId: Int) {
                // Handle click event here, for example navigate to event details
                Toast.makeText(context, "Event ID: $eventId", Toast.LENGTH_SHORT).show()
            }
        })

        binding.rvEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }

        viewModel.events.observe(viewLifecycleOwner) { events ->
            events?.let {
                eventAdapter.submitList(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchEvents(active = 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
