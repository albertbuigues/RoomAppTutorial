package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.UserDao
import com.example.roomapp.model.User

class UserRepository(private val userDao: UserDao) {

    val listOfUsers: LiveData<List<User>> = userDao.getAll()

    fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun updateUser(user: User){
        userDao.updateUser(user)
    }

    fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}