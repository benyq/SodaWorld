package com.benyq.sodaworld.music.netease

import com.benyq.sodaworld.music.netease.model.NeteasePlayListTrack
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author benyq
 * @date 10/16/2023
 *
 */
interface NeteaseMusicApi {

    @GET("playlist/track/all")
    suspend fun playListTrack(@Query("id") id: String, @Query("limit") limit: Int, @Query("offset") offset: Int = 0): NeteasePlayListTrack

}