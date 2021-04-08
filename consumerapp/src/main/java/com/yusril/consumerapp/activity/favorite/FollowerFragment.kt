package com.yusril.consumerapp.activity.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusril.consumerapp.R

import com.yusril.consumerapp.adapter.FollowerListAdapter
import com.yusril.consumerapp.databinding.FragmentFollowerBinding
import com.yusril.consumerapp.viewModel.UserDetailMainModel

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {


    private lateinit var binding: FragmentFollowerBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var userDetailMainModel: UserDetailMainModel
    private var username: String? = null
    private lateinit var followerListAdapter: FollowerListAdapter
    companion object {
        var DATA_USERNAME = "data_username"
        // cara newInstance #1 sesuai standart otomatis digenerate oleh android studio
        @JvmStatic
        fun newInstance(username:String?)=  FollowerFragment().apply {
            arguments = Bundle().apply {
                putString(DATA_USERNAME, username)
            }
        }


    }
    // jika memakai cara dr video intruksi dicoding onCreate ini tidak usah dipakai
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(DATA_USERNAME)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailMainModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailMainModel::class.java)
        binding= FragmentFollowerBinding.bind(view)
        val user= arguments?.getString(DATA_USERNAME).toString()
        Log.d("username", user)

        recyclerView=binding.rvFollower
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(context)

        userDetailMainModel.setUserFollower(user)// set data api
        // get data api
        userDetailMainModel.getUserFollower().observe(viewLifecycleOwner,{dataFollower->
            followerListAdapter = FollowerListAdapter(dataFollower)
            recyclerView.adapter=followerListAdapter
        })


    }

}


