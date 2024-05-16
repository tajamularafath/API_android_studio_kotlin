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

            if(arraylist.size > 2){
            arraylist.remove(arraylist[2])
            myAdapter.notifyItemRemoved(2)

            } else{
                Log.d("ArrayList=>", "ArrayList size is less than 3, cannot remove item at index 2");
            }
//            Adding By Index / notifyItemInserted
            val newProduct = Product("Hero Phone","Hero Phone")
            arraylist.add(0,newProduct)
            myAdapter.notifyItemInserted(0)

//            notifyItemRangeRemoved
            if (arraylist.size>3) {
                arraylist.remove(arraylist[0])
                arraylist.remove(arraylist[1])
                arraylist.remove(arraylist[2])
                myAdapter.notifyItemRangeRemoved(0, 3)
            } else{
                Log.d("notifyItemRangeRemoved=>", "ArrayList size is less than 3, cannot remove item at index 2");

            }

//            adding 3 list and notifying the adapter using notifyItemRangeInserted
            val newProduct1 = Product("Hero 1","Hero 1")
            val newProduct2 = Product("Hero 2","Hero 2")
            val newProduct3 = Product("Hero 3","Hero 3")
            arraylist.add(0,newProduct1)
            arraylist.add(1,newProduct2)
            arraylist.add(2,newProduct3)
            myAdapter.notifyItemRangeInserted(0,3)
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