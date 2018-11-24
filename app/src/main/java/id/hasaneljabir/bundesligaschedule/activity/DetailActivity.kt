package id.hasaneljabir.bundesligaschedule.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.hasaneljabir.bundesligaschedule.R
import id.hasaneljabir.bundesligaschedule.R.id.add_to_favorite
import id.hasaneljabir.bundesligaschedule.api.ApiServices
import id.hasaneljabir.bundesligaschedule.api.ApiUrl
import id.hasaneljabir.bundesligaschedule.databaseHelper.FavoriteMatch
import id.hasaneljabir.bundesligaschedule.model.EventData
import id.hasaneljabir.bundesligaschedule.model.TeamData
import id.hasaneljabir.bundesligaschedule.presenter.DetailMatchPresenter
import id.hasaneljabir.bundesligaschedule.repository.LocalRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.repository.TeamRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.utils.DateUtil
import id.hasaneljabir.bundesligaschedule.view.DetailMatchView
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.design.snackbar

class DetailActivity : AppCompatActivity(), DetailMatchView.View {
    lateinit var event: EventData
    lateinit var presenter: DetailMatchPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val service = ApiUrl.getClient().create(ApiServices::class.java)
        val request = TeamRepositoryImplementation(service)
        val localRepo = LocalRepositoryImplementation(applicationContext)
        presenter = DetailMatchPresenter(this, request, localRepo)

        event = intent.getParcelableExtra("match")
        presenter.getLogoHome(event.idHomeTeam)
        presenter.getLogoAway(event.idAwayTeam)
        presenter.checkMatch(event.idEvent)
        initData(event)
        supportActionBar?.title = event.strEvent
    }

    fun initData(event: EventData) {
        txtDate.text = event.dateEvent?.let { DateUtil.formatDateToMatch(it) }
        txtNameHome.text = event.strHomeTeam
        txtNameAway.text = event.strAwayTeam
        txtScoreHome.text = event.intHomeScore
        txtScoreAway.text = event.intAwayScore
        txtHomeScorer.text = event.strHomeGoalDetails
        txtAwayScorer.text = event.strAwayGoalDetails
        txtGkHome.text = event.strHomeLineupGoalkeeper
        txtGkAway.text = event.strAwayLineupGoalkeeper
        txtDefHome.text = event.strHomeLineupDefense
        txtDefAway.text = event.strAwayLineupDefense
        txtMidHome.text = event.strHomeLineupMidfield
        txtMidAway.text = event.strAwayLineupMidfield
        txtForwardHome.text = event.strHomeLineupForward
        txtForwardAway.text = event.strAwayLineupForward
        txtSubtituteHome.text = event.strHomeLineupSubstitutes
        txtSubtituteAway.text = event.strAwayLineupSubstitutes
    }

    override fun showLogoHome(team: TeamData) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
            .into(imgLogoHome)
    }

    override fun showLogoAway(team: TeamData) {
        Glide.with(applicationContext)
            .load(team.strTeamBadge)
            .apply(RequestOptions().placeholder(R.drawable.ic_no_image))
            .into(imgLogoAway)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (!isFavorite) {
                    presenter.insertMatch(
                        event.idEvent, event.idHomeTeam, event.idAwayTeam
                    )
                    layoutDetailMatch.snackbar("Added to favorite").show()
                    isFavorite = !isFavorite
                } else {
                    presenter.deleteMatch(event.idEvent)
                    layoutDetailMatch.snackbar("Removed from favorite").show()
                    isFavorite = !isFavorite
                }
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun setFavoriteState(favList: List<FavoriteMatch>) {
        if (!favList.isEmpty()) isFavorite = true
    }
}