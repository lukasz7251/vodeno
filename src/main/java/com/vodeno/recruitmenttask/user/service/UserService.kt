package com.vodeno.recruitmenttask.user.service

import com.vodeno.recruitmenttask.user.controller.User

interface UserService {
    fun create(email: String): User
    fun findAll(): List<User>
    fun findById(id: Int): User?
    fun findByEmail(email: String): User?
}