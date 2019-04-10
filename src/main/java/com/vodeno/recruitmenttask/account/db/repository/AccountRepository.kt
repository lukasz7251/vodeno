package com.vodeno.recruitmenttask.account.db.repository

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface AccountRepository : JpaRepository<AccountEntity, Int>