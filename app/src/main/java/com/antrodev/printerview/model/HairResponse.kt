package com.antrodev.printerview.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HairResponse(
    @Expose
    @SerializedName("b64_output")
    var image: String
)
