package com.andersonpimentel.faceitdata.login.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.andersonpimentel.faceitdata.login.data.model.UserTokenLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {
    @Query("SELECT * FROM user_tokens")
    fun getSavedUserTokens(): Flow<UserTokenLocalModel?>

    @Transaction
    fun updateUserTokens(userToken: UserTokenLocalModel){
        deleteUserToken()
        insertUserTokens(userToken)
    }

    @Insert
    fun insertUserTokens(userToken: UserTokenLocalModel)

    @Query("DELETE FROM user_tokens")
    fun deleteUserToken()
}