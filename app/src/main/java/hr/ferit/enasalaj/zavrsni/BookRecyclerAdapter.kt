package hr.ferit.enasalaj.zavrsni

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


enum class ItemClickType {
    IMAGE,
    REVIEW
}

var last :Book = Book(null,null,null,null,null,"2",null,null)

class BookRecyclerAdapter(
    val items:ArrayList<Book>,
    val listener: ContentListener,

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_books, parent, false)
        return BookViewHolder(mainView)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookViewHolder -> {
                holder.bind(position, listener, items[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }



    class BookViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        private val bookImage =
            view.findViewById<ImageView>(R.id.bookImage)
        private val bookAuthor =
            view.findViewById<EditText>(R.id.bookAuthor)
        private val bookTitle =
            view.findViewById<EditText>(R.id.bookTitle)
        private val bookRating =
            view.findViewById<EditText>(R.id.bookRating)
        private val reviewButton =
            view.findViewById<Button>(R.id.writeReviewButton)


        fun bind(
            index: Int,
            listener: ContentListener,
            item: Book,
        ) {


            Glide.with(view.context).load(item.image).into(bookImage)
            bookAuthor.setText(item.author)
            bookTitle.setText(item.title)
            bookRating.setText(item.rating)

            reviewButton.setOnClickListener {
                last = item
                listener.onItemButtonClick(index,item,ItemClickType.REVIEW)
            }

            bookImage.setOnClickListener {
                listener.onItemButtonClick(index,item,ItemClickType.IMAGE)

            }
        }
    }

    interface ContentListener {
        fun onItemButtonClick(index: Int, item: Book, clickType: ItemClickType)
    }
}