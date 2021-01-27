package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    daoContacto dao;
    Adaptador adapter;
    ArrayList<Contacto> Lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new daoContacto(this);
        Lista = dao.verTodos();
        adapter = new Adaptador(this, Lista, dao);
        ListView list = (ListView) findViewById(R.id.lista);
        Button agregar = (Button) findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Nuevo registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo);
                dialog.show();
                final EditText nombre = (EditText) dialog.findViewById(R.id.nombre);
                final EditText apellido = (EditText) dialog.findViewById(R.id.apellido);
                final EditText email = (EditText) dialog.findViewById(R.id.email);
                final EditText edad = (EditText) dialog.findViewById(R.id.edad);
                Button guardar = (Button) dialog.findViewById(R.id.d_agregar);
                guardar.setText("Agregar");
                Button cancelar = (Button) dialog.findViewById(R.id.d_cancelar);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            c = new Contacto(nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    email.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()));
                            dao.insertar(c);
                            Lista=dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }


                });
            }
        });
    }
}