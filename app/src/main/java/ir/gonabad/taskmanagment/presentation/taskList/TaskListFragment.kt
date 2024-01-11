package ir.gonabad.taskmanagment.presentation.taskListimport android.graphics.Typefaceimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.MotionEventimport android.view.Viewimport android.view.ViewGroupimport androidx.core.content.res.ResourcesCompatimport androidx.fragment.app.viewModelsimport androidx.navigation.fragment.findNavControllerimport androidx.recyclerview.widget.LinearLayoutManagerimport com.google.gson.Gsonimport dagger.hilt.android.AndroidEntryPointimport ir.ghadamyaar.aminsoft.stepcounter.helper.DataStateimport ir.gonabad.core.domain.ItemTaskimport ir.gonabad.taskmanagment.Rimport ir.gonabad.taskmanagment.databinding.FragmentTaskListBindingimport ir.gonabad.taskmanagment.di.qualifier.MediumTypefaceimport ir.gonabad.taskmanagment.presentation.cviews.ExposedDropDownMenuCustomAdapterimport ir.gonabad.taskmanagment.utils.BaseFragmentimport ir.gonabad.taskmanagment.utils.Constantsimport ir.gonabad.taskmanagment.utils.UserInfoContainerimport ir.gonabad.taskmanagment.utils.getValueFromThemeimport javax.inject.Inject@AndroidEntryPointclass TaskListFragment : BaseFragment() {    private val TAG = "TaskListFragment"    lateinit var binding : FragmentTaskListBinding    val viewModel : TaskListViewModel by viewModels()    private lateinit var sortExposedDropDownAdapter: ExposedDropDownMenuCustomAdapter    var taskAdapter: TaskAdapter ?= null    @Inject    @MediumTypeface    lateinit var mediumTypeFace : Typeface    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        binding = FragmentTaskListBinding.inflate(inflater , container , false)        return binding.root    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        initViewModel()        initView()        setupSort()        arguments?.let {            it.getString("category_id")?.let {c ->                viewModel.getTaskList(c)            }        }    }    private fun initView() {        binding.tbTaskList.setNavigationOnClickListener {            findNavController().popBackStack()        }        binding.fabTaskListAddTask.typeface = mediumTypeFace        binding.fabTaskListAddTask.setOnClickListener {            findNavController().navigate(R.id.action_taskListFragment_to_manageTaskFragment , arguments)        }        binding.rvTaskList.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)        if (UserInfoContainer.typeAccount == Constants.TYPE_USER) {            binding.fabTaskListAddTask.visibility = View.GONE        }    }    private fun setupSort() {        val sortList = listOf(            getString(R.string.show_all) ,            getString(R.string.todo) ,            getString(R.string.doing) ,            getString(R.string.don) ,        )        binding.actvSessionHistorySort.setOnItemClickListener { _, _, i, _ ->            taskAdapter?.let {                when (i) {                    0 -> it.filterByStatus(null)                    1 -> it.filterByStatus(Constants.TASK_STATUS_TODO)                    2 -> it.filterByStatus(Constants.TASK_STATUS_DOING)                    3 -> it.filterByStatus(Constants.TASK_STATUS_DONE)                }            }            sortExposedDropDownAdapter.setSelectedItemPosition(i)        }        sortExposedDropDownAdapter = ExposedDropDownMenuCustomAdapter(            requireContext(),            R.layout.item_simple_exposed_drop_down,            R.id.tv_itemExposedDropDownMenu_item,            sortList.toList(),        )        binding.actvSessionHistorySort.setDropDownBackgroundDrawable(            ResourcesCompat.getDrawable(                resources,                R.drawable.autocomplete_item_shape,                requireActivity().theme            )        )        binding.actvSessionHistorySort.setOnTouchListener { _, motionEvent ->            if (motionEvent.action == MotionEvent.ACTION_DOWN) {                binding.actvSessionHistorySort.setAdapter(sortExposedDropDownAdapter)                binding.actvSessionHistorySort.showDropDown()            }            true        }    }    private fun initViewModel() {        viewModel.taskListLiveData.observe(viewLifecycleOwner){            when(it){                is DataState.Loading -> {                    Log.i(TAG, "initViewModel: Loading")                    showLoading(true)                }                is DataState.Success -> {                    Log.i(TAG, "initViewModel: Success ${it.value}")                    showLoading(false)                    taskAdapter = TaskAdapter(requireContext() ,it.value , object : TaskAdapter.TaskAdapterListener{                        override fun deleteTask(itemTask: ItemTask) {                            Log.i(TAG, "deleteTask: ${itemTask.id}")                            viewModel.deleteTask(itemTask.id)                        }                        override fun emptyList(isEmpty: Boolean) {                            binding.tvTaskListEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE                        }                        override fun editTask(itemTask: ItemTask) {                            findNavController().navigate(R.id.action_taskListFragment_to_manageTaskFragment , Bundle().apply {                                putBoolean("editTask" , true)                                putString("task_json" , Gson().toJson(itemTask))                            })                        }                    })                    binding.rvTaskList.adapter = taskAdapter                }                is DataState.LocalError -> {                    Log.i(TAG, "initViewModel: LocalError ${it.message}")                    showLoading(false)                    showToast(getString(R.string.error_server))                }                is DataState.NetworkError -> {                    Log.i(TAG, "initViewModel: NetworkError ${it.networkError}")                    showLoading(false)                    showMessageErrorFromServer(it)                }            }        }        viewModel.deleteTaskLiveData.observe(viewLifecycleOwner){            when(it){                is DataState.Loading -> {                    Log.i(TAG, "initViewModel: Loading")                    showLoading(true)                }                is DataState.Success -> {                    Log.i(TAG, "initViewModel: Success ${it.value}")                    arguments?.let {                        it.getString("category_id")?.let {c ->                            viewModel.getTaskList(c)                        }                    }                    showLoading(false)                }                is DataState.LocalError -> {                    Log.i(TAG, "initViewModel: LocalError ${it.message}")                    showLoading(false)                    showToast(getString(R.string.error_server))                }                is DataState.NetworkError -> {                    Log.i(TAG, "initViewModel: NetworkError ${it.networkError}")                    showLoading(false)                    showMessageErrorFromServer(it)                }            }        }    }    private fun showLoading(show : Boolean) {        binding.rvTaskList.visibility = if (show) View.GONE else View.VISIBLE        binding.pbTaskList.visibility = if (show) View.VISIBLE else View.GONE    }}