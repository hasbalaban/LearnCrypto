package com.finance.trade_learn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentFavoritePageBinding
import com.finance.trade_learn.databinding.FragmentHomeBinding


class favoritePage : Fragment() {

    lateinit var dataBindingFavorite: FragmentFavoritePageBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingFavorite = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favorite_page,
            container, false
        )
        return  dataBindingFavorite.root
    }
}