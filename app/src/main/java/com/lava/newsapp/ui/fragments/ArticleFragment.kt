package com.lava.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.lava.newsapp.R
import com.lava.newsapp.db.ArticleDatabase
import com.lava.newsapp.repository.NewsRepository
import com.lava.newsapp.ui.NewsActivity
import com.lava.newsapp.ui.NewsViewModel
import com.lava.newsapp.ui.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = (activity as NewsActivity).viewModel
        val application = requireNotNull(this.activity).application
        val newsRepository = NewsRepository(ArticleDatabase(requireContext()))
        val vmProviderFactory = NewsViewModelProviderFactory(application,newsRepository)
        viewModel = ViewModelProvider(this, vmProviderFactory)[NewsViewModel::class.java]

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}