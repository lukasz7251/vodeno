package com.vodeno.recruitmenttask.user.service

import com.vodeno.recruitmenttask.transaction.service.billingmonth.BillingMonthCalculator
import com.vodeno.recruitmenttask.user.controller.User
import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import com.vodeno.recruitmenttask.user.db.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val billingMonthCalculator: BillingMonthCalculator
) : UserService {

    override fun create(email: String): User {
        return userRepository.save(UserEntity().also { it.email = email }).toDomain()
    }

    override fun findAll(): List<User> {
        return userRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: Int): User? {
        return userRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.toDomain()
    }

    private fun UserEntity.toDomain(): User = User(
            email = email,
            id = "$id",
            createDate = createDate,
            billingMonthStartDate = billingMonthCalculator.calculateStartOfCurrent(createDate),
            billingMonthEndDate = billingMonthCalculator.calculateEndOfCurrent(createDate)
    )

}
