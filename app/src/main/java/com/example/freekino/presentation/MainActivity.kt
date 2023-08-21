package com.example.freekino.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freekino.R
import com.example.freekino.data.repository.FilmsRepositoryImpl
import com.example.freekino.data.repository.RefreshFilmsRepositoryImpl
import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.usecase.RefreshCatalogUseCase
import com.example.freekino.domain.usecase.ShowBasicVideoInfoUseCase
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val filmsRepository = FilmsRepositoryImpl()
    private val refreshFilmsRepository = RefreshFilmsRepositoryImpl()
    private val showBasicVideoInfoUseCase = ShowBasicVideoInfoUseCase(filmsRepository = filmsRepository)
    private val refreshCatalogUseCase = RefreshCatalogUseCase(refreshFilmsRepository = refreshFilmsRepository)
    private lateinit var gridView: GridView
    private var prevCategory: Int = 0

    //menu drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    //appbar
    //var actionBar = supportActionBar
    private lateinit var actionBar: ActionBar
    private val titleCategory = arrayOf(
        "Новинки",
        "Фильмы",
        "Сериалы",
        "Мультфильмы",
        "Мультсериалы"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar = supportActionBar!!
        showFilms(showBasicVideoInfoUseCase.execute(0), 0)

        drawerLayout = findViewById(R.id.my_drawer_layout)
        setNavigationViewListener()
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBar.title = titleCategory[0]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun setNavigationViewListener(){
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
    }

    private fun chosenItem(category: Int){
        if (prevCategory != category){
            actionBar.title = titleCategory[category]
            showFilms(showBasicVideoInfoUseCase.execute(category), category)
            prevCategory = category
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.new_movies -> chosenItem(0)
            R.id.films -> chosenItem(1)
            R.id.serials -> chosenItem(2)
            R.id.cartoons -> chosenItem(3)
            R.id.animated_series -> chosenItem(4)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO make search and refresh
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            when (item.itemId) {
                R.id.search_button -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                R.id.refresh_button -> {
                    Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
                    showFilms(refreshCatalogUseCase.execute(prevCategory), prevCategory)
                }
            }
            super.onOptionsItemSelected(item)
        }
    }
    private fun showFilms(videos: Array<VideoBasicInfo?>, category: Int){
        //val clmns = 3
        val data: MutableList<VideoBasicInfo?> = videos.toMutableList()
        gridView = findViewById(R.id.videos)
        val mainAdapter = MainAdapter(this@MainActivity, data)
        gridView.adapter = mainAdapter

        gridView.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (totalItemCount - visibleItemCount <= firstVisibleItem) {
                    val tmp = data.toMutableList()
                    data.clear()
                    //TODO change execute input
                    data.addAll(tmp + showBasicVideoInfoUseCase.execute(category))
                    mainAdapter.notifyDataSetChanged()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, state: Int) {
            }
        })
    }
}
