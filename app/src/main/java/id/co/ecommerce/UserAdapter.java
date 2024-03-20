package id.co.ecommerce;

import id.co.ecommerce.model.Dataitem;

public class UserAdapter(private val users: MutableList<Dataitem>) :
        RecycleView.Adapter<UserAdapter.ListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
            return ListViewHolder {
                view
            }
        }

        fun addUser(newUser: Dataitem) {
            users.add(newUsers)
            notifyItemInserted(users.lastIndex)
        }

        fun clear() {
            users.clear()
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = users.size

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val user = users[position]

            Glide.with(holder.itemView.context)
        .load(user.avatar)
        .apply(RequestOptions().override(80,80).placeholder(R.drawable.icon_avatar))
        .transform(CircleCrop())
        .into(holder.ivAvatar)

        holder.tvUserName.text = "${user.firstName} ${user.lastName}"
        holder.tvEmail.text = user.email

        }

        class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }


}
