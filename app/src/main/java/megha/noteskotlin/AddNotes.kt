package megha.noteskotlin

import android.content.ContentValues
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.ticket.*

class AddNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
    }

    fun onAddClick(view: View){
        var dbManager= DbManager(this)

        var values=ContentValues()
        values.put("Title",Title.text.toString())
        values.put("Description",Des.text.toString())
        val id=dbManager.insert(values)
        if(id>0){
            Toast.makeText(this,"values added",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,"values cannot be added",Toast.LENGTH_LONG).show()
        }
    }
}
