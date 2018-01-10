package megha.noteskotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import java.net.ContentHandler
import java.security.AccessControlContext

class DbManager{
    val dbName="MyNotes"
    val dbTable="Notes"
    val colTitle="Title"
    val colId="Id"
    val colDes="Description"
    val dbVersion=1
    val sqlCreateTable="CREATE TABLE  " + dbTable+ " ( " + colId + " INTEGER PRIMARY KEY, "+ colTitle +" TEXT, " + colDes+" TEXT ) ; "

    var sqlDb:SQLiteDatabase?=null

    constructor(context: Context){
      val db= DatabaseHelper(context)
        sqlDb=db.writableDatabase

    }


inner class DatabaseHelper : SQLiteOpenHelper {
    var context:Context
    constructor(context: Context):super(context,dbName,null,dbVersion){
        this.context=context
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(sqlCreateTable)
        Toast.makeText(this.context,"database",Toast.LENGTH_LONG).show()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int){
        p0!!.execSQL("DROP TABLE "+dbTable)
    }
}

 fun insert(values:ContentValues):Long{
    val id= sqlDb!!.insert(dbTable,"",values)
     return id
 }

    fun Query(projection:Array<String>?,selection:String,selectionArgs:Array<String>,sortOrder:String):Cursor{
        val qb=SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDb,projection,selection,selectionArgs,null,null,null)
        return cursor
    }

    fun delete(selection: String,selectionArgs: Array<String>):Int{
        val count=sqlDb!!.delete(dbTable,selection,selectionArgs)
        return count
    }
}
