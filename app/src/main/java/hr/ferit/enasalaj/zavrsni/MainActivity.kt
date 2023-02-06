package hr.ferit.enasalaj.zavrsni


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hr.ferit.enasalaj.zavrsni.R.id
import hr.ferit.enasalaj.zavrsni.R.layout
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),BookRecyclerAdapter.ContentListener {
    private lateinit var recyclerAdapter: BookRecyclerAdapter
     val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(id.booksList)

        db.collection("books")
            .get() //da dohvati sve dokumente
            .addOnSuccessListener { //unutar ovoga pristup svim podadcima koji su se ucitali
                val items: ArrayList<Book> = ArrayList()
                for (data in it.documents) { //stvori novu data varijablu za svaki dohvaceni dokument(element?)
                    val book =
                        data.toObject(Book::class.java) //sve podatke pretvaramo u model preko toObject u Person
                    if (book != null) {
                        book.id = data.id  //postavljanje ida
                        items.add(book) //dodavanje gotovog eprsona u listu
                    }
                }
                recyclerAdapter = BookRecyclerAdapter(items, this@MainActivity)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = recyclerAdapter
                }
            }.addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents", exception)
            }

        val searchButton = findViewById<Button>(R.id.searchButton)
        val booksList = findViewById<RecyclerView>(id.booksList)
        val text = findViewById<EditText>(R.id.searchByTitle)
        text.setOnClickListener{
            text.text.clear()
        }


        searchButton.setOnClickListener {
            val text = findViewById<EditText>(R.id.searchByTitle).text.toString()
            db.collection("books").get().addOnSuccessListener { documents ->
                val filteredBooks = ArrayList<Book>()
                for (document in documents) {
                    val book = document.toObject(Book::class.java)
                    if (book.title!!.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) ||
                        book.author!!.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                        filteredBooks.add(book)
                    }
                }
                val adapter = booksList.adapter as BookRecyclerAdapter
                adapter.items.clear()
                if(filteredBooks.isEmpty()) {
                    Toast.makeText(this,"No book with that title or author",Toast.LENGTH_SHORT).show()
                } else {
                    adapter.items.addAll(filteredBooks)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        val color = ContextCompat.getColor(this, R.color.brown)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))

    }




    override fun onItemButtonClick(index: Int, item: Book, clickType: ItemClickType) {

        if (clickType == ItemClickType.REVIEW) {
            val newFragment: Fragment = ReviewFragment()
            val transaction: FragmentTransaction? = supportFragmentManager.beginTransaction()
            transaction?.replace(R.id.container, newFragment);
            transaction?.addToBackStack(null);
            transaction?.commit();
            val searchButton = findViewById<Button>(R.id.searchButton)
            searchButton.visibility = View.GONE

            Toast.makeText(this,"Yay",Toast.LENGTH_SHORT).show()
        }
        else if (clickType == ItemClickType.IMAGE) {
            last = item
            val bundle = Bundle()
            bundle.putString("bookTitle", item.title)
            bundle.putString("bookAuthor", item.author)
            bundle.putString("bookRating", item.rating)
            bundle.putString("bookDescription", item.description)
            bundle.putString("bookImage",item.image)
            bundle.putString("id",item.id)

            val newFragment: Fragment = DetailsFragment()
            newFragment.arguments = bundle
            val transaction: FragmentTransaction? = supportFragmentManager.beginTransaction()
            val recyclerItem = findViewById<RecyclerView>(R.id.booksList)
            recyclerItem.visibility = View.GONE
            val searchButton = findViewById<Button>(R.id.searchButton)
            searchButton.visibility = View.GONE
            val searchTitle = findViewById<EditText>(R.id.searchByTitle)
            searchTitle.visibility = View.GONE
            transaction?.replace(R.id.container, newFragment)
            transaction?.addToBackStack(null)
            transaction?.commit()

        }
    }
}