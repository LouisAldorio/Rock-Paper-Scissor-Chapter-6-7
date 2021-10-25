package com.catnip.rockpaperscissorchapter6and7.ui.about.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.catnip.rockpaperscissorchapter6and7.data.model.TeamMember
import com.catnip.rockpaperscissorchapter6and7.databinding.TeamMemberItemBinding

class TeamMemberRecyclerViewAdapter(private val members : List<TeamMember>) :
    RecyclerView.Adapter<TeamMemberRecyclerViewAdapter.TeamMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberViewHolder {
        val binding = TeamMemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TeamMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamMemberViewHolder, position: Int) {
        holder.bindView(members[position])
    }

    override fun getItemCount(): Int {
        return members.size
    }

    inner class TeamMemberViewHolder(private val binding: TeamMemberItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item : TeamMember) {
            binding.tvTeamName.text = item.name
            binding.tvTeamRole.text = item.role
            Glide.with(binding.root.context).asBitmap().load(item.photoURL).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.ivTeamImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
        }
    }
}

