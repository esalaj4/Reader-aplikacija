package hr.ferit.enasalaj.zavrsni

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FieldValue


class ReviewFragment : Fragment() {
    //private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.review, container,
            false

        )
        val button = view.findViewById<Button>(R.id.saveButton)
        val goBack = view.findViewById<ImageButton>(R.id.goBackButton)
        val text = view.findViewById<EditText>(R.id.saveReview)
        text.setOnClickListener{
            text.text.clear()
        }

        goBack.setOnClickListener{
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }

        writeReview(button, last)
        return view;
    }


    fun writeReview(button: Button, book: Book) {
        button.setOnClickListener {
            val review = view?.findViewById<EditText>(R.id.saveReview)
            val reviewTitle = view?.findViewById<EditText>(R.id.reviewTitle)
            val newReview = review?.text.toString()
            val newReviewTitle = review?.text.toString()
            Toast.makeText(activity, book.id, Toast.LENGTH_SHORT).show()

            val bookRef = MainActivity().db.collection("books").document(book.id)
            bookRef.update("reviews", FieldValue.arrayUnion(newReview))
                .addOnSuccessListener {
                    Toast.makeText(activity, "Review added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "Error adding review: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }





}