package com.benyq.sodaworld.base.network

/**
 *
 * @author benyq
 * @date 7/17/2023
 *
 */
class ApiThrowable(val code: Int, message: String): Throwable(message)