package com.isilsubasi.flowdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isilsubasi.flowdatabase.db.ContactsEntity
import com.isilsubasi.flowdatabase.repository.DatabaseRepository
import com.isilsubasi.flowdatabase.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel  @Inject constructor(private val repository: DatabaseRepository) : ViewModel(){


    private val _contactsList = MutableLiveData<DataStatus<List<ContactsEntity>>>()
    val contactsList : LiveData<DataStatus<List<ContactsEntity>>>
    get() = _contactsList


    fun saveContacts(entity: ContactsEntity) = viewModelScope.launch {
        repository.saveContact(entity)
    }


    fun getAllContacts() = viewModelScope.launch {
        repository.getAllContacts()
            .catch { _contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect{_contactsList.postValue(DataStatus.success(it,it.isEmpty()))}
    }





}