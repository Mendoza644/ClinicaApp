package com.example.clinicalasalud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clinicalasalud.models.Dossiers
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MainActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null
    private val dossiersArrayList = ArrayList<Dossiers>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.rv_logs)
        layoutManager = LinearLayoutManager(this)
        getDataFromDatabase()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, CreateLog::class.java)
            startActivity(intent)
        }
    }

    private fun getDataFromDatabase() {
        dossiersArrayList.clear()
        firebaseFirestore!!.collection("Dossiers")
                .get()
                .addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result!!) {
                            val dossiers = documentSnapshot.toObject(Dossiers::class.java)
                            dossiersArrayList.add(dossiers)
                        }
                        inflateData(dossiersArrayList)
                    } else {
                        Log.w("DocumentError", "Error getting docs", task.exception)
                    }
                }
    }

    private fun inflateData(arrayList: ArrayList<Dossiers>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        mAdapter = Adapter(arrayList, this)
        recyclerView.adapter = mAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_log_out) {
            firebaseAuth!!.signOut()
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
            return true
        } else if (item.itemId == R.id.action_refresh) {
            dossiersArrayList.clear()
            getDataFromDatabase()
        }
        return super.onOptionsItemSelected(item)
    }
}