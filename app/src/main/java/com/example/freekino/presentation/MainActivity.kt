package com.example.freekino.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFilms(showBasicVideoInfoUseCase.execute())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //TODO make search and refresh
        when (item.itemId){
            R.id.search_button -> Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show()
            R.id.refresh_button -> Toast.makeText(this,"Refresh",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
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
