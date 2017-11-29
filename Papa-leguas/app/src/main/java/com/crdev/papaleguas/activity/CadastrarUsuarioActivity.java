package com.crdev.papaleguas.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.crdev.papaleguas.R;
import com.crdev.papaleguas.config.ConfiguracaoFirebase;
import com.crdev.papaleguas.model.Usuario;

import java.util.Calendar;

public class CadastrarUsuarioActivity extends Activity {

    //declarando variaveis
    private EditText nome;
    private EditText email;
    private EditText senha;
    private CheckBox checkOrganizador;
    private Button botaoCadastrar;
    private Usuario usuario;
    private EditText dataNasc;
    private DatePickerDialog datePickerDialog;
    private RadioGroup rgsexo;
    private RadioButton masculino;
    private RadioButton feminino;

    //variavel autenticacao usuario firebase
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        //identificando layout com classe java pelo id
        nome = (EditText) findViewById(R.id.id_nome_cadastrar);
        email = (EditText) findViewById(R.id.id_email_cadastrar);
        senha = (EditText) findViewById(R.id.id_senha_cadastrar);
        checkOrganizador = findViewById(R.id.id_checkorganizador_cadastrar);
        botaoCadastrar = findViewById(R.id.id_botao_cadastrar);
        dataNasc = findViewById(R.id.id_data_cadastrar);
        rgsexo = findViewById(R.id.id_rgsexo_cadastrar);
        masculino = findViewById(R.id.id_rbmasc_cadastrar);
        feminino = findViewById(R.id.id_rbfem_cadastrar);

        //teclado nao aparecer ao clicar
        dataNasc.setTextIsSelectable(true);


        //click event no eT da data
        dataNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instanciando classe Calendar e pegando dia mes e ano atual
                final Calendar c = Calendar.getInstance();
                int mAno = c.get(Calendar.YEAR); //ano
                int mMes = c.get(Calendar.MONTH); //mes
                int mDia = c.get(Calendar.DAY_OF_MONTH); //dia
                //Dialog pra escolher data
                datePickerDialog = new DatePickerDialog(CadastrarUsuarioActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //setar dia, mes e ano no eT
                        dataNasc.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mAno, mMes, mDia);
                //mostrar dialog
                datePickerDialog.show();
            }
        });

        //botao para se cadastrar
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                switch (rgsexo.getCheckedRadioButtonId()) {
                    case R.id.id_rbmasc_cadastrar:
                        usuario.setSexo("Masculino");
                        break;
                    case R.id.id_rbfem_cadastrar:
                        usuario.setSexo("Feminino");
                        break;
                }
                //checando se é organizador, se tiver marcado atribui true
                if (checkOrganizador.isChecked()) {
                    usuario.setOrganizador(true);
                } else {
                    usuario.setOrganizador(false);
                }

                //metodo que vai autenticar o usuario e adiciona-lo ao banco
                cadastrarUsuario();

            }
        });

    }

    //construtor metodo cadastrar usuario e adicionar ao banco
    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //criando usuario com email e senha
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastrarUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //se email e senha sao validos e foi cadastrado nos usuarios
                if (task.isSuccessful()) {
                    Toast.makeText(CadastrarUsuarioActivity.this, "Você se cadastrou com sucesso", Toast.LENGTH_LONG).show();

                    //pegando uid da autenticacao e salvando como id, para criar nó no database
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(usuarioFirebase.getUid());
                    usuario.salvar();
                    abrirTelaPrincipal();
                } else {

                    //variavel para guardar excecao
                    String erroExcecao = "";
                    //tratando excecoes
                    try {
                        throw task.getException();

                        //excecao se o password for fraco
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                        //excecao se o email for invalido
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O email digitado é inválido, digite outro email!";
                        //excecao se o email ja estiver no db
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse email já está em uso no Aplicativo!";
                        //excecao se nao for nenhuma das anteriores, dando printStackTrace
                    } catch (Exception e) {
                        erroExcecao = "Ao cadastrar usuário!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastrarUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void abrirTelaPrincipal() {
        Intent intent = new Intent(CadastrarUsuarioActivity.this, MainActivity.class);
        startActivity(intent);

    }

}