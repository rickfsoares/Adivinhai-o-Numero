package br.edu.ifpb.acerteonumero

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.edu.ifpb.acerteonumero.Jogo
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    private lateinit var btnAdivinhacaoDoUsuario: Button
    private lateinit var palpiteDoUsuario: TextInputEditText
    private lateinit var btnStatus: Button

    private lateinit var tvMenorValor: TextView
    private lateinit var tvMaiorValor: TextView

    private var numeroSorteado:Int = Jogo().sortear()

    private val menorValor:String = "1"
    private val maiorValor:String = "100"

    private val venceuMsg: String = "Venceste"
    private val perdeuMsg: String = "Perdeste"
    private val quaseMsg: String = "Quase, mas não o suficiente para desvendar o enigma!"

    private val duracaoDaMsg: Int = Toast.LENGTH_LONG

    private var statusDoJogo: Boolean = true

    private val venceuResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            val nomeInserido = data?.getStringExtra("nomeDoUsuario")

            if (nomeInserido != null) {
                val tvNome = findViewById<TextView>(R.id.tvNomeDoUsuario)
                tvNome.text = nomeInserido
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tvMenorValor = findViewById(R.id.tvMenorValor)
        this.tvMaiorValor = findViewById(R.id.tvMaiorValor)

        this.palpiteDoUsuario = findViewById(R.id.tUserInput)

        this.btnAdivinhacaoDoUsuario = findViewById(R.id.bAdivinhar)
        this.btnAdivinhacaoDoUsuario.setOnClickListener {
            checkUserAnswer(it)
            changeStatus(it)
        }

        this.btnStatus = findViewById(R.id.bStatus)
        this.btnStatus.setOnLongClickListener{
            restart(it)
            return@setOnLongClickListener true
        }
        this.btnStatus.setOnClickListener{ changeStatus(it)}



    }

    fun checkUserAnswer(view: View) {
        val numeroDoUsuario:String = this.palpiteDoUsuario.text.toString()
        var novoValor: Int = 0

        val condicaoParaPerder: Boolean = (numeroDoUsuario == this.tvMenorValor.text.toString()) ||
                (numeroDoUsuario == this.tvMaiorValor.text.toString()) ||
                (this.tvMenorValor.text.toString() == this.tvMaiorValor.text.toString()) ||
                (numeroDoUsuario.toInt() > tvMaiorValor.text.toString().toInt()) ||
                (numeroDoUsuario.toInt() < tvMenorValor.text.toString().toInt())

        if (condicaoParaPerder) {
            Toast.makeText(this, perdeuMsg, duracaoDaMsg).show()
            this.statusDoJogo = false
            val intent = Intent(this, Perdeu::class.java)
            startActivity(intent)
        }
        else if ( numeroDoUsuario.toInt() < numeroSorteado) {
            novoValor = numeroDoUsuario.toInt() + 1
            this.tvMenorValor.text = SpannableStringBuilder(novoValor.toString())
            Toast.makeText(this, quaseMsg, duracaoDaMsg).show()
        }
        else if (numeroDoUsuario.toInt() > numeroSorteado) {
            novoValor = numeroDoUsuario.toInt() - 1
            this.tvMaiorValor.text = SpannableStringBuilder(novoValor.toString())
            Toast.makeText(this, quaseMsg, duracaoDaMsg).show()
        }
        else {
            Toast.makeText(this, venceuMsg, duracaoDaMsg).show()
            this.statusDoJogo = false
            val intent = Intent(this, Venceu::class.java)
            venceuResult.launch(intent)
        }
    }

    fun changeStatus(view: View) {
        if (!this.statusDoJogo) {
            this.btnStatus.text = getString(R.string.statusEncerrado)
        } else {
            this.btnStatus.text = getString(R.string.executando)
        }
    }

    fun restart(view: View) {
        this.statusDoJogo = true
        Jogo().sortear()

        this.tvMenorValor.text = SpannableStringBuilder(this.menorValor)
        this.tvMaiorValor.text = SpannableStringBuilder(this.maiorValor)
    }

}