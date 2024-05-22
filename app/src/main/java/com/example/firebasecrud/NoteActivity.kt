package com.example.firebasecrud

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebasecrud.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNoteBinding
    private lateinit var db : NotesDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setRelativeLayoutMargin()
        db = NotesDbHelper(this)

        binding.save.setOnClickListener{
            var title = binding.title.text.toString()
            var description = binding.description.text.toString()
            val note = Note(0,title,description)

            db.insertNote(note)
            finish()
            Toast.makeText(this,"Note Added Successfully!!",Toast.LENGTH_SHORT).show()
        }
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