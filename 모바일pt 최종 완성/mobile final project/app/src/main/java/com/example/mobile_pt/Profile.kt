package com.example.mobile_pt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.profilelayout.*
import org.jetbrains.anko.startActivity


class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profilelayout)

        val secondIntent = intent
        val uid = FirebaseAuth.getInstance().uid

        val name =secondIntent.getStringExtra("name")
        val address =secondIntent.getStringExtra("address")
        val dinner =secondIntent.getStringExtra("dinner")

        adressview.setText(address)
        namebox.setText(name)

        request.setOnClickListener {


            FirebaseDatabase.getInstance().getReference("/users/$uid").child("trainerlist") .setValue(name)
            startActivity<New_Main2Activity>()

        }



    }
}