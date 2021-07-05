package movie6.codingtest.movieinfo.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import movie6.codingtest.movieinfo.data.Movie
import movie6.codingtest.movieinfo.Network
import movie6.codingtest.movieinfo.R

/**
 * A fragment representing a list of Items.
 */
class MovieFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                reloadData(view)
            }
        }
        return view
    }

    private fun reloadData(recyclerView: RecyclerView) {
        val movie_URL = "https://api.hkmovie6.com/hkm/movies?type=showing"
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = Network.getTextFromNetwork(movie_URL)
                val movie = Gson().fromJson<List<Movie>>(json,object : TypeToken<List<Movie>>() {}.type)
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = MovieRecyclerViewAdapter(movie)
                }
            } catch (e : Exception) {
                Log.d("NewsListFragment", "reloadData: $e")
            }
        }
    }



    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}