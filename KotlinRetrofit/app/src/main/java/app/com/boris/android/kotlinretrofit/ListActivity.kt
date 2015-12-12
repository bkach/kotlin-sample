package app.com.boris.android.kotlinretrofit

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import app.com.boris.android.kotlinretrofit.Model.Result
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ListActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        var et = findViewById(R.id.editTextView) as EditText;
        et.setOnEditorActionListener { view: View?, keyCode: Int, keyEvent: KeyEvent? ->
            if (keyCode == EditorInfo.IME_ACTION_DONE){
                Log.d(this.localClassName,et.text.toString())
                reloadAdapter(et.text.toString())

                var inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                        this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS)
                true
            } else {
                false
            }
        }
    }

    fun reloadAdapter(userName : String){
        var lv = findViewById(R.id.listView) as ListView
        val obs  = RetrofitInterface.create().get(userName)
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {

                    var adapter = ListAdapter(this, it)
                    lv.adapter = adapter;
                    lv.onItemClickListener = AdapterView.OnItemClickListener {
                        adapterView, view, i, l ->
                        var name : String = (adapterView.getItemAtPosition(i) as Result).name
                        Log.d(this.localClassName, "${name} chosen")
                    }

                }, { Log.d(this.getLocalClassName(),it.message)} )
    }
}
