package com.catnip.rockpaperscissorchapter6and7.ui.game.history.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.GameHistoryData
import com.catnip.rockpaperscissorchapter6and7.databinding.CardWinnerItemBinding

import com.catnip.rockpaperscissorchapter6and7.utils.DateConverter
import java.text.SimpleDateFormat

class RemoteGameHistoryAdapter :
    RecyclerView.Adapter<RemoteGameHistoryAdapter.RemoteGameHistoryHolder>() {

    private var items: MutableList<GameHistoryData> = mutableListOf()


    fun setItems(items: List<GameHistoryData>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<GameHistoryData>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RemoteGameHistoryHolder {
        val binding = CardWinnerItemBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return RemoteGameHistoryHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    class RemoteGameHistoryHolder(private val binding: CardWinnerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: GameHistoryData) {
            binding.tvWinnerPlayerName.text = item.result
            binding.tvPlayer1Name.text = "Mode: ${item.mode}"
            binding.tvPlayer1Hero.text = "Akhir Permainan: ${item.message}"
            binding.tvPlayer2Hero.visibility = View.INVISIBLE
            binding.tvPlayer2Name.visibility = View.INVISIBLE
            binding.divider.visibility = View.INVISIBLE
            binding.tvDate.text = DateConverter.convert(item.createdAt)
        }
    }

    override fun onBindViewHolder(holder: RemoteGameHistoryHolder, position: Int) {
        holder.bindView(items[position])
    }
}