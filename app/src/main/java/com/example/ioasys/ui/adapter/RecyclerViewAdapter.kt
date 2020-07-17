package com.example.ioasys.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.ioasys.service.listeners.OnEnterpriseInfoClickListener
import com.example.ioasys.R
import com.example.ioasys.service.models.EnterpriseInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview.view.*


class RecyclerViewAdapter(private val lista: List<EnterpriseInfo>, val OnEnterpriseInfoClickListener: OnEnterpriseInfoClickListener) : Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.bind(item)
        holder.listener(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun listener(item: EnterpriseInfo) {
            itemView.setOnClickListener {
                OnEnterpriseInfoClickListener.onClickListener(item)
            }
        }

        fun bind(item: EnterpriseInfo) {
            val url = "https://empresas.ioasys.com.br/api/v1${item.photo}"
            val picasso = Picasso.Builder(itemView.context)
                .build()
            picasso.load(url).placeholder(R.drawable.ic_camera).into(itemView.imgempresa)
            itemView.empresanome.text = item.enterpriseName
            itemView.empresaPais.text = item.country
            itemView.empresatipo.text = item.enterpriseType.enterpriseTypeName
        }
    }
}

