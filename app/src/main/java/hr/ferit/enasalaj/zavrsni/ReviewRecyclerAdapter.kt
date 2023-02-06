package hr.ferit.enasalaj.zavrsni

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ReviewsAdapter(private val reviews: List<String>,private val reviewsTitle:List<String>) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reviews_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       when(holder)
       {
           is ViewHolder -> {
               holder.bind(position,reviews[position],reviewsTitle[position])
           }
       }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val review_: TextView = view.findViewById<TextView>(R.id.reviews_recycler)
        private val reviewsTitle:TextView = view.findViewById<TextView>(R.id.reviewTitle)

        fun bind(index:Int,
                 review:String,
                reviewTitle:String) {
            review_.setText(review)
            reviewsTitle.setText(reviewTitle)
        }
    }
}
