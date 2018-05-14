package com.imageworld.ui.fragment.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.imageworld.R
import com.imageworld.model.Post
import com.imageworld.model.UserProfile
import com.parse.ParseFile
import com.parse.ParseUser


class ProfilePresenter(private val view : ProfileContract.View) : ProfileContract.Presenter {

    private lateinit var username : String
    private var imgProfile : Bitmap? = null

    override fun getProfile() {
        val user = ParseUser.getCurrentUser()
        val id = user.objectId
        username = user.username
        val firstname = user.getString("first_name")
        val lastname = user.getString("last_name")
        val bio = user.getString("bio")

        val file : ParseFile = user.get("image_profile") as ParseFile
        file.getDataInBackground { data, e ->
            if (e == null && data != null) {
                imgProfile = BitmapFactory.decodeByteArray(data, 0, data.size)
                val userProfile = UserProfile(
                        id,
                        imgProfile,
                        firstname,
                        lastname,
                        username,
                        bio)
                view.showProfile(userProfile)
            } else {
                imgProfile = null
                val userProfile = UserProfile(
                        id,
                        imgProfile,
                        firstname,
                        lastname,
                        username,
                        bio)
                view.showProfile(userProfile)
            }
        }
    }

    override fun gridView() {
        val post1 = Post(
                1,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_1,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post2 = Post(
                2,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_2,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post3 = Post(
                3,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_3,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post4 = Post(
                4,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_4,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post5 = Post(
                5,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_5,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post6 = Post(
                6,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_6,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post7 = Post(
                7,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_7,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)

        val postList : MutableList<Post> = ArrayList()
        postList.add(post1)
        postList.add(post2)
        postList.add(post3)
        postList.add(post4)
        postList.add(post5)
        postList.add(post6)
        postList.add(post7)

        view.setGridView(postList)
    }

    override fun listView() {
        val post1 = Post(
                1,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_1,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post2 = Post(
                2,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_2,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post3 = Post(
                3,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_3,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post4 = Post(
                4,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_4,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post5 = Post(
                5,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_5,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post6 = Post(
                6,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_6,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)
        val post7 = Post(
                7,
                R.drawable.img_profil_1,
                username,
                R.drawable.img_7,
                "Dude, Jodi, Dean, and others",
                "yeah dude. it is all about PASSION....",
                3952)

        val postList : MutableList<Post> = ArrayList()
        postList.add(post1)
        postList.add(post2)
        postList.add(post3)
        postList.add(post4)
        postList.add(post5)
        postList.add(post6)
        postList.add(post7)

        view.setListView(postList)
    }

    override fun logout() {
        ParseUser.logOutInBackground { e ->
            if (e == null) {
                view.goToLogin()
            } else {
                view.showErrorLogout(e.message)
            }
        }
    }

}