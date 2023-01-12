package com.codingtestwl.list.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingtestwl.R
import com.codingtestwl.base.BaseActivity
import com.codingtestwl.databinding.ActivityMainBinding
import com.codingtestwl.databinding.PopUpBinding
import com.codingtestwl.list.model.Pagination
import com.codingtestwl.list.view.adapter.ListRecyclerAdapter
import com.codingtestwl.list.viewModel.ListViewModel
import com.codingtestwl.util.Utility


class MainActivity : BaseActivity(), View.OnClickListener {

    lateinit var listViewModel: ListViewModel
    private lateinit var binding: ActivityMainBinding
    private var isNextClick: Boolean = false
    private lateinit var pagination: Pagination
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get activity_main XML Binding object
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Set view to setContentView Method
        setContentView(binding.root)

        //Set clickable button
        setOnClickListener()

        //Get ListViewModel object from ViewModelProvider
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]

        //Set FirstPagination Object
        setPagination()

        //SetObserver
        setObserver(pagination.currentPageNo)

    }

    /*Current Pagination*/
    private fun setPagination() {
        pagination = Pagination()
        pagination.currentPageNo = 1
        pagination.nextPageNo = 2
    }

    /*Set CLickListener on Button and searchview*/
    private fun setOnClickListener() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {
                    performingSearch(s.toString())
                    if (s.isEmpty()) {
                        binding.imgClearQuery.visibility = View.INVISIBLE
                    } else {
                        binding.imgClearQuery.visibility = View.VISIBLE
                    }
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }
            }
        }
        binding.swipeOnRefreshLay.setOnRefreshListener { this.setObserver(pagination.currentPageNo) }
        binding.editTextSearch.addTextChangedListener(textWatcher)
        binding.leftShiftBtn.setOnClickListener(this)
        binding.rightShiftBtn.setOnClickListener(this)
        binding.imgClearQuery.setOnClickListener(this)
    }

    /*Start Searching*/
    private fun performingSearch(searchText: String) {
        var adapter = binding.mainRecyclerView.adapter
        if (adapter != null) {
            adapter = (binding.mainRecyclerView.adapter as ListRecyclerAdapter)
            adapter.getFilter()?.filter(searchText)
        }
    }

    /*Getting list from  api ViewModel and set to adapter*/
    private fun setObserver(pageNo: Int) {


        //Check internet and process forward
        if (Utility.isNetworkAvailable(this)) {


            //Set progress bar visible
            if (!binding.swipeOnRefreshLay.isRefreshing)
                binding.mainProgressBar.visibility = View.VISIBLE

            listViewModel.getList(pageNo).observe(this, {
                //Set refreshing close if continue
                if (binding.swipeOnRefreshLay.isRefreshing)
                    binding.swipeOnRefreshLay.isRefreshing = false

                //Set progress bar gone
                binding.mainProgressBar.visibility = View.GONE

                if (it.status) {
                    val adapter = ListRecyclerAdapter(it.mutableList, it.mutableList)
                    binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                    binding.mainRecyclerView.adapter = adapter

                    //Pagination setup for every refresh
                    setPaginationView(pageNo)
                } else {
                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_LONG).show()
                }
            })
        } else {
            //Set refreshing close if continue
            if (binding.swipeOnRefreshLay.isRefreshing)
                binding.swipeOnRefreshLay.isRefreshing = false

            //Set progress bar gone
            binding.mainProgressBar.visibility = View.GONE

            //Show internet alert dialog
            enableInternetAlert()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_refresh) {
            //Call api to refresh current view
            setObserver(pagination.currentPageNo)
        }
        return super.onOptionsItemSelected(item)
    }

    /*No internet alert*/
    private fun enableInternetAlert() {

        //Initialize dialog instance
        val dialog = AlertDialog.Builder(this@MainActivity)

        //Set dialog false to cancel
        dialog.setCancelable(false)

        //Bind xml view
        val binding = PopUpBinding.inflate(layoutInflater)
        binding.descriptionText.text =
            ("Plz, enable internet from your phone setting and then press try again")

        //Set Custom Xml view to dialog
        dialog.setView(binding.root)

        //Create alert
        val alert = dialog.create()

        binding.imageHeader.setImageResource(R.mipmap.server_error)
        binding.titleText.text = ("No Internet !!!")

        binding.okBtn.text = ("try again")

        //Set cancel button visible
        binding.cancelBtn.visibility =View.VISIBLE

        binding.okBtn.setOnClickListener {
            alert.dismiss()
            setObserver(pagination.currentPageNo)
        }
        binding.cancelBtn.setOnClickListener {
            alert.dismiss()
            finish()
        }

        //Make transparent background of dialog
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()

    }

    /*Calculate pagination PageNo and set PageNo on view*/
    private fun setPaginationView(pageNo: Int) {
        try {
            pagination.currentPageNo = pageNo
            pagination.nextPageNo = pagination.currentPageNo + 1
            pagination.lastPageNo = pagination.currentPageNo - 1

            val resources = resources
            if (isNextClick) {

                binding.leftShiftBtn.visibility = View.VISIBLE
                binding.firstPageText.background = null
                binding.firstPageText.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.appColorPrimary,
                        null
                    )
                )
                binding.firstPageText.text = (" ${pagination.lastPageNo} ")
                binding.secondPageText.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.app_color_round_corner, null)
                binding.secondPageText.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.white,
                        null
                    )
                )
                binding.secondPageText.text = (" " + pagination.currentPageNo.toString() + " ")
            } else {
                binding.firstPageText.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.app_color_round_corner, null)
                binding.firstPageText.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.white,
                        null
                    )
                )
                binding.firstPageText.text = (" " + pagination.currentPageNo.toString() + " ")
                if (pagination.currentPageNo == 1) {
                    binding.leftShiftBtn.visibility = View.GONE
                }

                binding.secondPageText.text = (" " + pagination.nextPageNo.toString() + " ")
                binding.secondPageText.visibility = View.VISIBLE
                binding.secondPageText.background = null
                binding.secondPageText.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.appColorPrimary,
                        null
                    )
                )

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            val id = v.id
            if (id == R.id.leftShiftBtn) {
                isNextClick = false
                setObserver(pagination.currentPageNo - 1)
            } else if (id == R.id.rightShiftBtn) {
                isNextClick = true
                setObserver(pagination.currentPageNo + 1)
            } else if (id == R.id.img_clear_query) {
                binding.editTextSearch.setText("")
            }
        }
    }
}