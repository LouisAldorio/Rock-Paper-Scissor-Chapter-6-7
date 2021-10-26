package com.catnip.rockpaperscissorchapter6and7.utils

object MapEx {

    fun <K, V> getKey(map: Map<K, V>, target: V): K? {
        for ((key, value) in map)
        {
            if (target == value) {
                return key
            }
        }
        return null
    }
}