package hr.ferit.enasalaj.zavrsni

import org.w3c.dom.Text

data class Book(
    var title:String? = null,
    var author:String? = null,
    var rating:String? = null,
    var description:String? = null,
    var image:String? = null,
    var id: String = "",
    var review:Array<String>? = null,
    var reviewTitles:List<String>? = null,
):java.io.Serializable
