package uz.ilhomjon.damasuzdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ilhomjon.damasuzdemo.databinding.ItemLiniyaBinding
import uz.ilhomjon.damasuzdemo.models.Liniya
import uz.ilhomjon.damasuzdemo.models.SHopir

class LiniyaAdapter(val list: List<Liniya>, val shList:List<SHopir>, val onCLick: OnCLick) : RecyclerView.Adapter<LiniyaAdapter.Vh>() {

    inner class Vh(var itemRv: ItemLiniyaBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(liniya: Liniya) {
            itemRv.itemName.text = liniya.name

            var count = 0
            var all = 0
            for (sHopir in shList) {
                if (sHopir.liniyaId==liniya.id){
                    all++
                    if (sHopir.isOnline){
                        count++
                    }
                }
            }
            itemRv.itemTvFaol.text = count.toString()
            itemRv.itemTvJami.text = all.toString()

            itemRv.root.setOnClickListener {
                onCLick.rootCLick(liniya)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemLiniyaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnCLick{
        fun rootCLick(liniya: Liniya)
    }
}