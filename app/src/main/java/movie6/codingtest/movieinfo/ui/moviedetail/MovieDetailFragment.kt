package movie6.codingtest.movieinfo.ui.moviedetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.slidertypes.DefaultSliderView
import com.glide.slider.library.slidertypes.TextSliderView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import movie6.codingtest.movieinfo.Network
import movie6.codingtest.movieinfo.R
import movie6.codingtest.movieinfo.data.Movie
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailFragment : Fragment() {
    lateinit var ratingView: TextView
    lateinit var movieNameView: TextView
    lateinit var favCountView: TextView
    lateinit var commentCountView: TextView
    lateinit var dateDurCatView: TextView
    lateinit var synopsis: TextView
    lateinit var directorView: TextView
    lateinit var castView: TextView
    lateinit var genreView: TextView
    lateinit var languageView: TextView

    lateinit var sliderShow: SliderLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_moviedetail, container, false)

        val movieID = arguments?.getString("movie_ID")

        setupPalette(view)
        loadData(movieID!!)

        return view
    }

    override fun onStop() {
        sliderShow.stopAutoCycle()
        super.onStop()
    }

    private fun setupPalette(view: View) {
        ratingView = view.findViewById(R.id.ratingDTextView)
        movieNameView = view.findViewById(R.id.movieDNameTextView)
        favCountView = view.findViewById(R.id.favCountDTextView)
        commentCountView = view.findViewById(R.id.commentCountDTextView)
        dateDurCatView = view.findViewById(R.id.dateDurCatTextView)
        synopsis = view.findViewById(R.id.synopsisTextView)
        directorView = view.findViewById(R.id.directorTextView)
        castView = view.findViewById(R.id.castTextView)
        genreView = view.findViewById(R.id.genreTextView)
        languageView = view.findViewById(R.id.languageTextView)
        sliderShow = view.findViewById(R.id.slider)
    }


    private fun loadData(movieID: String) {
        val movieURL = "https://api.hkmovie6.com/hkm/movies/$movieID"
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = Network.getTextFromNetwork(movieURL)
                val movie = Gson().fromJson<Movie>(json, object : TypeToken<Movie>() {}.type)
                CoroutineScope(Dispatchers.Main).launch {
                    setUIText(movie)
                }
            } catch (e: Exception) {
                Log.d("movieFragment", "reloadData: $e")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUIText(movie: Movie){

        ratingView.text = if (movie.rating != null) {
            val rating = movie.rating.toString().dropLast(1) // e.g. 395 -> 39
            StringBuffer(rating.trim()).insert(rating.length - 1, ".") // e.g. 39 -> 3.9
        } else {
            "NR"
        }

        movieNameView.text = movie.name

        favCountView.text = movie.favCount.toString()

        commentCountView.text = movie.commentCount.toString()

        val jsonDate =
            SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss", Locale.getDefault()).parse(movie.openDate)
        val stringDate = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(jsonDate!!)
        dateDurCatView.text = "$stringDate || ${movie.duration} mins || ${movie.infoDict.Category}"


        synopsis.text = movie.synopsis ?: "NR"

        directorView.text = movie.infoDict.Director ?: "NR"

        castView.text = movie.infoDict.Cast ?: "NR"

        genreView.text = movie.infoDict.Genre ?: "NR"

        languageView.text = movie.infoDict.Language ?: "NR"

        if (movie.screenShots.size <= 0){
            val sliderView = DefaultSliderView(requireContext())
            sliderView
                .image(R.drawable.ic_baseline_movie_24)
                .setProgressBarVisible(true)

            sliderShow.addSlider(sliderView)

        } else {
            movie.screenShots.forEach { screenShotUrl ->
                val sliderView = DefaultSliderView(requireContext())

                sliderView
                    .image(screenShotUrl)
                    .setProgressBarVisible(true)

                sliderShow.addSlider(sliderView)
            }
        }
    }
}