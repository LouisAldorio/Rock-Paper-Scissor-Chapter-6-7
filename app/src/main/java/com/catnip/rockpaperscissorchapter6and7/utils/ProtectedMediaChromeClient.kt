package com.catnip.rockpaperscissorchapter6and7.utils

import android.webkit.PermissionRequest
import android.webkit.WebChromeClient







class ProtectedMediaChromeClient : WebChromeClient() {



    override fun onPermissionRequest(request: PermissionRequest?) {

        val resources = request?.resources ?: arrayOf<String>()
        for (i in resources.indices) {
            if(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID == resources[i]){
                if (request != null) {
                    request.grant(resources)
                }
                return
            }
        }

        super.onPermissionRequest(request)
    }
}