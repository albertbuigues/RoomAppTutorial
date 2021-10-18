package com.example.roomapp.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.viewmodels.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val firstName = view.findViewById<EditText>(R.id.et_updated_first_name)
        val lastName = view.findViewById<EditText>(R.id.et_updated_last_name)
        val age = view.findViewById<EditText>(R.id.et_updated_age)
        val updateBtn: Button = view.findViewById(R.id.btn_update)

        firstName.setText(args.currentUser.firstName)
        lastName.setText(args.currentUser.lastName)
        age.setText(args.currentUser.age.toString())
        updateBtn.setOnClickListener {
            if (inputCheckNotEmpty(firstName = firstName.text.toString(), lastName = lastName.text.toString(), age = age.text)) {
                val updatedUser = User(args.currentUser.id, firstName = firstName.text.toString(), lastName = lastName.text.toString(), age = Integer.parseInt(age.text.toString()))
                viewModel.updateUser(updatedUser)
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.listFragment)
            } else {
                Toast.makeText(requireContext(), "All fields must be filled out", Toast.LENGTH_SHORT).show()
            }
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun inputCheckNotEmpty(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder
            .setPositiveButton("Yes"){ _,_ ->
                viewModel.deleteUser(args.currentUser)
                Toast.makeText(requireContext(), "User deleted", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.listFragment)
            }
            .setNegativeButton("No"){ _,_ -> }
            .setTitle("Delete ${args.currentUser.firstName}?")
            .setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
            .create().show()
    }
}