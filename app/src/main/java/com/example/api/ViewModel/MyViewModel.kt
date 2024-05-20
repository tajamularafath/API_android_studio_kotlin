package com.example.api.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ApiInterface
import com.example.api.MyData
import com.example.api.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MyViewModel : ViewModel() {

    val retrofitBuilder = Retrofit.Builder()
    .baseUrl("https://dummyjson.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val retrofitData = retrofitBuilder.create(ApiInterface::class.java)

    val myLiveData = MutableLiveData<List<Product>?>()

    val liveGetData : MutableLiveData<List<Product>?>
        get() = myLiveData

    // Function to fetch data from the API
    fun fetchData(){

        val call = retrofitData.getProductData()

        call.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                if(response.isSuccessful){
                    val data = response.body()?.products
                    liveGetData.postValue(data)
                    Log.d("Response Success!!", "Success"+data.toString() )
                } else{
                Log.d("Response not found!!", "Success" )
                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("API Network Error=>", "OnFailure" + t.message)

            }
        })


//        viewModelScope.launch(Dispatchers.IO){
//            try {
//                val response = retrofitData.getProductData()
//                myLiveData.postValue(response)
//            } catch (e:Exception){
//                Log.d("Main Activity", "OnFailure" + e.message)
//            }
//        }
    }



}