package com.godspeed.un_toxic.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godspeed.un_toxic.databinding.FragmentProgressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    val db = Firebase.firestore

    private lateinit var binding: FragmentProgressBinding
    private lateinit var prolist: ArrayList<Progressdata>
    private lateinit var prorecycle: RecyclerView
    // This property is only valid between onCreateView and
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(inflater, container, false)

        prorecycle=binding.pro
        prorecycle.layoutManager= LinearLayoutManager(requireContext())
        prorecycle.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.collection("Profiles").get().addOnSuccessListener {
                result->
            for(document in result){
                val money = (((document.data["Price"]).toString()).toLong())*(((document.data["Number of Smoke"]).toString()).toLong())
                val smokes = Integer.parseInt( document.data["Number of Smoke"].toString())
                val w = (Integer.parseInt(document.data["Number of Smoke"].toString()) * 7 * Integer.parseInt(document.data["Price"].toString())).toString()
                val m = (Integer.parseInt(document.data["Number of Smoke"].toString()) * 30 * Integer.parseInt(document.data["Price"].toString())).toString()
                val h = (Integer.parseInt(document.data["Number of Smoke"].toString()) * 182 * Integer.parseInt(document.data["Price"].toString())).toString()
                val y = (Integer.parseInt(document.data["Number of Smoke"].toString())* 365 * Integer.parseInt(document.data["Price"].toString())).toString()
                prolist.add(Progressdata(document.data["Name"] as String,smokes, money, 10, 22, w, m, h, y ))
                Log.d("TAG", "${document.id} => ${document.data["price"]}")
                break
            }
            val adapter = Progressadapt(prolist)
            prorecycle.adapter = adapter
        }.addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
//                Toast.makeText(this,"Error getting documents: ", Toast.LENGTH_SHORT).show()
            }
        prolist = arrayListOf<Progressdata>()
    }
}

