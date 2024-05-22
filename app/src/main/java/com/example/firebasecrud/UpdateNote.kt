package com.example.firebasecrud

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebasecrud.databinding.ActivityUpdateNoteBinding

class UpdateNote : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateNoteBinding
    private lateinit var db: NotesDbHelper
    private var id :Int = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateNoteBinding.inflate(layoutInflater);
        setContentView(binding.root)
        setRelativeLayoutMargin()
        db = NotesDbHelper(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        id = intent.getIntExtra("id",-1)

        if(id == -1){
            finish()
            return;
        }

        val note = db.getNoteById(id);
        binding.title.setText(note.title)
        binding.description.setText(note.description)

        binding.update.setOnClickListener{
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()

            val note = Note(id,title,description)

            db.updateNote(note)
            finish()
            Toast.makeText(this,"Note Updated Successfully!!",Toast.LENGTH_SHORT).show()
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