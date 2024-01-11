package ir.gonabad.taskmanagment.presentation.homeimport android.graphics.Typefaceimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.fragment.app.viewModelsimport androidx.navigation.fragment.findNavControllerimport androidx.recyclerview.widget.LinearLayoutManagerimport dagger.hilt.android.AndroidEntryPointimport ir.ghadamyaar.aminsoft.stepcounter.helper.DataStateimport ir.gonabad.taskmanagment.Rimport ir.gonabad.taskmanagment.databinding.FragmentHomeBindingimport ir.gonabad.taskmanagment.di.qualifier.MediumTypefaceimport ir.gonabad.taskmanagment.utils.BaseFragmentimport ir.gonabad.taskmanagment.utils.Constantsimport ir.gonabad.taskmanagment.utils.UserInfoContainerimport saman.zamani.persiandate.PersianDateimport javax.inject.Inject@AndroidEntryPointclass HomeFragment : BaseFragment() {    private val TAG = "HomeFragment"    lateinit var binding : FragmentHomeBinding    val viewModel : HomeViewModel by viewModels()    @Inject    @MediumTypeface    lateinit var mediumTypeFace : Typeface    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View {        binding = FragmentHomeBinding.inflate(inflater , container , false)        return binding.root    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        initViewModel()        initView()        viewModel.getCategory()    }    private fun initView() {        binding.rvHomeCategory.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)        binding.fabHomeAddCategory.typeface = mediumTypeFace        binding.tvHomeUsername.text = getString(R.string.user) + " : " + UserInfoContainer.username        binding.tvHomeDate.text = getString(R.string.today) + " " + PersianDate().shDay + " " + PersianDate().monthName() + " " + PersianDate().shYear        binding.fabHomeAddCategory.setOnClickListener {            findNavController().navigate(R.id.action_homeFragment_to_addCategoryFragment)        }        binding.ivHomeSettingAdmin.setOnClickListener {            findNavController().navigate(R.id.action_homeFragment_to_settingAdminFragment)        }        if (UserInfoContainer.typeAccount == Constants.TYPE_USER) {            binding.ivHomeSettingAdmin.visibility = View.GONE            binding.fabHomeAddCategory.visibility = View.GONE        }    }    private fun initViewModel() {        viewModel.categoryLiveData.observe(viewLifecycleOwner){            when(it){                is DataState.Loading -> {                    Log.i(TAG, "initViewModel: Loading")                    showLoading(true)                }                is DataState.Success -> {                    Log.i(TAG, "initViewModel: Success ${it.value}")                    binding.rvHomeCategory.adapter = CategoryAdapter(it.value , object : CategoryAdapter.ClickItemMenu{                        override fun edit(id: String , title : String) {                            findNavController().navigate(R.id.action_homeFragment_to_addCategoryFragment , Bundle().apply {                                putBoolean("edit" , true)                                putString("id" , id)                                putString("title" , title)                            })                        }                        override fun delete(id: String) {                            viewModel.deleteCategory(id)                        }                        override fun clickItem(id : String) {                            findNavController().navigate(R.id.action_homeFragment_to_taskListFragment , Bundle().apply {                                putString("category_id" , id)                            })                        }                    })                    showLoading(false)                }                is DataState.LocalError -> {                    Log.i(TAG, "initViewModel: LocalError ${it.message}")                    showLoading(false)                    showToast(getString(R.string.error_server))                }                is DataState.NetworkError -> {                    Log.i(TAG, "initViewModel: NetworkError ${it.networkError}")                    showLoading(false)                    showMessageErrorFromServer(it)                }            }        }        viewModel.deleteCategoryLiveData.observe(viewLifecycleOwner){            when(it){                is DataState.Loading -> {                    Log.i(TAG, "initViewModel: Loading")                    showLoading(true)                }                is DataState.Success -> {                    Log.i(TAG, "initViewModel: Success ${it.value}")                    viewModel.getCategory()                    showLoading(false)                }                is DataState.LocalError -> {                    Log.i(TAG, "initViewModel: LocalError ${it.message}")                    showLoading(false)                    showToast(getString(R.string.error_server))                }                is DataState.NetworkError -> {                    Log.i(TAG, "initViewModel: NetworkError ${it.networkError}")                    showLoading(false)                    showMessageErrorFromServer(it)                }            }        }    }    private fun showLoading(show : Boolean) {        binding.rvHomeCategory.visibility = if (show) View.GONE else View.VISIBLE        binding.pbHome.visibility = if (show) View.VISIBLE else View.GONE    }}