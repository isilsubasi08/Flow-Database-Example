package com.isilsubasi.flowdatabase.di

import android.content.Context
import androidx.room.Room
import com.isilsubasi.flowdatabase.db.ContactsDB
import com.isilsubasi.flowdatabase.db.ContactsDao
import com.isilsubasi.flowdatabase.db.ContactsEntity
import com.isilsubasi.flowdatabase.utils.Constants.CONTACTS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context,ContactsDB::class.java,CONTACTS_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideDao(db : ContactsDB) =db.contactsDao()


    @Provides
    @Singleton
    fun provideEntity() = ContactsEntity()


}