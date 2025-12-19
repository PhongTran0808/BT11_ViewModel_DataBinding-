package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ItemViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val username = intent.getStringExtra("username") ?: return
        val dbHelper = DBHelper(this)
        val viewModelFactory = ItemViewModelFactory(dbHelper, username)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()

        viewModel.items.observe(this) {
            adapter.updateItems(it)
        }
    }

    private fun setupRecyclerView() {
        adapter = ItemAdapter(emptyList(),
            onEdit = { item -> showEditItemDialog(item) },
            onDelete = { item -> showDeleteConfirmationDialog(item) })
        binding.recyclerViewItems.adapter = adapter
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
    }

    private fun showEditItemDialog(item: Item) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sửa công việc")
        val input = EditText(this)
        input.setText(item.name)
        builder.setView(input)
        builder.setPositiveButton("Lưu") { _, _ ->
            viewModel.updateItem(item, input.text.toString())
        }
        builder.setNegativeButton("Hủy") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun showDeleteConfirmationDialog(item: Item) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa công việc này?")
            .setPositiveButton("Xóa") { _, _ -> viewModel.deleteItem(item) }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
