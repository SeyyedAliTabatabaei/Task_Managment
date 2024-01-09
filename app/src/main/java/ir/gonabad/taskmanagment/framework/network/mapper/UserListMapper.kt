package ir.gonabad.taskmanagment.framework.network.mapperimport ir.gonabad.core.domain.listCategory.CategoryListimport ir.gonabad.core.domain.listUser.UserListResponseimport ir.gonabad.core.domain.login.Loginimport ir.gonabad.core.domain.login.Resultimport ir.gonabad.taskmanagment.framework.network.entities.category.CategoryListEntityimport ir.gonabad.taskmanagment.framework.network.entities.listUser.UserListResponseEntityimport ir.gonabad.taskmanagment.framework.network.entities.login.LoginEntityimport ir.gonabad.taskmanagment.framework.network.entities.login.ResultEntityimport ir.gonabad.taskmanagment.framework.utils.EntityMapperimport javax.inject.Injectclass UserListMapper @Inject constructor() : EntityMapper<UserListResponseEntity , UserListResponse> {    override fun mapFromEntity(entity: UserListResponseEntity): UserListResponse {        return UserListResponse(            result = entity.result ,            status = entity.status        )    }    override fun mapToEntity(domainModel: UserListResponse): UserListResponseEntity {        return UserListResponseEntity(            result = domainModel.result ,            status = domainModel.status        )    }}