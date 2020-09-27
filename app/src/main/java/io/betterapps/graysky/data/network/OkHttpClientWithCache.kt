package io.betterapps.graysky.data.network

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException
import java.io.File

class OkHttpClientWithCache {

    companion object {
        fun instance(context: Context): OkHttpClient {
            val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
            val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB

            val cache = Cache(httpCacheDirectory, cacheSize)
            val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(CacheDateLimitInterceptor(30))
                .cache(cache)
                .build()

            return okHttpClient
        }
    }
}

internal class CacheDateLimitInterceptor(val maxMinutes: Int) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())

        /*
        val cacheControl = CacheControl.Builder()
            .maxAge(maxMinutes, TimeUnit.MINUTES)
            .build()
        // RFC 7234: max-age or max-stale
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
        */
        val maxAge = maxMinutes * 60
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=${maxAge}")
            .build()
    }
}
