package megha.noteskotlin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.EventLogTags
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.ticket.*

class AddNotes : AppCompatActivity() {
    val dbTables="Note"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        try {
            val bundle: Bundle = intent.extras
            id = bundle.getInt("Id", 0)

            if (id != 0) {
                Title.setText(bundle.getString("Name"))
                Des.setText(bundle.getString("Description"))
            }
        } catch (exp: Exception) {
        }
    }
        fun onAddClick(view: View) {
            var dbManager = DbManager(this)

            var values = ContentValues()
            values.put("Title", Title.text.toString())
            values.put("Description", Des.text.toString())
            if (id == 0) {
                val ID = dbManager.insert(values)

                if (ID > 0) {
                    Toast.makeText(this, "values added", Toast.LENGTH_LONG).show()
                    intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "values cannot be added", Toast.LENGTH_LONG).show()
                }
            }
             else{
                var selectionArgs= arrayOf(id.toString())
                val ID=dbManager.update(values,"Id = ? ",selectionArgs)
                if(ID>0){
                    Toast.makeText(this,"Values updated",Toast.LENGTH_LONG).show()
                    intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Values cannot be updated ",Toast.LENGTH_LONG).show()
                }
            }
        }

}
