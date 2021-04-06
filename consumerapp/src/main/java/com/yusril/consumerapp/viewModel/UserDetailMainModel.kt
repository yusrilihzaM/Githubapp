package com.yusril.consumerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.gifdecoder.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.yusril.consumerapp.BuildConfig
import com.yusril.consumerapp.model.UserDetail
import com.yusril.consumerapp.model.UserFollower
import com.yusril.consumerapp.model.UserFollowing
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

@Suppress("NAME_SHADOWING")
class UserDetailMainModel:ViewModel() {
    companion object {
        private const val TOKEN = BuildConfig.TOKEN
        private const val URL_USER_DETAIL= BuildConfig.URL_USER_DETAIL
        private const val URL_USER_DETAIL_FOLLOWER= BuildConfig.URL_USER_DETAIL_FOLLOWER
        private const val URL_USER_DETAIL_FOLLOWING= BuildConfig.URL_USER_DETAIL_FOLLOWING
        private const val AUTORIZATION= BuildConfig.AUTORIZATION
        private const val USERAGENT= BuildConfig.USERAGENT
        private const val REQUEST= BuildConfig.REQUEST
    }
    val listUser = MutableLiveData<ArrayList<UserDetail>>()
    val listUserFollower = MutableLiveData<ArrayList<UserFollower>>()
    val listUserFollowing = MutableLiveData<ArrayList<UserFollowing>>()

    fun setUserDetail(username:String){
        val listDetail = ArrayList<UserDetail>()
        val client= AsyncHttpClient()
        client.addHeader(AUTORIZATION, TOKEN)
        client.addHeader(USERAGENT, REQUEST)
        client.get(URL_USER_DETAIL+username,object :AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                Log.d("setUserDetail", "Success Conect")
                try {
                    val result = String(responseBody)
                    Log.d("result", result)
                    val resultObject=JSONObject(result)
                    Log.d("resultObject", resultObject.toString())
                    val name=resultObject.getString("name")
                    val company=resultObject.getString("company")
                    val location=resultObject.getString("location")
                    val publicRepos=resultObject.getString("public_repos")
                    val follower=resultObject.getString("followers")
                    val following=resultObject.getString("following")
                    val userDetail=UserDetail(name,company,location,publicRepos,follower,following)
                    listDetail.add(userDetail)
                    listUser.postValue(listDetail)
                }catch (e: Exception){
                    Log.d("catch", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("setUserDetail", "Failure Conect")
                val errorMessage = when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("Exception", errorMessage)
            }
        })
    }
    fun setUserFollowing(username:String?){
        val listFollowing = ArrayList<UserFollowing>()
        val client= AsyncHttpClient()
        client.addHeader(AUTORIZATION, TOKEN)
        client.addHeader(USERAGENT, REQUEST)
        client.get("$URL_USER_DETAIL_FOLLOWER$username/following",object :AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                Log.d("setUserFollowing", "Success Conect")
                try {
                    val result = String(responseBody)
                    Log.d("result", result)
                    val resultList= JSONArray(result)
                    Log.d("resultList", resultList.toString())
                    val login=resultList.getJSONObject(0).getString("login")
                    Log.d("login", login.toString())
                    for(i in 0 until resultList.length()){
                        val userItem=resultList.getJSONObject(i)
                        val username = userItem.getString("login")
                        val avatar = userItem.getString("avatar_url")
                        val userFollowing = UserFollowing(username,avatar)
                        listFollowing.add(userFollowing)
                    }
                    Log.d("listFollowing", listFollowing.toString())
                    listUserFollowing.postValue(listFollowing)
                }catch (e:Exception){
                    Log.d("catch", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("FollowingonFailure", "Success Conect")
                val errorMessage = when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("Exception", errorMessage)
            }
        })
    }
    fun setUserFollower(username: String){
        val listFollower = ArrayList<UserFollower>()
        val client= AsyncHttpClient()
        client.addHeader(AUTORIZATION, TOKEN)
        client.addHeader(USERAGENT, REQUEST)
        client.get("$URL_USER_DETAIL_FOLLOWING$username/followers",object :AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                Log.d("setUserFollower", "Success Conect")
                try {
                    val result = String(responseBody)
                    Log.d("result", result)
                    val resultList= JSONArray(result)
                    Log.d("resultList", resultList.toString())
                    val login=resultList.getJSONObject(0).getString("login")
                    Log.d("login", login.toString())
                    for(i in 0 until resultList.length()){
                        val userItem=resultList.getJSONObject(i)
                        val username = userItem.getString("login")
                        val avatar = userItem.getString("avatar_url")

                        val usersFollower = UserFollower(username,avatar)
                        listFollower.add(usersFollower)
                    }
                    Log.d("listFollower", listFollower.toString())
                    listUserFollower.postValue(listFollower)
                }catch (e:Exception){
                    Log.d("catch", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("FolloweronFailure", "Success Conect")
                val errorMessage = when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("Exception", errorMessage)
            }
        })
    }
    fun getUserDetail(): LiveData<ArrayList<UserDetail>> {
        return listUser
    }
    fun getUserFollower(): LiveData<ArrayList<UserFollower>> {
        return listUserFollower
    }
    fun getUserFollowing(): LiveData<ArrayList<UserFollowing>> {
        return listUserFollowing
    }
}