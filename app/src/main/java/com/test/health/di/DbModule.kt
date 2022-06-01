package com.test.health.di

import android.content.Context
import androidx.room.Room
import com.test.health.data.dao.MedicineDao
import com.test.health.data.dao.ReceptionDao
import com.test.health.data.dao.TypeDao
import com.test.health.data.db.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): MainDatabase =
        Room.databaseBuilder(context, MainDatabase::class.java, "db")
            .build()

    @Provides
    @Singleton
    fun provideMedicineDao(mainDatabase: MainDatabase): MedicineDao = mainDatabase.medicineDao()

    @Provides
    @Singleton
    fun provideReceptionDao(mainDatabase: MainDatabase): ReceptionDao = mainDatabase.receptionDao()

    @Provides
    @Singleton
    fun provideTypeDao(mainDatabase: MainDatabase): TypeDao = mainDatabase.typeDao()
}