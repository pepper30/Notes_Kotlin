package megha.noteskotlin

/**
 * Created by Megha Chauhan on 26-Dec-17.
 */
class Note {
    var noteId:Int?=null
    var noteName:String?=null
    var noteDes:String?=null
    constructor(noteId:Int?,noteName:String?,noteDes:String?){
        this.noteDes=noteDes
        this.noteId=noteId
        this.noteName=noteName
    }

}
