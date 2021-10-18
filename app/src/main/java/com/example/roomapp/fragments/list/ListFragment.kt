package com.example.roomapp.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.viewmodels.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val usersAdapter = UsersAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = usersAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.listOfUsers.observe(viewLifecycleOwner, Observer{ user ->
            usersAdapter.setData(user)
        })

        val floatingButton: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return view
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
                viewModel.deleteAllUsers()
                Toast.makeText(requireContext(), "Users deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){ _,_ -> }
            .setTitle("Delete all users?")
            .setMessage("Are you sure you want to delete all users?")
            .create().show()
    }
}