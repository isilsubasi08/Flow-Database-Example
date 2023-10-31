package com.isilsubasi.flowdatabase.repository

import com.isilsubasi.flowdatabase.db.ContactsDao
import com.isilsubasi.flowdatabase.db.ContactsEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: ContactsDao){


    suspend fun saveContact(entity: ContactsEntity) = dao.saveContacts(entity)


}