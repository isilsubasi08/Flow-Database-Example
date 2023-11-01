package com.isilsubasi.flowdatabase.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isilsubasi.flowdatabase.utils.Constants.CONTACTS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContacts(contactsEntity: ContactsEntity)

    @Query("SELECT * FROM $CONTACTS_TABLE")
    fun getAllContacts() : Flow<MutableList<ContactsEntity>>

    @Query("DELETE FROM $CONTACTS_TABLE")
    fun deleteAllContacts()

}