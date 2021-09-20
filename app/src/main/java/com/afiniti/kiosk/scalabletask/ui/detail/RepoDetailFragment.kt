package com.afiniti.kiosk.scalabletask.ui.detail

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afiniti.kiosk.scalabletask.databinding.RepoDetailFragmentBinding
import androidx.navigation.fragment.navArgs
import com.afiniti.kiosk.scalabletask.di.ViewModelFactory
import com.afiniti.kiosk.scalabletask.model.CommitListModel
import dagger.android.support.AndroidSupportInjection
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.concurrent.fixedRateTimer


class RepoDetailFragment : Fragment() {

    private var maxCommitsCount: Int = -1

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mViewModel: RepoDetailViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var mBinding: RepoDetailFragmentBinding
    private val navArgs: RepoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = RepoDetailFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.fetchCommits(navArgs.repoName)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.progressRepos.visibility = View.VISIBLE
        setUpObserver()
    }

    private fun setUpUi(commitsList: List<Pair<String, Int>>) {
        var index = 0
        var barHeight: Float
        var percentageAsPerMax = -1
        var initialHeight = -1f
        mBinding.cvBar.setScreenHeight(getDeviceHeight().toFloat())
        fixedRateTimer("timer", false, 0, 2000) {
            activity?.runOnUiThread {
                if (index < commitsList.size) {
                    percentageAsPerMax = (commitsList[index].second * 100) / maxCommitsCount
                    initialHeight = (((1000) / 100) * percentageAsPerMax).toFloat()
                    barHeight = (1000) - initialHeight
                    mBinding.cvBar.setBarHeight(barHeight)
                    mBinding.tvMonth.text = commitsList[index].first
                    mBinding.tvCommitCount.text = "${commitsList[index].second} Commits"
                    index++
                } else {
                    index = 0
                }
            }
        }
    }

    private fun setUpObserver() {
        mViewModel.repoCommitsLiveData.observe(requireActivity()) {
            mBinding.progressRepos.visibility = View.GONE

            if (it != null) {
                setUpUi(countCommitsPerMonth(it).toList())
            }
        }
    }

    private fun countCommitsPerMonth(commitsList: List<CommitListModel>): HashMap<String, Int> {
        val commitCountHashMap = HashMap<String, Int>()
        var count: Int
        var month: String

        for (item in commitsList) {
            month = getMonthFromDate(item.commit.author.date)
            if (month.isEmpty().not()) {
                if (commitCountHashMap.containsKey(month)) {
                    count = commitCountHashMap.get(month)!!
                    commitCountHashMap.put(month, ++count)
                } else {
                    commitCountHashMap.put(month, 1)
                }
            }
        }
        filterMaximumCommitCount(commitCountHashMap)
        return commitCountHashMap
    }

    private fun filterMaximumCommitCount(commitCountHashMap: HashMap<String, Int>) {
        val maxValue = commitCountHashMap.values.maxOrNull()
        maxCommitsCount = maxValue ?: 10
    }

    private fun getMonthFromDate(originalDate: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
            val convertedDate: Date? = format.parse(originalDate)
            return DateFormat.format("MMMM", convertedDate) as String
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getDeviceHeight(): Int {
        val displayManager = activity?.getSystemService<DisplayManager>()!!
        val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
        return defaultDisplay.height
    }

}