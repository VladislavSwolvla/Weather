package startandroid.ru.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter(context: Context, private val items: List<String>) : ArrayAdapter<String>(context, 0, items) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(android.R.layout.simple_spinner_item, parent, false)

        val item = getItem(position)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        val item = getItem(position)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item

        return view
    }
}