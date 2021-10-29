package com.catnip.rockpaperscissorchapter6and7.ui.game.history.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistoryWithPlayer
import com.catnip.rockpaperscissorchapter6and7.databinding.CardWinnerItemBinding
import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult
import com.catnip.rockpaperscissorchapter6and7.utils.GameUtil

class GameHistoryAdapter : RecyclerView.Adapter<GameHistoryAdapter.GameHistoryHolder>() {

    private var items: MutableList<GameHistoryWithPlayer> = mutableListOf()


    fun setItems(items: List<GameHistoryWithPlayer>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<GameHistoryWithPlayer>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GameHistoryHolder {
        val binding = CardWinnerItemBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return GameHistoryHolder(binding)
    }

    override fun onBindViewHolder(p0: GameHistoryHolder, p1: Int) {
        p0.bindView(items[p1])
    }

    override fun getItemCount(): Int = items.size


    class GameHistoryHolder(private val binding: CardWinnerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: GameHistoryWithPlayer) {
            when {
                GameResult.get(
                    GameUtil.getGameResult(
                        item.gameHistory.player1Hero,
                        item.gameHistory.player2Hero
                    )
                ).toString() == GameResult.DRAW.stringValue ->
                    binding.tvWinnerPlayerName.text = "DRAW"
                item.player1.name == UserPreference(binding.root.context).player?.name -> {
                    binding.tvWinnerPlayerName.text = binding.root.context.getString(
                        R.string.text_format_player_name_win,
                        item.player1.name.uppercase(),
                        GameResult.get(
                            GameUtil.getGameResult(
                                item.gameHistory.player1Hero,
                                item.gameHistory.player2Hero
                            )
                        ).toString()
                    )
                }
                else -> {
                    binding.tvWinnerPlayerName.text = binding.root.context.getString(
                        R.string.text_format_player_name_win,
                        item.player2.name.uppercase(),
                        GameResult.get(
                            GameUtil.getGameResult(
                                item.gameHistory.player2Hero,
                                item.gameHistory.player1Hero
                            )
                        ).toString()
                    )
                }
            }

            binding.tvPlayer1Name.text =
                binding.root.context.getString(R.string.text_format_player1, item.player1.name)

            binding.tvPlayer1Hero.text = binding.root.context.getString(
                R.string.text_format_power,
                HERO[item.gameHistory.player1Hero!!]
            )

            if (item.gameHistory.player2Id == null)
                binding.tvPlayer2Name.text =
                    binding.root.context.getString(R.string.text_format_player2, "ROBOT")
            else
                binding.tvPlayer2Name.text =
                    binding.root.context.getString(R.string.text_format_player2, item.player2.name)

            binding.tvPlayer2Hero.text = binding.root.context.getString(
                R.string.text_format_power,
                HERO[item.gameHistory.player2Hero!!]
            )

            binding.tvDate.text = item.gameHistory.date
        }
    }

    companion object {
        val HERO = arrayListOf("ROCK", "PAPER", "SCISSORS")

    }
}