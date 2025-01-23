package com.lms.lmsassignment.view.child_tabs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lms.lmsassignment.R
import com.lms.lmsassignment.data.model.RecentVideos
import com.lms.lmsassignment.databinding.ItemVideoBinding
import java.text.SimpleDateFormat
import java.util.Locale

class VideoAdapter : ListAdapter<RecentVideos, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: RecentVideos) {
            // Extract title from PlaybackUrl
            val titleRegex = "title=\"(.*?)\"".toRegex()
            val titleMatch = titleRegex.find(video.PlaybackUrl)
            val title = titleMatch?.groups?.get(1)?.value ?: "No Title"

            // Format FixDate
            val formattedDate = video.FixDate.takeIf { it.isNotBlank() }?.let {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                inputFormat.parse(it)?.let { date -> outputFormat.format(date) } ?: "N/A"
            } ?: "N/A"

            // Bind data to views
            binding.apply {
                tvTitle.text = title
                tvDateHighlights.text = formattedDate
                ivShareIcon.setOnClickListener {
                    // Handle share logic if needed
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class VideoDiffCallback : DiffUtil.ItemCallback<RecentVideos>() {
    override fun areItemsTheSame(oldItem: RecentVideos, newItem: RecentVideos): Boolean {
        return oldItem.TeamFixture == newItem.TeamFixture
    }

    override fun areContentsTheSame(oldItem: RecentVideos, newItem: RecentVideos): Boolean {
        return oldItem == newItem
    }
}
