package com.yusril.consumerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.yusril.consumerapp.BuildConfig
import com.yusril.consumerapp.model.User
import com.yusril.consumerapp.viewModel.MainViewModel.Companion.AUTORIZATION
import com.yusril.consumerapp.viewModel.MainViewModel.Companion.REQUEST
import com.yusril.consumerapp.viewModel.MainViewModel.Companion.TOKEN
import com.yusril.consumerapp.viewModel.MainViewModel.Companion.URL_SEARCH
import com.yusril.consumerapp.viewModel.MainViewModel.Companion.URL_USER
import com.yusril.consumerapp.viewModel.MainViewModel.Companion.USERAGENT
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel: ViewModel() {
    companion object {
        private const val TOKEN = BuildConfig.TOKEN
        private const val URL_USER=BuildConfig.URL_USER
        private const val URL_SEARCH=BuildConfig.URL_SEARCH
        private const val AUTORIZATION=BuildConfig.AUTORIZATION
        private const val USERAGENT=BuildConfig.USERAGENT
        private const val REQUEST=BuildConfig.REQUEST
    }
    val listUsers = MutableLiveData<ArrayList<User>>()
    fun setUser(){
        val listItems = ArrayList<User>()
        val client= AsyncHttpClient()
        client.addHeader(AUTORIZATION, TOKEN)
        client.addHeader(USERAGENT, REQUEST)
        client.get(URL_USER, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            ) {
                Log.d("setUser", "Success Conect")
                try {
                    val result = String(responseBody)
                    Log.d("result", result)
                    val resultList = JSONArray(result)
                    Log.d("resultList", resultList.toString())

                    for (i in 0 until resultList.length()) {
                        val userItem = resultList.getJSONObject(i)
                        val username = userItem.getString("login")
                        val avatar = userItem.getString("avatar_url")

                        val users = User(username, avatar)
                        listItems.add(users)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("catch", e.message.toString())
                }
            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
            ) {
                Log.d("onFailure", "Fail Conect")
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("Exception", errorMessage)
            }

        })
    }
    fun searchUser(username: String){
        val listItems = ArrayList<User>()
        val client= AsyncHttpClient()
        client.addHeader(AUTORIZATION, TOKEN)
        client.addHeader(USERAGENT, REQUEST)
        client.get(URL_SEARCH+username, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
            ) {
                Log.d("searchtUser", "Success Conect")
                try {
                    val result = String(responseBody)
                    Log.d("searchresult", result)
                    val resultObject = JSONObject(result)
                    Log.d("searchresultObject", resultObject.toString())
                    val items = resultObject.getJSONArray("items")
                    Log.d("searchritems", items.toString())
                    for (i in 0 until items.length()) {
                        val userItem = items.getJSONObject(i)
                        val userName = userItem.getString("login")
                        val avatar = userItem.getString("avatar_url")

                        val users = User(userName, avatar)
                        listItems.add(users)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("catch", e.message.toString())
                }
            }

            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
            ) {
                Log.d("onFailuresearcg", "onFailure Conect")
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("Exception", errorMessage)
            }
        })
    }
    fun getUser(): LiveData<ArrayList<User>> {
        return listUsers
    }
}