package com.example.project.Volley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements Filterable {
    private List<Order> orderList, filteredOrderList;
    private Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        filteredOrderList = new ArrayList<>(orderList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = filteredOrderList.get(position);

        holder.tvNama.setText(order.getNama());
        holder.tvTelepon.setText(order.getTelepon());
        holder.tvAlamat.setText(order.getAlamat());
        holder.tvTotal.setText("Rp. "+order.getTotal());
    }

    @Override
    public int getItemCount() {
        return filteredOrderList.size();
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        filteredOrderList = new ArrayList<>(orderList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Order> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(orderList);
                } else {
                    for (Order order : orderList) {
                        if (order.getNama().toLowerCase() .contains(charSequenceString.toLowerCase()))
                            filtered.add(order);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered; return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredOrderList.clear();
                filteredOrderList.addAll((List<Order>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTelepon, tvTotal, tvAlamat;
        CardView cvOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTelepon = itemView.findViewById(R.id.tv_phone);
            tvNama = itemView.findViewById(R.id.tv_title);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvAlamat = itemView.findViewById(R.id.tv_address);
            cvOrder = itemView.findViewById(R.id.cv_order);
        }
    }
}
