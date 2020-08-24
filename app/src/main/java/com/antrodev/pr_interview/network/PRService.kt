package com.antrodev.pr_interview.network

import com.antrodev.pr_interview.model.HairRequest
import com.antrodev.pr_interview.model.HairResponse
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

public interface PRService {

    @POST("/upload")
    fun performHairRequest(@Body searchRequest: HairRequest): Observable<HairResponse>

}