package ir.gonabad.core.intractorsimport ir.gonabad.core.data.UserRepositoryclass GetType(private val userRepository: UserRepository) {    operator fun invoke() = userRepository.getType()}