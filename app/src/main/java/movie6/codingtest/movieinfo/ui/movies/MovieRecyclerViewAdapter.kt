package movie6.codingtest.movieinfo.ui.movies

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import movie6.codingtest.movieinfo.data.Movie
import movie6.codingtest.movieinfo.R
import java.text.SimpleDateFormat
import java.util.*


class MovieRecyclerViewAdapter(
    private val values: List<Movie>
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_move_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        Picasso.get().load(item.thumbnail).into(holder.movieView)

        if (item.rateCount <= 0) {
            holder.ratingView.text = "NR"
        } else {
            holder.ratingView.text = item.rating.toString()
        }

        holder.movieNameView.text = item.name

        holder.favCountView.text = item.favCount.toString()

        holder.commentCountView.text = item.commentCount.toString()

        val jsonDate =
            SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss", Locale.getDefault()).parse(item.openDate)
        val stringDate = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(jsonDate!!)

        holder.openDateView.text = stringDate

        holder.movieID = item.id
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieView: ImageView = view.findViewById(R.id.movieImageView)
        val ratingView: TextView = view.findViewById(R.id.ratingTextView)
        val movieNameView: TextView = view.findViewById(R.id.movieNameTextView)
        val favCountView: TextView = view.findViewById(R.id.favCountTextView)
        val commentCountView: TextView = view.findViewById(R.id.commentCountTextView)
        val openDateView: TextView = view.findViewById(R.id.openDateTextView)

        var movieID : Int = 0

        init {
            view.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_movieFragment_to_moviedetailFragment,
                    bundleOf(Pair("property_ID", movieID))
                )
            }
        }


        override fun toString(): String {
            return "${super.toString()} , ${ratingView.text} "
        }
    }
}