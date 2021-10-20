package com.example.flickerbrowserappretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    //my views:
    lateinit var etSearch: EditText
    lateinit var btnSearch: Button
    lateinit var imgView: ImageView
    lateinit var Layout: LinearLayout

    //recycler view arraylist
    lateinit var photoList:ArrayList<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoList= arrayListOf()
        rv_main.adapter=RecyclerAdapter(this,photoList)
        rv_main.layoutManager= LinearLayoutManager(this)

        //initalize my views
        etSearch=findViewById(R.id.edSearch)
        btnSearch=findViewById(R.id.btnSearch)
        imgView=findViewById(R.id.imgViewOpen)
        Layout=findViewById(R.id.LL1)

        //button listener
        btnSearch.setOnClickListener(){
            if (etSearch.text.isNotEmpty()){
                getPhoto()
            }else
                Toast.makeText(this, "Search field is empty", Toast.LENGTH_LONG).show()
        }
        imgView.setOnClickListener { closeImg() }
    }

    ////////////
    fun getPhoto(){

        val apiInterface=APIclint().getClient()?.create(APIinterface::class.java)

        if (apiInterface != null){
            apiInterface.getDate(etSearch.text.toString())?.enqueue(object : Callback<photoData?> {
                override fun onResponse(call: Call<photoData?>, response: Response<photoData?>) {
                    val response = response.body()!!.photos!!.photo
                    Log.d("infoxx",response.toString())
                   for (item in response)
                    {
                        val title=item.title
                        Log.d("title",title!!)
                        //these variables helps in build photo link
                        val farmID = item.farm
                        val serverID = item.server
                        val id = item.id
                        val secret = item.secret
                        //use previous variables to get photo link
                        val photoLink = "https://farm$farmID.staticflickr.com/$serverID/${id}_$secret.jpg"
                        photoList.add(listOf(title!!,photoLink))
                    }
                    Log.d("infoxx", photoList.toString())
                    rv_main.adapter?.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<photoData?>, t: Throwable) {
                    Log.d("fail",t.message.toString())

                }
            }) //end enqueue
        }//end if (apiInterface != null)
    }//end get photo

    fun openImg(link:String){
        Glide.with(this).load(link).into(imgViewOpen)
        imgView.isVisible=true
        LL1.isVisible=false
        rv_main.isVisible=false
    }

    fun closeImg(){
        imgView.isVisible=false
        LL1.isVisible=true
        rv_main.isVisible=true
    }
}