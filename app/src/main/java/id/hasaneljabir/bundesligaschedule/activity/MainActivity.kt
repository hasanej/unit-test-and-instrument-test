package id.hasaneljabir.bundesligaschedule.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.hasaneljabir.bundesligaschedule.R
import id.hasaneljabir.bundesligaschedule.R.id.*
import id.hasaneljabir.bundesligaschedule.fragment.FavoriteMatchFragment
import id.hasaneljabir.bundesligaschedule.fragment.LastMatchFragment
import id.hasaneljabir.bundesligaschedule.fragment.NextMatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                lastMatch -> {
                    loadLastMatchFragment(savedInstanceState)
                }
                nextMatch -> {
                    loadNextMatchFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }

        bottom_navigation.selectedItemId = lastMatch
    }

    private fun loadLastMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavoriteMatchFragment(), FavoriteMatchFragment::class.java.simpleName)
                .commit()
        }
    }
}