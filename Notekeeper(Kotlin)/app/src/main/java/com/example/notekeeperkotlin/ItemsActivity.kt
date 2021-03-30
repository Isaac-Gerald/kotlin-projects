package com.example.notekeeperkotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jwhh.notekeeper.CourseRecyclerAdapter
import com.jwhh.notekeeper.ReminderNotification
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.content_note_list.*


class ItemsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NoteRecyclerAdapter.OnNoteSelectedListener {
    private val tag = this::class.simpleName
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val noteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, DataManager.notes)
        adapter.setNoteSelectedListener(this)
        adapter
    }

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)

    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, resources.getInteger(R.integer.course_grid_span))
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    private val recentlyViewNoteAdapter by lazy {

        val adapter = NoteRecyclerAdapter(this, viewModel.recentlyViewedNotes)
        adapter.setNoteSelectedListener(this)
        adapter
    }


    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ItemsActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        // val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        val toogle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.open_navigation,
            R.string.close_navigation
        )
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        if (viewModel.isNewlyCreated && savedInstanceState != null)
            viewModel.restoreState(savedInstanceState)
        viewModel.isNewlyCreated = false

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)



        nav_view.setNavigationItemSelectedListener(this)

        registerNotificationChannel()

    }

    private fun registerNotificationChannel() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
           val notificationChannel = NotificationChannel(
               ReminderNotification.REMINDER_CHANNEL,
               "Note Reminders", NotificationManager.IMPORTANCE_HIGH
           )
           nm.createNotificationChannel(notificationChannel)
       }
    }

    private fun handleDisplaySelection(itemId: Int) {
        when (itemId) {
            R.id.action_notes -> {
                displayNotes()
            }
            R.id.action_courses -> {
                displayCourses()
            }
            R.id.action_recently_viewed -> {
                displayRecentlyViewedNotes()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)

    }

    private fun displayRecentlyViewedNotes() {
//        Log.d(tag, "displayRecentlyViewedNotes: ${recentlyViewedNotes[0]}]")
        listNotes.layoutManager = noteLayoutManager
        listNotes.adapter = recentlyViewNoteAdapter



        nav_view.menu.findItem(R.id.action_recently_viewed).isCheckable = true

    }

    private fun displayCourses() {
        listNotes.layoutManager = courseLayoutManager
        listNotes.adapter = courseRecyclerAdapter

        nav_view.menu.findItem(R.id.action_courses).isCheckable = true
    }

    private fun displayNotes() {
        listNotes.layoutManager = noteLayoutManager
        listNotes.adapter = noteRecyclerAdapter

        nav_view.menu.findItem(R.id.action_notes).isCheckable = true
    }

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        listNotes.adapter?.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_notes,
            R.id.action_courses,
            R.id.action_recently_viewed -> {
                handleDisplaySelection(item.itemId)
                viewModel.navDrawerDisplaySelection = item.itemId
            }

            R.id.action_share -> {
                handleSelection(R.string.nav_share_message)
            }
            R.id.action_send -> {
                handleSelection(R.string.nav_send)
            }
            R.id.action_how_many -> {
                val message = getString(
                    R.string.nav_how_many_message_format,
                    DataManager.notes.size, DataManager.courses.size
                )
                Snackbar.make(listNotes, message, Snackbar.LENGTH_LONG).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleSelection(stringId: Int) {

        Snackbar.make(listNotes, stringId, Snackbar.LENGTH_LONG).show()
//        val intent = Intent(this, NoteActivity::class.java)
//        startActivity(intent)
//        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun onNoteSelected(note: NoteInfo) {
        Log.d(tag, "onNoteSelected: $note")
        viewModel.addToRecentlyViewedNotes(note)
    }


}