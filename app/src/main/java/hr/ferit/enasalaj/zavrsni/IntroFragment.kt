package hr.ferit.enasalaj.zavrsni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.intro, container, false)
        val imageView: ImageView = view.findViewById(R.id.introImage)
        imageView.setImageResource(R.drawable.image_name)
        return view;
    }

}