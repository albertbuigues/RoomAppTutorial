package com.example.roomapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.viewmodels.UserViewModel

class AddFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_add, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val addButton: Button = fragmentView.findViewById(R.id.btn_add)
        addButton.setOnClickListener { insertUserToDatabase() }

        return fragmentView
    }

    private fun insertUserToDatabase() {
        val firstName: EditText = fragmentView.findViewById(R.id.et_first_name)
        val lastName: EditText = fragmentView.findViewById(R.id.et_last_name)
        val age: EditText = fragmentView.findViewById(R.id.et_age)

        if (inputCheckNotEmpty(firstName = firstName.text.toString(), lastName = lastName.text.toString(), age = age.text)) {
            viewModel.addUser(User(id = 0, firstName = firstName.text.toString(), lastName = lastName.text.toString(), age = Integer.parseInt(age.text.toString())))
            Toast.makeText(requireContext(), "User added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "All fields must be filled out", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheckNotEmpty(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }
}