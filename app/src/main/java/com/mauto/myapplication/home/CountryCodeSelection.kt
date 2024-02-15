package com.mauto.myapplication.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager

import com.mauto.myapplication.R
import com.mauto.myapplication.utils.ApiConstant
import com.mauto.myapplication.utils.AppUtils
import kotlinx.android.synthetic.main.countrycodeselection.*
import java.util.*

class CountryCodeSelection : LocaleAwareCompatActivity()
{
    //variable Decalaration
    private lateinit var countryadapater: CountryCodeAdapter
    lateinit var mViewModel: CountryCodeViewModel
    private lateinit var mContext: Activity
    lateinit var fullcountryarray: ArrayList<CountrySelectionModel>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.countrycodeselection)
        mContext = this@CountryCodeSelection
        initViewModel()
        EditTextListenerCall()
        // click listener
        close.setOnClickListener {
            AppUtils.hideKeyboard(mContext, searchtext!!)
           finish()
        }
        clear.setOnClickListener {
            searchtext.text.clear()
            AppUtils.hideKeyboard(mContext, searchtext!!)
        }
    }
    private fun EditTextListenerCall()
    {
        searchtext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                mViewModel.filter(p0.toString(),fullcountryarray)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isEmpty())
                    clear.visibility = View.INVISIBLE
                else
                    clear.visibility = View.VISIBLE
            }
        })
    }
    private fun initRecycleView(countryarray: ArrayList<CountrySelectionModel>)
    {
        fullcountryarray=countryarray
        rv_todo_list.layoutManager = LinearLayoutManager(this)
        countryadapater = CountryCodeAdapter(this,countryarray, object : CountryCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int,code :String,codeshortname :String,countryfullname :String)
            {
                AppUtils.hideKeyboard(mContext, searchtext!!)
                val codeString = code
                val codeShortNameString = codeshortname
                val intentz = Intent()
                intentz.putExtra(ApiConstant.INTENT_OBJECT1, codeShortNameString)
                intentz.putExtra(ApiConstant.INTENT_OBJECT, codeString+","+codeShortNameString+","+countryfullname)
                intentz.putExtra(ApiConstant.INTENT_CUNTYCODE,codeString)
                setResult(Activity.RESULT_FIRST_USER, intentz)
                finish()
            }
        })
        rv_todo_list.adapter = countryadapater
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProvider(this)[CountryCodeViewModel::class.java]
        mViewModel.countryImageArray(mContext)
        mViewModel.countryarrayobserver().observe(this, Observer {
            initRecycleView(it)
        })
        mViewModel.filtercountryarrayobserver().observe(this, Observer {
            countryadapater.filterList(it)
        })
    }
}