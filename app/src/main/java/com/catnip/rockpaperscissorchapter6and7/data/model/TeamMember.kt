package com.catnip.rockpaperscissorchapter6and7.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamMember(
    var name : String,
    var role : String,
    var photoURL : String
) : Parcelable
