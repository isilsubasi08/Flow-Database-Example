package com.isilsubasi.flowdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.isilsubasi.flowdatabase.R
import com.isilsubasi.flowdatabase.adapter.ContactsAdapter
import com.isilsubasi.flowdatabase.databinding.ActivityMainBinding
import com.isilsubasi.flowdatabase.ui.addFragment.AddContactFragment
import com.isilsubasi.flowdatabase.ui.deleteAll.DeleteAllFragment
import com.isilsubasi.flowdatabase.utils.DataStatus
import com.isilsubasi.flowdatabase.utils.isVisible
import com.isilsubasi.flowdatabase.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var contactsAdapter: ContactsAdapter

    private val viewModel : DatabaseViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //setSupportActionBar(toolbar)

            btnShowDialog.setOnClickListener {
                AddContactFragment().show(supportFragmentManager,AddContactFragment().tag)
            }

            viewModel.getAllContacts()
            viewModel.contactsList.observe(this@MainActivity){
                when(it.status){
                    DataStatus.Status.LOADING -> {
                        loading.isVisible(true,rvContacts)
                        emptyBody.isVisible(false,rvContacts)

                    }
                    DataStatus.Status.SUCCESS -> {
                        it.isEmpty?.let {
                            showEmpty(it)
                        }
                        loading.isVisible(false,rvContacts)
                        contactsAdapter.differ.submitList(it.data)
                        rvContacts.apply {
                            layoutManager=LinearLayoutManager(this@MainActivity)
                            adapter=contactsAdapter
                        }
                    }
                    DataStatus.Status.ERROR -> {
                        loading.isVisible(false,rvContacts)
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }


            }

            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.actionDeleteAll -> {
                        DeleteAllFragment().show(supportFragmentManager,DeleteAllFragment().tag)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.actionSort -> {
                        return@setOnMenuItemClickListener false
                    }
                    R.id.actionSearch -> {
                        return@setOnMenuItemClickListener false
                    }
                    else -> {return@setOnMenuItemClickListener false}
                }
            }


        }


    }

    fun showEmpty(isShown: Boolean){
        binding.apply {
            if (isShown){
                emptyBody.isVisible(true,listBody)
            }else{
                emptyBody.isVisible(false,listBody)
            }
        }

    }

}