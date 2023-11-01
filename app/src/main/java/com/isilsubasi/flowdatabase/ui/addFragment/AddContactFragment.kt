package com.isilsubasi.flowdatabase.ui.addFragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.isilsubasi.flowdatabase.R
import com.isilsubasi.flowdatabase.databinding.FragmentAddContactBinding
import com.isilsubasi.flowdatabase.db.ContactsEntity
import com.isilsubasi.flowdatabase.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddContactFragment : DialogFragment() {


    @Inject
    lateinit var entity: ContactsEntity

    private val viewModel : DatabaseViewModel by viewModels()

    private var contactId = 0
    private var name=""
    private var phone=""

    private lateinit var binding: FragmentAddContactBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddContactBinding.inflate(inflater,container,false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgClose.setOnClickListener {
                dismiss()
            }
            btnSave.setOnClickListener {
                name=edtName.text.toString()
                phone=edtPhone.text.toString()

                if (name.isEmpty() || phone.isEmpty()){
                    Snackbar.make(it,"Name and phone cannot be empty",Snackbar.LENGTH_SHORT).show()
                }
                else{
                    entity.id=contactId
                    entity.name=name
                    entity.phone=phone

                    viewModel.saveContacts(entity)
                    edtName.setText("")
                    edtPhone.setText("")
                    dismiss()

                }

            }
        }
    }


}