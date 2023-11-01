package com.isilsubasi.flowdatabase.repository

import com.isilsubasi.flowdatabase.db.ContactsDao
import com.isilsubasi.flowdatabase.db.ContactsEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: ContactsDao){


    suspend fun saveContact(entity: ContactsEntity) = dao.saveContacts(entity)

    fun getAllContacts()=dao.getAllContacts()

    fun deleteAllContacts()=dao.deleteAllContacts()

    fun sortedASC()=dao.sortedASC()

    fun sortedDESC()=dao.sortedDESC()

    fun searchContact(name: String) = dao.searchContact(name)


}