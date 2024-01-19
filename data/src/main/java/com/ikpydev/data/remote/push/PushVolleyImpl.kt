package com.ikpydev.data.remote.push

import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.ikpydev.data.cons.ContVal
import io.reactivex.rxjava3.core.Completable
import org.json.JSONObject

class PushVolleyImpl(
    private val queue: RequestQueue
) : PushVolley {
    override fun push(token: String?, title: String, body: String): Completable =
        Completable.create { emit ->

            token ?: kotlin.run {
                emit.onComplete()
                return@create
            }
            val url = ContVal.FMS_Url

            val notification = JSONObject()
            notification.put("title", title)
            notification.put("body", body)
            val message = JSONObject()
            message.put("to", token)
            message.put("notification", notification)

            val request = object : JsonObjectRequest(
                Method.POST,
                url,
                message,
                {
                    emit.onComplete()

                },
                { e ->
                    emit.onError(e)
                },
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return mutableMapOf(
                        "Content-Type" to "application/json",
                        "Authorization" to "key=${ContVal.FMS_Token}")

                }
            }
            queue.add(request)
        }
}