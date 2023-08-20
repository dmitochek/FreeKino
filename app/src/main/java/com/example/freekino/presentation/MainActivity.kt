package com.example.freekino.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freekino.R
import com.example.freekino.data.repository.FilmsRepositoryImpl
import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.usecase.RefreshCatalogUseCase
import com.example.freekino.domain.usecase.ShowBasicVideoInfoUseCase


class MainActivity : AppCompatActivity() {

    private val filmsRepository = FilmsRepositoryImpl()
    private val showBasicVideoInfoUseCase = ShowBasicVideoInfoUseCase(filmsRepository = filmsRepository)
    private val refreshCatalogUseCase = RefreshCatalogUseCase()
    private lateinit var gridView: GridView

    //menu drawer
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFilms(showBasicVideoInfoUseCase.execute())

        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO make search and refresh
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            when (item.itemId){
                R.id.new_movies -> showFilms(showBasicVideoInfoUseCase.execute())
                R.id.films -> showFilms(showBasicVideoInfoUseCase.execute())
                R.id.serials -> showFilms(showBasicVideoInfoUseCase.execute())
                R.id.cartoons -> showFilms(showBasicVideoInfoUseCase.execute())
                R.id.animated_series -> showFilms(showBasicVideoInfoUseCase.execute())
            }
            return true
        }
        else {
            when (item.itemId) {
                R.id.search_button -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                R.id.refresh_button -> Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            }
            return super.onOptionsItemSelected(item)
        }
    }
    private fun showFilms(videos: Array<VideoBasicInfo?>){
        //val clmns = 3
        var data: MutableList<VideoBasicInfo?> = videos.toMutableList()
        gridView = findViewById(R.id.videos)
        val mainAdapter = MainAdapter(this@MainActivity, data)
        gridView.adapter = mainAdapter

        gridView.setOnScrollListener(object: AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (totalItemCount - visibleItemCount <= firstVisibleItem) {
                    val tmp = data.toMutableList()
                    data.clear()
                    data.addAll(tmp + showBasicVideoInfoUseCase.execute())
                    mainAdapter.notifyDataSetChanged()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, state: Int) {
            }
        })
    }
}
