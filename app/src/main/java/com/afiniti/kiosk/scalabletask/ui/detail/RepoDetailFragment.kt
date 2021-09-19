package com.afiniti.kiosk.scalabletask.ui.detail

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.text.format.DateFormat
import android.util.DisplayMetrics
import android.view.*
import androidx.core.content.ContextCompat.getSystemService
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

        var screenHeight = getScreenDimension()

        println("Screen Height  -> " + screenHeight.toFloat())

        fixedRateTimer("timer", false, 0, 2000) {
            activity?.runOnUiThread {
                if (index < commitsList.size) {
//                    barHeight = if (commitsList[index].second < 15)
//                        (screenHeight - (commitsList[index].second * 100)).toFloat()
//                    else
//                        (screenHeight - ((commitsList[index].second * 100) / 10)).toFloat()
                    var commitsPercentage = 0f
                    var screenSize = 0f
                    if (commitsList[index].second > 100) {
                        commitsPercentage = (commitsList[index].second.toFloat() / 100f).toFloat()
                        screenSize = (screenHeight.toFloat() / 100f).toFloat()
                    } else {
                        commitsPercentage = commitsList[index].second.toFloat()
                        screenSize =  screenHeight.toFloat() - 10f
                    }

                    barHeight = (screenSize.toFloat() * commitsPercentage).toFloat()

                    mBinding.cvBar.setBarHeight(barHeight)
                    mBinding.cvBar.setScreenHeight(screenHeight.toFloat())

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
        commitCountHashMap.clear()
        commitCountHashMap.put("Aug", 750)
        commitCountHashMap.put("Dec", 20)
        commitCountHashMap.put("Jan", 350)
        commitCountHashMap.put("Nov", 150)
        commitCountHashMap.put("Feb", 1)
        return commitCountHashMap
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

    private fun getScreenDimension(): Int {
        val displayManager = activity?.getSystemService<DisplayManager>()!!
        val defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
        return defaultDisplay.height
    }

}