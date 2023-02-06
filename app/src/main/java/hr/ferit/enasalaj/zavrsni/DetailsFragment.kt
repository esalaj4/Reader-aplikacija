package hr.ferit.enasalaj.zavrsni

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DetailsFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view =inflater.inflate(R.layout.fragment_details, container, false)
        val bookTitle = arguments?.getString("bookTitle")
        val bookImage = arguments?.getString("bookImage")
        val bookAuthor = arguments?.getString("bookAuthor")
        val bookRating = arguments?.getString("bookRating")
        val bookDescription = arguments?.getString("bookDescription")
        val bookId = arguments?.getString("id")
        Toast.makeText(activity,bookId, Toast.LENGTH_SHORT).show()

        val bookTitleDetail = view?.findViewById<TextView>(R.id.bookTitleDetail)
        val bookImageDetail = view.findViewById<ImageView>(R.id.bookImageDetail)
        val bookAuthorDetail = view?.findViewById<TextView>(R.id.bookAuthorDetail)
        val bookRatingDetail = view?.findViewById<TextView>(R.id.bookRatingDetail)
        val bookDescriptionDetail = view?.findViewById<TextView>(R.id.bookDescriptionDetail)
        bookTitleDetail?.text = bookTitle
        bookAuthorDetail?.text = bookAuthor
        bookRatingDetail?.text = bookRating
        Glide.with(this).load(bookImage).into(bookImageDetail)
        bookDescriptionDetail?.text = bookDescription
        val goBack = view.findViewById<ImageButton>(R.id.goBackButton2)

        goBack.setOnClickListener{
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }





        if (bookId != null) {
            MainActivity().db.collection("books").document(bookId).get().addOnSuccessListener { document ->
                if (document != null) {
                    val reviews = document.get("reviews") as List<String>
                    val reviewsTitle = document.get("reviewTitles") as List<String>
                    val recyclerView = view?.findViewById<RecyclerView>(R.id.reviews_recycle_view)
                    recyclerView?.layoutManager = LinearLayoutManager(activity)
                    recyclerView?.adapter = ReviewsAdapter(reviews,reviewsTitle)
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        }


        return view;
    }


}