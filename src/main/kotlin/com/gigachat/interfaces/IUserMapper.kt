package com.gigachat.interfaces

import com.gigachat.model.User

interface IUserMapper {
  fun listUsers(s :String): ArrayList<User>
  fun login(username:String, password:String):User?
}