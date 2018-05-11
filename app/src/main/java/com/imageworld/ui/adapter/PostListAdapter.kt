package com.imageworld.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.imageworld.R
import com.imageworld.model.Post
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.component_post.view.*

class PostListAdapter(private val postList : List<Post>)
    : RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    private lateinit var mContext: Context

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var imgProfile: CircleImageView = itemView.postImgProfile
        internal var txtUsername: TextView = itemView.postTxtUsername
        internal var btnOptions: ImageButton = itemView.postBtnOption
        internal var imgPost: ImageView = itemView.postImg
        internal var txtSeenBy: TextView = itemView.postTxtSeenBy
        internal var txtPost: TextView = itemView.postTxtPost
        internal var txtSeeComments: TextView = itemView.postTxtSeeComments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.component_post, parent,
                false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgProfile.setImageResource(postList[position].imageProfile)
        holder.txtUsername.text = postList[position].username
        holder.imgPost.setImageResource(postList[position].imagePost)
        holder.txtSeenBy.text = postList[position].seenBy
        holder.txtPost.text = postList[position].post
        val totalComments = postList[position].totalComment
        val seeComments = "see $totalComments comments"
        holder.txtSeeComments.text = seeComments

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}