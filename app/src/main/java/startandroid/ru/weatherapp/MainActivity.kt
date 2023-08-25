package startandroid.ru.weatherapp


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject
import startandroid.ru.weatherapp.databinding.ActivityMainBinding
import java.net.URL

class MainActivity : AppCompatActivity() {
    var num: Int? = 0
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        var names: ArrayList<String> = arrayListOf()
        db.getDao().getAllItem().asLiveData().observe(this){
            binding.tv.text = ""
            names.clear()
            for(item in it){
                names.add(item.name)
            }
            binding.tv.text = it[num!!].temp + "°"
            binding.tv2.text = it[num!!].text
            val adapter = SpinnerAdapter(this, names)
            binding.spinner.adapter = adapter
            binding.spinner.setSelection(num!!)
        }

        binding.spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(

                parent: AdapterView<*>?,

                view: View?,

                position: Int,

                id: Long

            ) {

                num = position
                var city = names[position]

                val key: String = "7dd133b61b819d04cfa2aaa0603aba36"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

                GlobalScope.async(Dispatchers.Default) {
                    val apiResponse = URL(url).readText()

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")

                    val name = JSONObject(apiResponse).getString("name")

                    var info = Item(null, name, temp, desc)




                    Thread {
                        var names: ArrayList<String> = arrayListOf()
                        var list = db.getDao().getItems()
                        list.forEach {
                            names.add(it.name)
                        }
                        if(name in names) db.getDao().update(name,temp,desc)
                        else {db.getDao().insertItem(info)
                        }

                    }.start()

                }

            }



            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

        }



        


        var city = binding.edit.text
        binding.button.setOnClickListener {
                binding.edit.text = null
                val key: String = "7dd133b61b819d04cfa2aaa0603aba36"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

                GlobalScope.async(Dispatchers.Default) {
                    val apiResponse = URL(url).readText()

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")

                    val name = JSONObject(apiResponse).getString("name")

                    var info = Item(null, name, temp, desc)



                    Thread {
                        var count = 0
                        var names: ArrayList<String> = arrayListOf()
                        var list = db.getDao().getItems()
                        list.forEach {
                            names.add(it.name)
                            count++
                        }
                        num = count
                        if(name in names) db.getDao().update(name,temp,desc)
                        else {db.getDao().insertItem(info)
                        }
                    }.start()


                }



        }


    }






}