package com.crdev.papaleguas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.crdev.papaleguas.R;
import com.crdev.papaleguas.config.ConfiguracaoFirebase;
import com.crdev.papaleguas.model.Usuario;

public class EntrarUsuarioActivity extends AppCompatActivity {

    //declarando variaveis da classe
    private EditText email;
    private EditText senha;
    private Button btEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_usuario);

        verificarSeUsuarioEstaLogado();

        //setando layout com variaveis da classe
        email = findViewById(R.id.id_email_entrar);
        senha = findViewById(R.id.id_senha_entrar);
        btEntrar = findViewById(R.id.id_botao_entrar);

        //evento ao clicar botao Entrar
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //instanciando usuario e pegando email e senha
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                //metodo para validar
                validarLogin();
            }
        });






    }

    private void verificarSeUsuarioEstaLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    //construtor metodo de validacao
    private void validarLogin(){

        //pegando metodo estatico de autenticacao da classe ConfiguracaoFirebase
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(EntrarUsuarioActivity.this, "Seja bem vindo! Vamos correr?" , Toast.LENGTH_LONG).show();
                }else{
                    //variavel para guardar excecao
                    String erroExcecao = "";
                    //tratando excecoes
                    try{
                        throw task.getException();

                        //excecao se o email estiver errado ou nao estiver cadastrado
                    } catch (FirebaseAuthInvalidUserException e){
                        erroExcecao = "O email digitado está incorreto ou não está cadastrado no sistema!";
                        //excecao se a senha estiver errada
                    } catch (FirebaseAuthInvalidCredentialsException e ){
                        erroExcecao = "A senha digitada está incorreta, favor verificar!";
                        //excecao se nao for nenhuma das anteriores, dando printStackTrace
                    } catch (Exception e) {
                        erroExcecao = "Ao entrar!";
                        e.printStackTrace();
                    }
                    Toast.makeText(EntrarUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void abrirTelaPrincipal(){
        Intent intent = new Intent(EntrarUsuarioActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent (EntrarUsuarioActivity.this, CadastrarUsuarioActivity.class);
        startActivity(intent);
    }
}
