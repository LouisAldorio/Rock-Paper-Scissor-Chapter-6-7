package com.catnip.rockpaperscissorchapter6and7.enumeration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameHistoryType: Parcelable {
    REMOTE_HISTORY,
    LOCAL_HISTORY
}