package com.example.ioasys.ui.viewholder

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ioasys.*
import com.example.ioasys.service.constants.IoasysConstants.KeyWords.KEY
import com.example.ioasys.service.listeners.OnEnterpriseInfoClickListener
import com.example.ioasys.service.models.EnterpriseInfo
import com.example.ioasys.ui.adapter.RecyclerViewAdapter
import com.example.ioasys.ui.viewmodel.InfoViewModel
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    private lateinit var mInfoViewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        mInfoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)

        val mToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_menu)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        observer()
    }

    private fun observer() {
        mInfoViewModel.model.observe(this, Observer {
            val list = it.onValidation()[0]
            val code = it.onValidation()[1]
            if (code == 200) {
                when {
                    (list as List<EnterpriseInfo>).isNotEmpty() -> {
                        close()
                        entrouerro.visibility = View.GONE
                        recyclerviewList.visibility = View.VISIBLE
                        val adapter = RecyclerViewAdapter(
                            list,
                            object :
                                OnEnterpriseInfoClickListener {
                                override fun onClickListener(item: EnterpriseInfo) {
                                    val i = Intent(baseContext, DetailActivity::class.java)
                                    i.putExtra(KEY, item)
                                    startActivity(i)
                                }
                            })
                        recyclerviewList.layoutManager = LinearLayoutManager(this)
                        recyclerviewList.adapter = adapter
                    }
                    list.isEmpty() -> {
                        close()
                        recyclerviewList.visibility = View.GONE
                        entrouerro.visibility = View.VISIBLE
                    }
                }
            } else if (code == 401) {
                close()
                Toast.makeText(
                    this,
                    "Houve um erro durante a pesquisa, por favor pesquise novamente.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                close()
                Toast.makeText(
                    this,
                    "Houve um erro inesperado, por favor tente mais tarde.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        //Get the SearchView and set the searchable configuration
        val mSearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val mSearchItem = menu?.findItem(R.id.menu_search)
        val mSearchView = mSearchItem?.actionView as SearchView
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(componentName))
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchView.clearFocus()
                mSearchView.setQuery("", false)
                cliquebusca.visibility = View.GONE
                show()
                mInfoViewModel.getEnterpriseList(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    private fun show() {
        progress2.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )

    }

    private fun close() {
        progress2.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}
