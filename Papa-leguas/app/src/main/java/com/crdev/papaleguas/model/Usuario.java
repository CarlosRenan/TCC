package com.crdev.papaleguas.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.crdev.papaleguas.config.ConfiguracaoFirebase;
import com.google.firebase.database.ValueEventListener;

public class Usuario {


    //variaveis do usuario, modelo para o database
    private String id;
    private String nome;
    private String email;
    private String senha;
    private boolean organizador;
    private String sexo;
    private int idade;


    public Usuario(){

    }

    //salvando informacoes do usuario no database
    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        //pegando o id do usuario e colocando como nó do database
        referenciaFirebase.child("Usuarios").child(getId()).setValue(this);
    }

    /**public void verificaOrganizador(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
       int ehOrganizador;
        ValueEventListener valueEventListener = referenciaFirebase.child("Usuarios").child(getId()).child("organizador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }**/

    //exclude para nao adicionar o id no banco de dados, vai ficar como no do usuario somente
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //exclude para nao salvar senha no banco de dados tambem
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isOrganizador() {
        return organizador;
    }

    public void setOrganizador(boolean organizador) {
        this.organizador = organizador;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

}
