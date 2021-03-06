package jp.org.example.geckour.glyph.fragment

import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import io.realm.Realm
import jp.org.example.geckour.glyph.App
import jp.org.example.geckour.glyph.App.Companion.coda
import jp.org.example.geckour.glyph.R
import jp.org.example.geckour.glyph.activity.MainActivity
import jp.org.example.geckour.glyph.activity.PrefActivity
import jp.org.example.geckour.glyph.databinding.FragmentMainBinding
import jp.org.example.geckour.glyph.db.DBInitialData
import jp.org.example.geckour.glyph.db.model.Sequence
import jp.org.example.geckour.glyph.db.model.Shaper as DBShaper
import jp.org.example.geckour.glyph.fragment.model.Result
import jp.org.example.geckour.glyph.util.*
import jp.org.example.geckour.glyph.view.AnimateView
import jp.org.example.geckour.glyph.view.model.Shaper
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import timber.log.Timber

class MainFragment: Fragment() {

    companion object {
        val tag: String = MainFragment::class.java.simpleName

        fun newInstance(): MainFragment = MainFragment()

        private val STATE_ARGS_LEVEL = "level"
        private val STATE_ARGS_QUESTIONS = "questions"
    }

    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    private lateinit var binding: FragmentMainBinding
    private lateinit var realm: Realm
    private val sp: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(activity) }

    private var min = 0
    private var max = 8
    private var level = 0

    private var gameMode: Int = 0
    private var doVibrate: Boolean = false

    private val commandMaster: List<Shaper> by lazy {
        realm.where(DBShaper::class.java)
                .equalTo("name", DBInitialData.Shaper.COMPLEX.displayName)
                .or().equalTo("name", DBInitialData.Shaper.SIMPLE.displayName)
                .or().equalTo("name", DBInitialData.Shaper.MORE.displayName)
                .or().equalTo("name", DBInitialData.Shaper.LESS.displayName)
                .findAll()
                .toList()
                .map { it.parse() }
    }

    private val questions: ArrayList<Shaper> = ArrayList()
    private val throughDots: ArrayList<Int> = ArrayList()
    private val paths: MutableList<List<Pair<Int, Int>>> = ArrayList()
    private val spentTimes: ArrayList<Long> = ArrayList()

    private var fromX = -1f
    private var fromY = -1f

    private val onTimeUpForCommand: () -> Unit = {
        if (getTouchStatus() == MotionEvent.ACTION_UP) {
            hideDialog()
            binding.animateView.clearParticle()
            binding.dotsView.setDotsState { false }
            showSequence {
                binding.animateView.apply {
                    clearParticle()
                    setGrainAlphaModeIntoPrepareInput()
                }
            }
        }
    }

    private val jobs: ArrayList<Job> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()

        val t: Tracker? = (activity.application as App).getDefaultTracker()
        t?.setScreenName(tag)
        t?.send(HitBuilders.ScreenViewBuilder().build())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) {
            try {
                min = if (sp.contains(PrefActivity.Key.LEVEL_MIN.name)) sp.getInt(PrefActivity.Key.LEVEL_MIN.name, 0) else 0
                Timber.d("min: $min")
            } catch (e: Exception) {
                Timber.e("Can't translate minimum-level to Int.")
            }

            try {
                max = if (sp.contains(PrefActivity.Key.LEVEL_MAX.name)) sp.getInt(PrefActivity.Key.LEVEL_MAX.name, 8) else 8
                Timber.d("max: $max")
            } catch (e: Exception) {
                Timber.e("Can't translate maximum-level to Int.")
            }

            try {
                gameMode = if (sp.contains(PrefActivity.Key.GAME_MODE.name)) sp.getInt(PrefActivity.Key.GAME_MODE.name, 0) else 0
                Timber.d("gameMode: $gameMode")
            } catch (e: Exception) {
                Timber.e("Can't translate game mode to Int.")
            }

            doVibrate = sp.contains(PrefActivity.Key.VIBRATE.name) && sp.getBoolean(PrefActivity.Key.VIBRATE.name, false)
            Timber.d("doVibrate: $doVibrate")

            questions.apply {
                clear()
                addAll(getSequence(mainActivity.sequenceId))
            }
        } else {
            level = savedInstanceState.getInt(STATE_ARGS_LEVEL)
            questions.apply {
                clear()
                addAll(savedInstanceState.getParcelableArrayList(STATE_ARGS_QUESTIONS))
            }
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideLeftButton()
        setRightButton("NEXT") { mainActivity.onNext() }

        binding.animateView.resetInitTime()

        binding.animateView.setOnTouchListener { _, event ->
            when (binding.animateView.getInputState()) {
                AnimateView.InputState.DISABLED -> false

                AnimateView.InputState.ENABLED -> {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                            fromX = event.x
                            fromY = event.y
                            binding.animateView.clearParticle()
                            throughDots.clear()
                            binding.dotsView.setDotsState { false }
                            binding.animateView.setGrainAlphaModeIntoInput(Pair(paths.size + 1, level.getDifficulty()))
                            binding.animateView.addParticle(event.x, event.y)
                            true
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val collision = binding.dotsView.getCollision(fromX, fromY, event.x, event.y) {
                                if (doVibrate && (throughDots.isEmpty() || it.count { it != throughDots.last() } > 0)) vibrate()
                            }
                            throughDots.addAll(collision)
                            binding.dotsView.setDotsState(collision.map { Pair(it, true) })
                            binding.animateView.addParticle(event.x, event.y)
                            fromX = event.x
                            fromY = event.y
                            true
                        }

                        MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                            addCurrentSpentTime()

                            val collision = binding.dotsView.getCollision(fromX, fromY, event.x, event.y) {
                                if (doVibrate && (throughDots.isEmpty() || it.count { it != throughDots.last() } > 0)) vibrate()
                            }
                            throughDots.addAll(collision)
                            binding.dotsView.setDotsState(collision.map { Pair(it, true) })
                            paths.add(throughDots.convertDotsListToPaths().getNormalizedPaths())

                            setLeftButton("REDO") {
                                paths.removeAt(paths.lastIndex)
                                binding.dotsView.setDotsState { false }
                                binding.animateView.clearParticle()
                                binding.animateView.setGrainAlphaModeIntoInput(Pair(paths.size, level.getDifficulty()))

                                if (paths.isEmpty()) hideLeftButton()
                            }

                            binding.animateView.apply {
                                setGrainAlphaModeIntoFadeout { binding.dotsView.setDotsState { false } }
                                showPaths(paths.last().mapToPointPathsFromDotPaths(binding.dotsView.getDots()))
                            }
                            if (paths.size >= level.getDifficulty()) binding.animateView.setGrainAlphaModeIntoPrepareAnswer()
                            fromX = -1f
                            fromY = -1f
                            true
                        }
                        else -> true
                    }
                }

                AnimateView.InputState.COMMAND -> {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                            fromX = event.x
                            fromY = event.y
                            binding.animateView.clearParticle()
                            throughDots.clear()
                            binding.dotsView.setDotsState { false }
                            hideDialog()
                            binding.animateView.addParticle(event.x, event.y)
                            true
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val collision = binding.dotsView.getCollision(fromX, fromY, event.x, event.y) {
                                if (doVibrate && (throughDots.isEmpty() || it.count { it != throughDots.last() } > 0)) vibrate()
                            }
                            throughDots.addAll(collision)
                            binding.dotsView.setDotsState(collision.map { Pair(it, true) })
                            binding.animateView.addParticle(event.x, event.y)
                            fromX = event.x
                            fromY = event.y
                            true
                        }

                        MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                            val collision = binding.dotsView.getCollision(fromX, fromY, event.x, event.y) {
                                if (doVibrate && (throughDots.isEmpty() || it.count { it != throughDots.last() } > 0)) vibrate()
                            }
                            throughDots.addAll(collision)
                            binding.dotsView.setDotsState(collision.map { Pair(it, true) })

                            val path = throughDots.convertDotsListToPaths().getNormalizedPaths()
                            binding.animateView.showPaths(path.mapToPointPathsFromDotPaths(binding.dotsView.getDots()))
                            commandMaster.forEach {
                                if (it.match(path)) {
                                    val command = DBInitialData.Shaper.valueOf(it.name)
                                    binding.animateView.setCommand(command)
                                    showDialog(command)
                                }
                            }
                            binding.animateView.setGrainAlphaModeIntoWaitCommand(onTimeUpForCommand)

                            fromX = -1f
                            fromY = -1f
                            true
                        }

                        else -> true
                    }
                }
            }
        }
        binding.animateView.setGrainAlphaModeIntoWaitCommand(onTimeUpForCommand)
        showDialog("COMMAND CHANNEL OPEN…")
    }

    private fun getTouchStatus(): Int =
            when {
                fromX > 0f || fromY > 0f -> MotionEvent.ACTION_DOWN
                else -> MotionEvent.ACTION_UP
            }

    override fun onResume() {
        super.onResume()

        paths.clear()
        spentTimes.clear()
    }

    override fun onDestroy() {
        super.onDestroy()

        realm.close()
        clearJobs(jobs)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.apply {
            putInt(STATE_ARGS_LEVEL, level)
            putParcelableArrayList(STATE_ARGS_QUESTIONS, ArrayList(questions))
        }
    }

    private fun getAllowableTime(level: Int): Long =
            when (level) {
                in 0..2 -> 20000L
                in 3..8 -> 20000L - 1000L * (level - 2)
                else -> 0L
            }

    private fun setRightButton(buttonText: String, predicate: (View) -> Unit) {
        binding.buttonRight.apply {
            text = buttonText
            setOnClickListener { predicate(it) }
            visibility = View.VISIBLE
            typeface = coda
        }
    }

    private fun hideRightButton() {
        binding.buttonRight.apply {
            visibility = View.INVISIBLE
            setOnClickListener(null)
        }
    }

    private fun setLeftButton(buttonText: String, predicate: (View) -> Unit) {
        binding.buttonLeft.apply {
            text = buttonText
            setOnClickListener { predicate(it) }
            visibility = View.VISIBLE
            typeface = coda
        }
    }

    private fun hideLeftButton() {
        binding.buttonLeft.apply {
            visibility = View.INVISIBLE
            setOnClickListener(null)
        }
    }

    private fun showSequence(questions: List<Shaper> = this.questions, onComplete: () -> Unit = {}) {
        if (questions.isNotEmpty()) {
            val difficulty = level.getDifficulty()
            binding.animateView
                    .setGrainAlphaModeIntoQuestion(
                            Pair(difficulty + 1 - questions.size, difficulty),
                            getAllowableTime(level),
                            onStartNextQ = { showSequence(questions.drop(1), onComplete) },
                            onStartInput = {
                                setRightButton("BYPASS") {
                                    binding.animateView.setGrainAlphaModeIntoPrepareAnswer()
                                }
                            },
                            onPrepareAnswer = {
                                hideLeftButton()
                                hideRightButton()
                                binding.dotsView.visibility = View.INVISIBLE
                            },
                            onTransitionToCheckAnswer = { checkAnswer() }
                    )
            showShaper(questions.first())
        } else onComplete()
    }

    private fun showShaper(shaper: Shaper) =
        binding.animateView.apply {
            if (gameMode != 2) setShaperName(listOf(shaper.name))
            if (gameMode != 1) {
                showPaths(
                        shaper.dots.convertDotsListToPaths().getNormalizedPaths().mapToPointPathsFromDotPaths(binding.dotsView.getDots())
                ).apply { Timber.d("showing shaper id: ${shaper.id}, name: ${shaper.name}, dots: ${shaper.dots}") }
            }
        }

    private fun getSequence(id: Long? = null, mode: MainActivity.Mode? = null, level: Int? = null): List<Shaper> {
        fun getLevel(): Int = ((max - min + 1) * Math.random() + min).toInt()

        this.level = level ?: mainActivity.level ?: getLevel().apply { Timber.d("level: $this") }
        val difficulty = this.level.getDifficulty().apply { Timber.d("difficulty: $this") }
        mainActivity.level = this.level

        return when (mode ?: mainActivity.getMode()) {
            MainActivity.Mode.NORMAL -> {
                when (difficulty) {
                    1 -> {
                        realm.where(DBShaper::class.java).count().let {
                            realm.where(DBShaper::class.java)
                                    .findAll()
                                    .toList()
                                    .map { it.parse() }
                                    .let {
                                        listOf(it[id?.toInt() ?: (Math.random() * it.size).toInt().apply { mainActivity.sequenceId = this.toLong() }])
                                    }
                        }
                    }

                    in 2..5 -> {
                        realm.where(Sequence::class.java)
                                .equalTo("size", difficulty)
                                .findAll()
                                .toList()
                                .map { it.message.toList().map { it.parse() } }
                                .let {
                                    it[id?.toInt() ?: (Math.random() * it.size).toInt().apply { mainActivity.sequenceId = this.toLong() }]
                                }
                    }

                    else -> listOf()
                }
            }

            MainActivity.Mode.WEAKNESS -> {
                when (difficulty) {
                    1 -> {
                        realm.where(DBShaper::class.java).let {
                            if (id == null) {
                                it.greaterThan("examCount", 0).let {
                                    val size = it.count().toInt()
                                    if (size > 0) {
                                        it.findAll().toList()
                                                .sortedBy { it.correctCount.toDouble() / it.examCount }
                                                .dropLast(size / 4)
                                                .map { it.parse() }
                                                .let {
                                                    listOf(it[(Math.random() * it.size).toInt().apply { mainActivity.sequenceId = this.toLong() }])
                                                }
                                    } else getSequence(id = id, mode = MainActivity.Mode.NORMAL, level = this@MainFragment.level)
                                }
                            } else {
                                listOf(it.findAll().map { it.parse() }[id.toInt()])
                            }
                        }
                    }

                    in 2..5 -> {
                        realm.where(Sequence::class.java)
                                .equalTo("size", difficulty)
                                .let {
                                    if (id == null) {
                                        it.greaterThan("examCount", 0).let { sequences ->
                                            val size = sequences.count().toInt()
                                            if (size > 0) {
                                                sequences.findAll().toList()
                                                        .sortedBy { it.correctCount.toDouble() / it.examCount }
                                                        .dropLast(size / 4)
                                                        .map { it.message.toList().map { it.parse() } }
                                                        .let {
                                                            it[(Math.random() * it.size).toInt().apply { mainActivity.sequenceId = this.toLong() }]
                                                        }
                                            } else getSequence(id = id, mode = MainActivity.Mode.NORMAL, level = this@MainFragment.level)
                                        }
                                    } else {
                                        it.findAll().map { it.message.toList().map { it.parse() } }[id.toInt()]
                                    }
                                }

                    }

                    else -> listOf()
                }
            }
        }
    }

    private fun addCurrentSpentTime() = spentTimes.add(System.currentTimeMillis() - binding.animateView.getInputStartTime() - spentTimes.sum())

    private fun checkAnswer() =
            mainActivity.transitionForCheckAnswer(
                    questions.mapIndexed { i, q ->
                        if (i < paths.size) Result(q.id, q.match(paths[i]), spentTimes[i])
                        else Result(q.id, false, 0L)
                    },
                    getAllowableTime(level)
            )

    private fun showDialog(command: DBInitialData.Shaper) {
        val message =
                when (command) {
                    DBInitialData.Shaper.COMPLEX -> "COMPLEX HACK"
                    DBInitialData.Shaper.SIMPLE -> "SIMPLE HACK"
                    DBInitialData.Shaper.MORE -> "REQUEST KEY"
                    DBInitialData.Shaper.LESS -> "NO KEY"
                    else -> ""
                }

        if (message.isNotBlank()) showDialog(message)
    }

    private fun showDialog(message: String) {
        if (message.isNotBlank()) {
            binding.dialog.apply {
                val id: Long = (tag as? Long)?.plus(1) ?: 0
                tag = id
                text = message
                visibility = View.VISIBLE

                ui(jobs) {
                    delay(1000)
                    if ((tag as? Long) == id) hideDialog()
                }
            }
        }
    }

    private fun hideDialog() {
        binding.dialog.visibility = View.GONE
        if (getTouchStatus() != MotionEvent.ACTION_DOWN) {
            binding.dotsView.setDotsState { false }
            binding.animateView.clearParticle()
        }
    }
}