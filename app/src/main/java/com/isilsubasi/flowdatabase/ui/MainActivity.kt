package com.isilsubasi.flowdatabase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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

    var selectedItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            setSupportActionBar(toolbar)

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
                        filter()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {return@setOnMenuItemClickListener false}
                }
            }


        }


    }

    private fun showEmpty(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                emptyBody.visibility = View.VISIBLE
                listBody.visibility = View.GONE
            } else {
                emptyBody.visibility = View.GONE
                listBody.visibility = View.VISIBLE
            }
        }
    }


    private fun filter(){

        val builder = AlertDialog.Builder(this)
        val sortItem = arrayOf("Newer(Default)","Name : A-Z" , "Name : Z-A")
        builder.setSingleChoiceItems(sortItem,selectedItem){ dialog , item ->
            when(item){
                0-> viewModel.getAllContacts()
                1->viewModel.sortedASC()
                2->viewModel.sortedDESC()
            }
            selectedItem=item
            dialog.dismiss()
        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu!!.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        Log.e("isil","burada")
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("isil","burada2")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("isil","burada3")
                viewModel.getSearchContacts(newText!!)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}