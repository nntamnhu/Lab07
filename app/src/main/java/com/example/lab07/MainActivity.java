package com.example.lab07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //insert product
        ProductDAO dao = new ProductDAO(this);
        List<Product> listProduct = dao.GetAll();

        ProductAdapter adapter = new ProductAdapter(listProduct);
        RecyclerView rcvProduct = findViewById(R.id.rcvProduct);
        rcvProduct.setAdapter(adapter);
        rcvProduct.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(itemDecoration);

        //Animators
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(adapter, this));
        itemTouchHelper.attachToRecyclerView(rcvProduct);


        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_product, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(viewDialog);
                AlertDialog alert = builder.create();
                alert.show();

                EditText txtName = viewDialog.findViewById(R.id.edtName);
                EditText txtImage = viewDialog.findViewById(R.id.edtImage);
                EditText txtPrice = viewDialog.findViewById(R.id.edtPrice);

                viewDialog.findViewById(R.id.btnDialogSaveProduct).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = txtName.getText().toString();
                        String image = txtImage.getText().toString(); // Get image URL
                        float price = Float.parseFloat(txtPrice.getText().toString());

                        Product p = new Product(0, name, image, price); // Create Product with image URL
                        dao.Insert(p);
                        listProduct.add(p);
                        Toast.makeText(viewDialog.getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                        adapter.notifyItemInserted(listProduct.size() - 1);
                    }
                });

                viewDialog.findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Xóa nội dung trong EditText
                        txtName.getText().clear();
                        txtImage.getText().clear();
                        txtPrice.getText().clear();
                    }
                });
            }
        });
    }
}


