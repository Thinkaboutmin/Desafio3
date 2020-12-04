package com.trab.desafio3.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.trab.desafio3.R
import com.trab.desafio3.models.Dates
import kotlinx.android.synthetic.main.fragment_hq.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class HQFragment : Fragment() {
    val args: HQFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Eventos de clicks
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // Define os dados mostrados na tela
        tvHQTitle.text = args.comic.title.toUpperCase()

        tvHQDesc.text = args.comic.description

        var datePub: Dates? = null
        for (date in args.comic.dates) {
            if (date.type == "focDate") {
                datePub = date
                break
            }
        }

        val hqDate = HtmlCompat.fromHtml(if (datePub != null) {
            val dateFormated = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(datePub.date)
            if (dateFormated != null) {
                SimpleDateFormat("LLLL d, Y", Locale.US).format(dateFormated).toString()
            } else {
                "None"
            }
        } else {
            "None"
        }, HtmlCompat.FROM_HTML_MODE_COMPACT)

        tvHQPub.text = HtmlCompat.fromHtml(
            resources.getString(R.string.hq_published, hqDate),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )


        if (args.comic.prices.isNotEmpty()) {
            tvHQPrice.text = HtmlCompat.fromHtml(resources.getString(
                R.string.hq_price,
                args.comic.prices[0].price.toString()
            ), HtmlCompat.FROM_HTML_MODE_COMPACT)
        }

        tvHQPages.text = HtmlCompat.fromHtml(
            resources.getString(R.string.hq_pages, args.comic.pageCount.toString()),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        val image = args.comic.images[0]
        Picasso.get().load("${image.path}.${image.extension}").into(imgHQCover)

        imgHQCover.setOnClickListener {
            val nav = HQFragmentDirections.actionHQFragmentToHQCoverFullFragment(
                    "${image.path}.${image.extension}"
            )
            findNavController().navigate(nav)
        }
    }
}