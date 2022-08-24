package com.yamberlajustin.myappsqlite

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY first_name ASC")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}
