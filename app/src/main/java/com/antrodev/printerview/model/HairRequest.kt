package com.antrodev.printerview.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HairRequest(
    @Expose
    @SerializedName("b64_img")
    var image: String
)