package ir.gonabad.taskmanagment.presentation.userListimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.appcompat.widget.PopupMenuimport androidx.recyclerview.widget.RecyclerViewimport ir.gonabad.core.domain.listUser.ResultUserListimport ir.gonabad.core.domain.listUser.UserListResponseimport ir.gonabad.taskmanagment.Rimport ir.gonabad.taskmanagment.databinding.ItemUserBindingimport ir.gonabad.taskmanagment.utils.Constantsclass UserAdapter(private val userList : UserListResponse, private val clickItem : (username : String) -> Unit) : RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder>() {    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapterViewHolder {        return UserAdapterViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context) , parent , false))    }    override fun getItemCount(): Int {        return userList.result.size    }    override fun onBindViewHolder(holder: UserAdapterViewHolder, position: Int) {        holder.onBind(userList.result[position])    }    inner class UserAdapterViewHolder(private val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root){        fun onBind(user : ResultUserList) {            itemUserBinding.tvItemUserUsername.text = user.username            itemUserBinding.tvItemUserType.text = when(user.type){                Constants.TYPE_ADMIN -> "مدیر"                Constants.TYPE_USER -> "کاربر"                Constants.TYPE_OWNER -> "مالک"                else -> "کاربر"            }            itemUserBinding.ivItemUserMenu.setOnClickListener {                val popup = PopupMenu(itemUserBinding.root.context , itemUserBinding.ivItemUserMenu)                popup.menuInflater.inflate(R.menu.user_menu , popup.menu)                popup.setOnMenuItemClickListener {                    when(it.itemId){                        R.id.item_userMenu_deleteUser -> {                            clickItem(user.username)                        }                    }                    return@setOnMenuItemClickListener false                }                popup.show()            }        }    }}