package com.example.freekino.presentation
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.freekino.R
import com.example.freekino.domain.models.VideoBasicInfo
import com.squareup.picasso.Picasso


internal class MainAdapter(
    private val context: Context,
    private val videos: MutableList<VideoBasicInfo?>
): BaseAdapter(){
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun getCount(): Int {
      return videos.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?{
        var convertView = convertView
        if (layoutInflater == null) {
         layoutInflater =
         context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
         convertView = layoutInflater!!.inflate(R.layout.row_item, null)
        }
        imageView = convertView!!.findViewById(R.id.imageView)
        textView = convertView.findViewById(R.id.textView)
        Picasso.get().load(videos[position]!!.img).into(imageView)

        if (videos[position]!!.name.length < 16) {
            textView.text = videos[position]!!.name + "(" + videos[position]!!.year + ")"
        }
        else{
            val str = videos[position]!!.name.subSequence(0, 15).toString()
            textView.text = str + "...(" + videos[position]!!.year + ")"
        }

        return convertView
    }
}
