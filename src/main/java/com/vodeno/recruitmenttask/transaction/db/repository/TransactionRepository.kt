package com.vodeno.recruitmenttask.transaction.db.repository

import com.vodeno.recruitmenttask.transaction.db.entity.TransactionEntity
import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface TransactionRepository : JpaRepository<TransactionEntity, Int> {

    @Query("SELECT count(t) From Transaction t, Account s, Account r " +
            "WHERE t.sender = s " +
            "AND t.receiver = r " +
            "AND t.commission = 0 " +
            "AND s.user = ?1 " +
            "AND r.user <> s.user " +
            "AND t.createDate >= ?2")
    fun countCommissionFreeInThisBillingMonthWhereReceiverIsDifferentUser(user: UserEntity, currentBillingMonthStartDate: Date): Int

}