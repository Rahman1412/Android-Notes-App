package com.example.firebasecrud

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecrud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var db : NotesDbHelper;
    private lateinit var noteAdapter : NotesAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        db = NotesDbHelper(this)
        noteAdapter = NotesAdapter(db.getAllNotes(),this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = noteAdapter



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setRelativeLayoutMargin()

        binding.addNote.setOnClickListener{
            startActivity(Intent(this,NoteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.refreshData(db.getAllNotes())
    }

    private fun setRelativeLayoutMargin() {
        // Get the LayoutParams of the RelativeLayout
        val layoutParams = binding.main.layoutParams as ViewGroup.MarginLayoutParams

        // Set the margins
        val marginInDp = convertDpToPixels(16)
        layoutParams.setMargins(marginInDp, marginInDp, marginInDp, marginInDp)

        // Apply the updated LayoutParams to the RelativeLayout
        binding.main.layoutParams = layoutParams
    }

    private fun convertDpToPixels(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}