package global.msnthrp.langen.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.MobileAds
import global.msnthrp.langen.R
import global.msnthrp.langen.ui.base.BaseActivity
import global.msnthrp.langen.ui.create.CreateLanguageActivity
import global.msnthrp.langen.ui.details.LanguageDetailsActivity
import global.msnthrp.langen.ui.main.model.LanguagePreview
import global.msnthrp.langen.ui.utils.setBottomInsetMargin
import global.msnthrp.langen.ui.utils.setBottomInsetPadding
import global.msnthrp.langen.ui.utils.setVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    // Icons made by <a href="https://www.flaticon.com/authors/kirill-kazachek" title="Kirill Kazachek">Kirill Kazachek</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

    private val viewModel by viewModels<MainViewModel>()
    private val adapter by lazy {
        LanguagesAdapter(this, ::onClick)
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setTitle("")
        fabAdd.setOnClickListener {
            CreateLanguageActivity.launch(this)
        }
        rvLanguages.layoutManager = LinearLayoutManager(this)
        rvLanguages.adapter = adapter
        fabAdd.setBottomInsetMargin(resources.getDimensionPixelSize(R.dimen.fab_margin))
        rvLanguages.setBottomInsetPadding()

        viewModel.languages.observe(this, Observer(::onLanguagesLoaded))

        MobileAds.initialize(this) {}
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLanguages()
    }

    private fun onLanguagesLoaded(languages: List<LanguagePreview>) {
        adapter.update(languages)
        tvStartHint.setVisible(languages.isEmpty())
    }

    private fun onClick(languagePreview: LanguagePreview) {
        LanguageDetailsActivity.launch(this, languagePreview.language)
    }
}