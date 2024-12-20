package com.example.dicodingapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodingapp.data.remote.response.Event
import com.example.dicodingapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val eventId = intent.getStringExtra(EXTRA_EVENT_ID)?.toIntOrNull() ?: -1
        if (eventId == -1) {
            Toast.makeText(this, "Event ID tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupObservers()
        detailViewModel.fetchEventDetail(eventId.toString())

    }

    private fun setupObservers() {
        detailViewModel.eventDetail.observe(this) { event ->
            event?.let { populateEventDetail(it) }
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateEventDetail(event: Event) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(event.mediaCover)
                .into(imgEventBanner)

            tvEventName.text = event.name
            tvEventQuota.text = "Sisa Kuota: ${event.quota - event.registrants}"
            tvEventTime.text = "Tanggal & Waktu: ${event.beginTime} - ${event.endTime}"
            tvEventOwner.text = event.ownerName
            tvEventCity.text = "Lokasi: ${event.cityName}"
            tvEventDescription.text = HtmlCompat.fromHtml(
                event.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            tvEventCategory.text = event.summary

            buttonRegister.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}
