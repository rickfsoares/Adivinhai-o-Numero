package br.edu.ifpb.acerteonumero

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Venceu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venceu)

        val etNomeDoUsuario = findViewById<EditText>(R.id.etNomeDoUsuario)
        val btnInserirNome = findViewById<Button>(R.id.btnInserirNome)

        btnInserirNome.setOnClickListener {
            val nomeInserido = etNomeDoUsuario.text.toString()

            val intent = Intent()
            intent.putExtra("nomeDoUsuario", nomeInserido)

            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }
}