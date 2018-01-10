package megha.noteskotlin

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DialogTitle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*
import java.util.*


class MainActivity : AppCompatActivity() {
  var listNotes=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listNotes.add(Note(1,"meet prof.","9:00 am University"))
        listNotes.add(Note(2,"meet doctor","29 dec 11:00 am hospital "))
        listNotes.add(Note(3,"meet friend","3 jan 5:30 pm restaurant "))



        LoadQuery("%")

    }

    fun LoadQuery(title: String){
        val dbManager=DbManager(this)
        val selectionargs= arrayOf(title)
        val projections= arrayOf("Id","Title","Description")
        val cursor=dbManager.Query(projections,"Title LIKE ?",selectionargs,"DESC")
        listNotes.clear()
        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(cursor.getColumnIndex("Id"))
                val title=cursor.getString(cursor.getColumnIndex("Title"))
                val des=cursor.getString(cursor.getColumnIndex("Description"))
                listNotes.add(Note(id,title,des))

            }while (cursor.moveToNext())
        }
        var myNotesAdapter= MyNotesAdapter(this,listNotes)
        list.adapter=myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val sv=menu!!.findItem(R.id.search).actionView as SearchView
        val sm=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))

        sv.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(applicationContext,p0,Toast.LENGTH_LONG).show()
                LoadQuery("%"+p0+"%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })


        return super.onCreateOptionsMenu(menu)
        }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.add -> {
                    var intent= Intent(this,AddNotes::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter:BaseAdapter{
        var  listNoteAdapter=ArrayList<Note>()
        var context:Context?=null
        constructor(context:Context,listNoteAdapter:ArrayList<Note>):super(){
            this.listNoteAdapter=listNoteAdapter
            this.context=context
        }
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.ticket,null)
            var myNote=listNoteAdapter[p0]
            myView.tvTitle.text=myNote.noteName
            myView.tvDes.text=myNote.noteDes
            myView.deletebtn.setOnClickListener(View.OnClickListener {
                var dbmanager=DbManager(this.context!!)
                val selectionargs= arrayOf(myNote.noteId.toString())
                dbmanager.delete("Id=?",selectionargs)
                LoadQuery("%")

            })
            return myView
        }

        override fun getItem(p0: Int): Any {
           return listNoteAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return  p0.toLong()
        }

        override fun getCount(): Int {
           return listNoteAdapter.size
        }

    }
}
