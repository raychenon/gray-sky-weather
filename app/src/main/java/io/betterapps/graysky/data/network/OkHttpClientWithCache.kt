package io.betterapps.graysky.data.network

import android.content.Context
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException
import java.io.File
import java.util.concurrent.TimeUnit

class OkHttpClientWithCache {

    companion object {
        fun instance(context: Context): OkHttpClient {
            val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
            val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB

            val cache = Cache(httpCacheDirectory, cacheSize)
            val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(provideCacheInterceptor(30))
                .cache(cache)
                .build()

            return okHttpClient
        }

        private fun provideCacheInterceptor(maxMinutes: Int): Interceptor {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val response: Response = chain.proceed(chain.request())
                    val cacheControl = CacheControl.Builder()
                        .maxAge(maxMinutes, TimeUnit.MINUTES)
                        .build()

                    // RFC 7234: max-age or max-stale
                    return response.newBuilder()
                        .header("max-age", cacheControl.toString())
                        .build()
                }
            }
        }
    }


}

