package movie6.codingtest.movieinfo.data

data class Movie(
        val id: Int,
        val uuid: String,
        val name: String,
        val chiName: String,
        val dbTrailerUrl: String,
        val thumbnail: String,
        val openDate: String,
        val duration: Int,
        val synopsis: String,
        val chiSynopsis: String,
        val rating: Int,
        val interestingness: Int,
        val commentCount: Int,
        val intertingnessComing: Int,
        val engNormalAltNames: String,
        val favCount: Int,
        val language: String,
        val chiLanguage: String,
        val isCommentable: Boolean,
        val isShowPromoIcon: Boolean,
        val isOpenMonth: Boolean,
        val status: Int,
        val pagination: Pagination,
        val favourite: Boolean,
        val notification: Boolean,
        val screenShots: ArrayList<String>,
        val commentDate: String,
        val rateCount: Int,
        val multitrailers: ArrayList<String>,
        val trailerUrl: String,
        val infoDict: InfoDict,
        val chiInfoDict: chiInfoDict
)

data class Pagination(
        val comments: ArrayList<String>,
        val page: Int,
        val size: Int,
        val total: Int
)

data class InfoDict(
        val Cast: String,
        val Category: String,
        val Language: String,
        val Director: String,
        val Duration: String,
        val Genre: String
)

data class chiInfoDict(
        val 演員: String,
        val 語言: String,
        val 級別: String,
        val 導演: String,
        val 片長: String,
        val 類型: String
)