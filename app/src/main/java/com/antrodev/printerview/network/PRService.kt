package com.antrodev.printerview.network

import com.antrodev.printerview.model.HairRequest
import com.antrodev.printerview.model.HairResponse
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface PRService {

    @POST("/upload")
    fun performHairRequest(@Body searchRequest: HairRequest): Observable<HairResponse>

}