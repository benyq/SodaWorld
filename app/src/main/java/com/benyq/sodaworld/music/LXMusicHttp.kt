package com.benyq.sodaworld.music

import fi.iki.elonen.NanoHTTPD

/**
 *
 * @author benyq
 * @date 10/9/2023
 *
 */
class LXMusicHttp(port: Int): NanoHTTPD(port) {

    override fun serve(session: IHTTPSession): Response {
        val contentType = ContentType(session.headers["content-type"]).tryUTF8()
        session.headers["content-type"] = contentType.contentTypeHeader
        val uri = session.uri
        val returnData = try {
            when(session.method) {
                Method.GET -> {
                    when(uri) {
                        "/test" -> "我来啦"
                        else -> null
                    }
                }
                else -> null
            }
        }catch (e: Exception) {
            null
        }
        return newFixedLengthResponse(returnData)
    }
}