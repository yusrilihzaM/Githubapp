package com.yusril.consumerapp.activity.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusril.consumerapp.R
import com.yusril.consumerapp.viewModel.UserDetailMainModel
import com.yusril.consumerapp.adapter.FollowingListAdapter
import com.yusril.consumerapp.databinding.FragmentFollowingBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userDetailMainModel: UserDetailMainModel
    private var username: String? = null
    private lateinit var followingListAdapter: FollowingListAdapter
    companion object {
        var DATA_USERNAME = "data_username"
        @JvmStatic
        fun newInstance(username:String?) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(DATA_USERNAME, username)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(FollowerFragment.DATA_USERNAME)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailMainModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailMainModel::class.java)
        binding= FragmentFollowingBinding.bind(view)
        val username= arguments?.getString(DATA_USERNAME).toString()
        Log.d("usernamef", username)

        recyclerView=binding.rvFollowing
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(context)

        userDetailMainModel.setUserFollowing(username)// set data api
        //get data api
        userDetailMainModel.getUserFollowing().observe(viewLifecycleOwner,{dataFollowing->
            followingListAdapter=FollowingListAdapter(dataFollowing)
            recyclerView.adapter=followingListAdapter
        })
    }
}