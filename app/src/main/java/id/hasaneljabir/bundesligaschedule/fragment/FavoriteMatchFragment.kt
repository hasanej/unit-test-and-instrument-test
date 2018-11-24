package id.hasaneljabir.bundesligaschedule.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hasaneljabir.bundesligaschedule.R
import id.hasaneljabir.bundesligaschedule.adapter.MatchListAdapter
import id.hasaneljabir.bundesligaschedule.api.ApiServices
import id.hasaneljabir.bundesligaschedule.api.ApiUrl
import id.hasaneljabir.bundesligaschedule.model.EventData
import id.hasaneljabir.bundesligaschedule.presenter.FavoriteMatchPresenter
import id.hasaneljabir.bundesligaschedule.repository.LocalRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.repository.MatchRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.utils.AppSchedulerProvider
import id.hasaneljabir.bundesligaschedule.utils.invisible
import id.hasaneljabir.bundesligaschedule.utils.visible
import id.hasaneljabir.bundesligaschedule.view.FavoriteMatchView
import kotlinx.android.synthetic.main.fragment_favorite_match.*

class FavoriteMatchFragment : Fragment(), FavoriteMatchView.View {
    private var matchLists: MutableList<EventData> = mutableListOf()
    lateinit var presenter: FavoriteMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = ApiUrl.getClient().create(ApiServices::class.java)
        val request = MatchRepositoryImplementation(service)
        val localRepositoryImpl = LocalRepositoryImplementation(context!!)
        val scheduler = AppSchedulerProvider()
        presenter = FavoriteMatchPresenter(this, request, localRepositoryImpl, scheduler)
        presenter.getFootballMatchData()

        swipeRefreshFavMatch.setOnRefreshListener {
            presenter.getFootballMatchData()
        }
    }

    override fun hideLoading() {
        progressBar.invisible()
        rvFavMatch.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.visible()
        rvFavMatch.visibility = View.INVISIBLE
    }

    override fun hideSwipeRefresh() {
        swipeRefreshFavMatch.isRefreshing = false
        progressBar.invisible()
        rvFavMatch.visibility = View.VISIBLE
    }

    override fun displayFootballMatch(matchList: List<EventData>) {
        matchLists.clear()
        matchLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvFavMatch.layoutManager = layoutManager
        rvFavMatch.adapter = MatchListAdapter(matchList, context)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }
}