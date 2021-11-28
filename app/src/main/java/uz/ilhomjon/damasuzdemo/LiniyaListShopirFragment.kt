package uz.ilhomjon.damasuzdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import uz.ilhomjon.damasuzdemo.adapter.LiniyaAdapter
import uz.ilhomjon.damasuzdemo.databinding.FragmentLiniyaListShopirBinding
import uz.ilhomjon.damasuzdemo.models.Liniya
import uz.ilhomjon.damasuzdemo.models.SHopir

class LiniyaListShopirFragment : Fragment() {
    lateinit var binding:FragmentLiniyaListShopirBinding
    lateinit var shopir: SHopir

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var referenceLiniya: DatabaseReference
    lateinit var referenceShopir: DatabaseReference
    lateinit var liniyaList:ArrayList<Liniya>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLiniyaListShopirBinding.inflate(layoutInflater)

        shopir = arguments?.getSerializable("keyShopir") as SHopir
        firebaseDatabase = FirebaseDatabase.getInstance()
        referenceLiniya = firebaseDatabase.getReference("liniya")
        referenceShopir = firebaseDatabase.getReference("shopir")

        referenceLiniya.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                liniyaList = ArrayList()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Liniya::class.java)
                    if (value!=null){
                        if (value.id == shopir.liniyaId){
                            liniyaList.add(value)
                        }
                    }
                }
                binding.progressLiniya.visibility = View.GONE
                if (liniyaList.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                }
             referenceShopir.addValueEventListener(object : ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {
                     val shList = ArrayList<SHopir>()
                     val children1 = snapshot.children
                     for (child in children1) {
                         val value = child.getValue(SHopir::class.java)
                         if (value!=null){
                             shList.add(value)
                         }
                     }

                     val liniyaAdapter = LiniyaAdapter(liniyaList, shList, object : LiniyaAdapter.OnCLick{
                         override fun rootCLick(liniya: Liniya) {
                             findNavController().navigate(R.id.mapsFragment, bundleOf("keyLiniya" to liniya, "keyShopir" to shopir))
                         }
                     })
                     binding.rvLiniya.adapter = liniyaAdapter

                 }

                 override fun onCancelled(error: DatabaseError) {

                 }
             })

              }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Iltimos internetga ulaning", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}