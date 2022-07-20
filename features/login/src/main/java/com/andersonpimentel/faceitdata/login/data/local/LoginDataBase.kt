package com.andersonpimentel.faceitdata.login.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andersonpimentel.faceitdata.login.data.local.dao.UserDataDao
import com.andersonpimentel.faceitdata.login.data.model.UserTokenLocalModel

@Database(
    version = 1,
    entities = [UserTokenLocalModel::class]
)
abstract class LoginDataBase : RoomDatabase() {

    abstract fun userDataDao(): UserDataDao

    companion object {
        fun createDataBase(context: Context): LoginDataBase {
            return Room
                .databaseBuilder(
                    context,
                    LoginDataBase::class.java,
                    "user_tokens"
                )
                .build()
        }
    }
}