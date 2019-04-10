package com.vodeno.recruitmenttask.user.db.repository

import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface UserRepository : JpaRepository<UserEntity, Int> {

    fun findByEmail(email: String): UserEntity?

}