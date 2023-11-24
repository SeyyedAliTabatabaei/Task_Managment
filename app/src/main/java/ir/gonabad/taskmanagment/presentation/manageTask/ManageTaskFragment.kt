package ir.gonabad.taskmanagment.presentation.manageTaskimport android.content.res.ColorStateListimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.fragment.app.viewModelsimport androidx.navigation.fragment.findNavControllerimport com.google.android.material.chip.Chipimport dagger.hilt.android.AndroidEntryPointimport ir.gonabad.taskmanagment.Rimport ir.gonabad.taskmanagment.databinding.FragmentManageTaskBindingimport ir.gonabad.taskmanagment.utils.BaseFragmentimport ir.gonabad.taskmanagment.utils.getValueFromTheme@AndroidEntryPointclass ManageTaskFragment : BaseFragment() {    lateinit var binding : FragmentManageTaskBinding    val viewModel : ManageTaskViewModel by viewModels()    private var userListSelected = arrayListOf<Int>()    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        binding = FragmentManageTaskBinding.inflate(inflater , container , false)        return binding.root    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        initView()    }    private fun initView(){        binding.tbAddTask.setNavigationOnClickListener {            findNavController().popBackStack()        }        for (i in 0..10){            binding.cgManageTaskUsers.addView(createChip(i , "chip $i"))        }    }    private fun createChip(id : Int , text : String) : View{        val chip = Chip(requireContext())        chip.text = text        chip.id = id        chip.chipStrokeColor = ColorStateList.valueOf(getValueFromTheme(requireContext() , android.R.attr.colorPrimary))        chip.setOnClickListener {            Log.i("TAG", "createChip: aaaa-> ${chip.isChecked}")            if (chip.chipIcon == null){                chip.chipIcon = requireContext().getDrawable(R.drawable.ic_check)                chip.chipIconSize = 40f                chip.chipStrokeWidth = 3f            } else {                chip.chipIcon = null                chip.chipStrokeWidth = 0f            }        }        return chip    }}