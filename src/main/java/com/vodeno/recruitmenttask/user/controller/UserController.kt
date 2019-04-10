package com.vodeno.recruitmenttask.user.controller

import com.fasterxml.jackson.annotation.JsonFormat
import com.vodeno.recruitmenttask.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
        private val userService: UserService
) {

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody request: CreateUserRequest): User {
        return userService.create(email = request.email)
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): List<User> {
        return userService.findAll()
    }

    @RequestMapping("{id}", method = [RequestMethod.GET])
    fun findById(@PathVariable("id") id: Int): User {
        return userService.findById(id = id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @RequestMapping(params = ["email"], method = [RequestMethod.GET])
    fun findByEmail(@RequestParam("email") email: String): User {
        return userService.findByEmail(email = email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}

data class CreateUserRequest(
        val email: String
)

data class User(
        val id: String,
        val email: String,
        @JsonFormat(pattern = "yyyy-MM-dd")
        val createDate: Date,
        @JsonFormat(pattern = "yyyy-MM-dd")
        val billingMonthStartDate: Date,
        @JsonFormat(pattern = "yyyy-MM-dd")
        val billingMonthEndDate: Date
)