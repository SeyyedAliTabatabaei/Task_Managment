package ir.gonabad.taskmanagment.presentation.loginimport android.util.Logimport androidx.lifecycle.MutableLiveDataimport androidx.lifecycle.viewModelScopeimport dagger.hilt.android.lifecycle.HiltViewModelimport ir.ghadamyaar.aminsoft.stepcounter.helper.DataStateimport ir.gonabad.core.domain.login.Loginimport ir.gonabad.core.intractors.LoginUserimport ir.gonabad.taskmanagment.utils.BaseViewModelimport ir.gonabad.taskmanagment.utils.IsOnlineimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.flow.catchimport kotlinx.coroutines.launchimport javax.inject.Inject@HiltViewModelclass LoginViewModel @Inject constructor(    private val loginUser: LoginUser ,    private val isOnline: IsOnline) : BaseViewModel() {    val loginLiveData = MutableLiveData<DataState<Login>>()    fun login(username : String , password : String) {        viewModelScope.launch(Dispatchers.IO) {            loginLiveData.postValue(DataState.Loading)            loginUser(username , password, isOnline.isOnline()).catch {                loginLiveData.postValue(DataState.LocalError(it.message))            }.collect {                loginLiveData.postValue(it)            }        }    }}