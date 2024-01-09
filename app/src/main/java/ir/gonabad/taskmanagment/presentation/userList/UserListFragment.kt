package ir.gonabad.taskmanagment.presentation.userListimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.fragment.app.viewModelsimport androidx.navigation.fragment.findNavControllerimport androidx.recyclerview.widget.LinearLayoutManagerimport dagger.hilt.android.AndroidEntryPointimport ir.ghadamyaar.aminsoft.stepcounter.helper.DataStateimport ir.gonabad.taskmanagment.Rimport ir.gonabad.taskmanagment.databinding.FragmentUserListBindingimport ir.gonabad.taskmanagment.presentation.home.CategoryAdapterimport ir.gonabad.taskmanagment.utils.BaseFragment@AndroidEntryPointclass UserListFragment : BaseFragment() {    private val TAG = "UserListFragment"    lateinit var binding : FragmentUserListBinding    val viewModel : UserListViewModel by viewModels()    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        binding = FragmentUserListBinding.inflate(inflater , container , false)        return binding.root    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        initViewModel()        initView()        viewModel.getUserList()    }    private fun initView() {        binding.tbUserList.setNavigationOnClickListener {            findNavController().popBackStack()        }        binding.rvUserList.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)    }    private fun initViewModel() {        viewModel.usersLiveData.observe(viewLifecycleOwner){            when(it){                is DataState.Loading -> {                    Log.i(TAG, "initViewModel: Loading")                    showLoading(true)                }                is DataState.Success -> {                    Log.i(TAG, "initViewModel: Success ${it.value}")                    showLoading(false)                    binding.rvUserList.adapter = UserAdapter(it.value){                        viewModel.deleteUser(it)                    }                }                is DataState.LocalError -> {                    Log.i(TAG, "initViewModel: LocalError ${it.message}")                    showLoading(false)                    showToast(getString(R.string.error_server))                }                is DataState.NetworkError -> {                    Log.i(TAG, "initViewModel: NetworkError ${it.networkError}")                    showLoading(false)                    showMessageErrorFromServer(it)                }            }        }        viewModel.deleteUsersLiveData.observe(viewLifecycleOwner){            when(it){                is DataState.Loading -> {                    Log.i(TAG, "initViewModel: Loading")                    showLoading(true)                }                is DataState.Success -> {                    Log.i(TAG, "initViewModel: Success ${it.value}")                    showLoading(false)                    viewModel.getUserList()                }                is DataState.LocalError -> {                    Log.i(TAG, "initViewModel: LocalError ${it.message}")                    showLoading(false)                    showToast(getString(R.string.error_server))                }                is DataState.NetworkError -> {                    Log.i(TAG, "initViewModel: NetworkError ${it.networkError}")                    showLoading(false)                    showMessageErrorFromServer(it)                }            }        }    }    private fun showLoading(show : Boolean) {        binding.rvUserList.visibility = if (show) View.GONE else View.VISIBLE        binding.pbUserList.visibility = if (show) View.VISIBLE else View.GONE    }}