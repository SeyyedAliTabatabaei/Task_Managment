package ir.gonabad.taskmanagment.diimport dagger.Moduleimport dagger.Providesimport dagger.hilt.InstallInimport dagger.hilt.components.SingletonComponentimport ir.gonabad.core.data.UserRepositoryimport ir.gonabad.core.intractors.AddCategoryimport ir.gonabad.core.intractors.AddTaskimport ir.gonabad.core.intractors.DeleteCategoryimport ir.gonabad.core.intractors.DeleteUserimport ir.gonabad.core.intractors.EditCategoryimport ir.gonabad.core.intractors.GetCategoryListimport ir.gonabad.core.intractors.GetTokenimport ir.gonabad.core.intractors.GetTypeimport ir.gonabad.core.intractors.GetUserListimport ir.gonabad.core.intractors.GetUsernameimport ir.gonabad.core.intractors.LoginUserimport ir.gonabad.core.intractors.RegisterUserimport javax.inject.Singleton@Module@InstallIn(SingletonComponent::class)class UseCaseModule {    @Provides    @Singleton    fun provideLogin(userRepository: UserRepository): LoginUser = LoginUser(userRepository)    @Provides    @Singleton    fun provideRegister(userRepository: UserRepository): RegisterUser = RegisterUser(userRepository)    @Provides    @Singleton    fun provideGetToken(userRepository: UserRepository): GetToken = GetToken(userRepository)    @Provides    @Singleton    fun provideGetType(userRepository: UserRepository): GetType = GetType(userRepository)    @Provides    @Singleton    fun provideGetUsername(userRepository: UserRepository): GetUsername = GetUsername(userRepository)    @Provides    @Singleton    fun provideGetCategoryList(userRepository: UserRepository): GetCategoryList = GetCategoryList(userRepository)    @Provides    @Singleton    fun provideAddCategory(userRepository: UserRepository): AddCategory = AddCategory(userRepository)    @Provides    @Singleton    fun provideEditCategory(userRepository: UserRepository): EditCategory = EditCategory(userRepository)    @Provides    @Singleton    fun provideDeleteCategory(userRepository: UserRepository): DeleteCategory = DeleteCategory(userRepository)    @Provides    @Singleton    fun provideGetUserList(userRepository: UserRepository): GetUserList = GetUserList(userRepository)    @Provides    @Singleton    fun provideDeleteUser(userRepository: UserRepository): DeleteUser = DeleteUser(userRepository)    @Provides    @Singleton    fun provideAddTask(userRepository: UserRepository): AddTask = AddTask(userRepository)}