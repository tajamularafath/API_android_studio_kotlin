package com.example.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)


        var arraylist = ArrayList<Product>()

        arraylist.add(Product("www.google.com","Honda ZA12"))

        myAdapter = MyAdapter(this@MainActivity, arraylist)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getProductData()

        val fbtn = findViewById<FloatingActionButton>(R.id.fbtn)

        fbtn.setOnClickListener {
            val nowItemIndex = arraylist[1]
            nowItemIndex.title = "Apple z0"
            myAdapter.notifyItemChanged(1)
        }

        retrofitData.enqueue(object : Callback<MyData?> {

            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                var responseBody = response.body()
                val productList = responseBody?.products!!
                arraylist.addAll(productList)

                arraylist.add(Product("www.google.com","Zero ZA12"))
                myAdapter.notifyDataSetChanged()

//                delay(5000)
//                val nowItemIndex = arraylist[2]
//                nowItemIndex.title = "Apple 00"


//                myAdapter.notifyItemChanged(arraylist.size)
//                myAdapter.run {
//                    Log.e("notifyDataSetChanged=>","-->"+ notifyDataSetChanged().toString())
//                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("Main Activity", "OnFailure" + t.message)
            }
        })

    }
}