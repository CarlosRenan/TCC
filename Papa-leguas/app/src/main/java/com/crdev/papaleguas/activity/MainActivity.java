package com.crdev.papaleguas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crdev.papaleguas.R;
import com.crdev.papaleguas.adapter.TabAdapter;
import com.crdev.papaleguas.config.ConfiguracaoFirebase;
import com.crdev.papaleguas.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //variaveis
    private ViewPager viewPager;

    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private Toolbar toolBar;
    private FirebaseAuth usuarioAutenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //setando toolbar
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        //setando viewpager e tabs
        viewPager = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabAdapter  = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);



        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }


    //inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.grupo_organizador, false);

        return true;
    }

    //capturando o clique no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //item.getItemId retorna a opcao clicada e encaminha para a ação
        switch (item.getItemId()){
            case R.id.item_cadastrarcorrida:
                return true;
            case R.id.item_editarconta:
                return true;
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //metodo para deslogar o usuario
    public void deslogarUsuario(){
        final AlertDialog.Builder certezaSair = new AlertDialog.Builder(MainActivity.this);
        certezaSair.setTitle("Sair da sua conta?");
        certezaSair.setMessage("Você terá que reinserir seu email e senha para entrar novamente, tem certeza?");
        certezaSair.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Você saiu da sua conta", Toast.LENGTH_LONG).show();
                usuarioAutenticacao.signOut();
                Intent intent = new Intent(MainActivity.this, EntrarUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
        });
        certezaSair.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        certezaSair.create();
        certezaSair.show();
        }
}

