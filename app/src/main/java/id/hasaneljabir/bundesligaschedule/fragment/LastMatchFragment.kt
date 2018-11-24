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
import id.hasaneljabir.bundesligaschedule.presenter.LastMatchPresenter
import id.hasaneljabir.bundesligaschedule.repository.MatchRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.utils.AppSchedulerProvider
import id.hasaneljabir.bundesligaschedule.utils.invisible
import id.hasaneljabir.bundesligaschedule.utils.visible
import id.hasaneljabir.bundesligaschedule.view.MatchView
import kotlinx.android.synthetic.main.fragment_last_match.*

class LastMatchFragment : Fragment(), MatchView.View {
    private var matchLists: MutableList<EventData> = mutableListOf()
    private lateinit var presenter: LastMatchPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val service = ApiUrl.getClient().create(ApiServices::class.java)
        val request = MatchRepositoryImplementation(service)
        val scheduler = AppSchedulerProvider()
        presenter = LastMatchPresenter(this, request, scheduler)
        presenter.getMatchList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun showMatchList(matchList: List<EventData>) {
        matchLists.clear()
        matchLists.addAll(matchList)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvLastMatch.layoutManager = layoutManager
        rvLastMatch.adapter = MatchListAdapter(matchList, context)
    }

    override fun showLoading() {
        progressBar.visible()
        rvLastMatch.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        progressBar.invisible()
        rvLastMatch.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }
}