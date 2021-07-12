package com.example.mobile_pt



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity

import com.google.firebase.database.DataSnapshot
import com.naver.maps.geometry.LatLng
import android.R.attr.data



internal interface DataReceivedListener {
    fun onDataReceived(data: List<trainerdata>)
}


class TrainerList : AppCompatActivity() {


    val trainerlist2 = arrayListOf<trainerdata>(
    )
    val myinfor = arrayListOf<trainerdata>(
    )

    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")
    lateinit var mrecy: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trainerlist)
        mapbutton.setOnClickListener{ // 회원 찾으러 이동
            startActivity<Start_Activity1>()

        }

        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("/users/$uid")

        mrecy = findViewById(R.id.rv)
        val lm = LinearLayoutManager(this)
        mrecy.layoutManager = lm


        mrecy.setHasFixedSize(true)
        val madapter = traineradapter(this, trainerlist2) { trainerList ->
            startActivity<Profile>(

                "name" to trainerList.username,
                "address" to trainerList.address,
                "dinner" to trainerList.uid,
                "distance" to trainerList.distance!!.toInt().toString()+"m"
            )

        }


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val myinf = dataSnapshot.getValue(trainerdata::class.java)!!

                myinfor.add(myinf)

                dataRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot2: DataSnapshot) {
                        trainerlist2.clear()
                        for (dataSnapshot3 in dataSnapshot2.children) { //하위노드가 없을 떄까지 반복
                            val trainerdats = dataSnapshot3.getValue(trainerdata::class.java)!!

                            trainerlist2.add(trainerdats)

                        }
                        Log.d("TAG", "SSSSSSSssssssss"+trainerlist2)
                        comparison(myinfor,trainerlist2)
                        madapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(p0: DatabaseError) {
                    }
                })


            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

        mrecy.adapter = madapter


    }
    fun comparison (array : ArrayList<trainerdata> , array2 : ArrayList<trainerdata>){

        var sizes =array2.size
        for(i  in 0..(sizes-1)){

            array2[i].distance = LatLng(array[0].x!!,array[0].y!!).distanceTo(LatLng(array2[i].x!!,array2[i].y!!))

        }
        for(i in 0..(sizes-1)){
            if(array2[i].distance == 0.0){

                array2.removeAt(i)
                sizes--
                break;
            }

        }


        for (i in 0 .. (sizes- 1)) {

            for (j in 0 .. (sizes- 1)) {

                if (array2[i].distance!!< array2[j].distance!!) {

                    var temp = array2[i].distance
                    array2[i].distance = array2[j].distance
                    array2[j].distance = temp
                 }
            }


        }



    }

}
