package ir.gonabad.taskmanagment.presentation.addCategoryimport android.os.Bundleimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.navigation.fragment.findNavControllerimport dagger.hilt.android.AndroidEntryPointimport ir.gonabad.taskmanagment.databinding.FragmentAddCategoryBindingimport ir.gonabad.taskmanagment.utils.BaseFragment@AndroidEntryPointclass AddCategoryFragment : BaseFragment() {    lateinit var binding : FragmentAddCategoryBinding    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        binding = FragmentAddCategoryBinding.inflate(inflater , container , false)        return binding.root    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        initView()    }    private fun initView() {        binding.tbAddCategory.setNavigationOnClickListener {            findNavController().popBackStack()        }    }}