package com.melchiorjunior.todolist.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.melchiorjunior.todolist.R
import com.melchiorjunior.todolist.databinding.FragmentHomeBinding
import com.melchiorjunior.todolist.datasource.application.TaskApplication
import com.melchiorjunior.todolist.ui.fragments.TaskViewModel
import com.melchiorjunior.todolist.ui.fragments.TaskViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TaskViewModel
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewModelFactory =
            TaskViewModelFactory((requireActivity().application as TaskApplication).repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        // Recycler View
        binding.homeFragmentRecyclerTasks.layoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.grid_column_count))
        binding.homeFragmentRecyclerTasks.adapter = adapter

        setObservers()
        setListeners()
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Change the layout if the list is not empty
     */
    private fun setObservers() {
        viewModel.taskList.observe(viewLifecycleOwner, { tasks ->
            adapter.notifyDataSetChanged()
            adapter.submitList(tasks)
            if (tasks.isNotEmpty()) {
                binding.emptyState.emptyStateConstraint.visibility = View.GONE
                binding.homeFragmentRecyclerTasks.visibility = View.VISIBLE
            } else if (tasks.isNullOrEmpty()) {
                binding.emptyState.emptyStateConstraint.visibility = View.VISIBLE
            }
        })
    }


    private fun setListeners() {
        // Fab
        binding.fabAddTask.setOnClickListener {
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddTaskFragment()
            )
        }

        // item_task listeners
        adapter.listenerEdit = { task ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddTaskFragment().setTask(task)
            )
        }

        adapter.listenerDelete = { task ->
            viewModel.deleteTask(task)
        }
    }

}