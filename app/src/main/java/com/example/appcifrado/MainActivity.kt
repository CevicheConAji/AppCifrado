package com.example.appcifrado

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var btn_par_impar: Button
    lateinit var btn_cesar: Button
    lateinit var btn_desc_cesar: Button
    lateinit var btn_bilateral: Button
    lateinit var btn_desc_bilateral: Button
    lateinit var btn_vegenere: Button
    lateinit var btn_desc_vegenere: Button

    lateinit var et_frase: EditText
    lateinit var tv_frase: TextView
    lateinit var btn_frase: Button
    lateinit var btn_seven_times: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponent()
        initListeners()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun initListeners() {
        btn_frase.setOnClickListener {
            // Obtiene el texto del TextView que muestra el criptograma
            val criptograma = tv_frase.text.toString()

            // Mueve el criptograma al EditText
            et_frase.setText(criptograma)
        }

        btn_seven_times.setOnClickListener {
            // Establece la frase predeterminada en el EditText
            et_frase.setText("fall seven times stand up eight")
        }

        btn_par_impar.setOnClickListener {
            // Obtiene el texto en claro desde el EditText
            val textoEnClaro = et_frase.text.toString()

            // Aplica el cifrado par-impar
            val textoCifrado = cifradoParImpar(textoEnClaro)

            // Muestra el texto cifrado en el TextView
            tv_frase.text = textoCifrado
        }
        btn_cesar.setOnClickListener {
            val textoEnClaro = et_frase.text.toString()
            val textoCifrado = cifradoCesar(textoEnClaro, 3) // Desplazamiento de 3
            tv_frase.text = textoCifrado
        }
        btn_bilateral.setOnClickListener {
            val textoEnClaro = et_frase.text.toString()
            val textoCifrado = cifradoBilateral(textoEnClaro)
            tv_frase.text = textoCifrado
        }
        btn_vegenere.setOnClickListener {
            val textoEnClaro = et_frase.text.toString()
            val clave = "BCD" // Define la clave aquí
            val textoCifrado = cifradoVenegere(textoEnClaro, clave)
            tv_frase.text = textoCifrado
        }
        btn_desc_cesar.setOnClickListener {
            val textoCifrado = et_frase.text.toString()
            val textoDescifrado = descifradoCesar(textoCifrado, 3) // Desplazamiento de 3
            tv_frase.text = textoDescifrado
        }
        btn_desc_bilateral.setOnClickListener {
            val textoCifrado = et_frase.text.toString()
            val textoDescifrado = descifradoBilateral(textoCifrado)
            tv_frase.text = textoDescifrado
        }
        btn_desc_vegenere.setOnClickListener {
            val textoCifrado = et_frase.text.toString()
            val clave = "BCD" // Define la clave
            val textoDescifrado = descifradoVenegere(textoCifrado, clave)
            tv_frase.text = textoDescifrado
        }

    }

    private fun cifradoVenegere(texto: String, clave: String): String {
        val resultado = StringBuilder()
        val claveExtendida = clave.uppercase().repeat((texto.length / clave.length) + 1).substring(0, texto.length) // Aseguramos clave en mayúsculas
        Log.d("CifradoVenegere", "Clave extendida: $claveExtendida")

        var claveIndex = 0

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val base = if (caracter.isLowerCase()) 'a' else 'A'
                val desplazamiento = claveExtendida[claveIndex] - 'A' + 1 // A=1, B=2, etc.
                Log.d("CifradoVenegere", "Procesando: $caracter, Desplazamiento: $desplazamiento")

                val nuevaLetra = ((caracter - base + desplazamiento) % 26 + base.code).toChar()
                Log.d("CifradoVenegere", "Letra cifrada: $nuevaLetra")

                resultado.append(nuevaLetra)
                claveIndex++
            } else {
                resultado.append(caracter) // Mantiene espacios y otros caracteres
            }
        }

        return resultado.toString()
    }

    private fun descifradoVenegere(texto: String, clave: String): String {
        val resultado = StringBuilder()
        val claveExtendida = clave.uppercase().repeat((texto.length / clave.length) + 1).substring(0, texto.length) // Extiende la clave
        Log.d("DescifradoVenegere", "Clave extendida: $claveExtendida")

        var claveIndex = 0

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val base = if (caracter.isLowerCase()) 'a' else 'A'
                val desplazamiento = claveExtendida[claveIndex] - 'A' + 1 // Clave en base A=1, B=2...
                Log.d("DescifradoVenegere", "Procesando: $caracter, Desplazamiento: $desplazamiento")

                val nuevaLetra = ((caracter - base - desplazamiento + 26) % 26 + base.code).toChar()
                Log.d("DescifradoVenegere", "Letra descifrada: $nuevaLetra")

                resultado.append(nuevaLetra)
                claveIndex++
            } else {
                resultado.append(caracter) // Mantiene espacios y otros caracteres
            }
        }

        return resultado.toString()
    }


    private fun cifradoBilateral(texto: String): String {
        val resultado = StringBuilder()
        var suma = true // Alterna entre sumar y restar

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val base = if (caracter.isLowerCase()) 'a' else 'A'
                val desplazamiento = if (suma) 3 else -3
                val nuevaLetra = ((caracter - base + desplazamiento + 26) % 26 + base.code).toChar()
                resultado.append(nuevaLetra)
                suma = !suma // Cambia entre sumar y restar
            } else {
                resultado.append(caracter) // Mantiene espacios y otros caracteres
            }
        }
        return resultado.toString()
    }
    private fun descifradoBilateral(texto: String): String {
        val resultado = StringBuilder()
        var suma = true // Alterna entre sumar y restar el desplazamiento

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val base = if (caracter.isLowerCase()) 'a' else 'A'
                val desplazamiento = if (suma) -3 else 3 // Invertimos las operaciones
                val nuevaLetra = ((caracter - base + desplazamiento + 26) % 26 + base.code).toChar()
                resultado.append(nuevaLetra)
                suma = !suma // Cambia entre sumar y restar
            } else {
                resultado.append(caracter) // Mantiene espacios y otros caracteres
            }
        }

        return resultado.toString()
    }

    private fun cifradoCesar(texto: String, desplazamiento: Int): String {
        val resultado = StringBuilder()

        for (caracter in texto) {
            // Solo aplica el cifrado a letras, deja espacios y otros símbolos igual
            if (caracter.isLetter()) {
                val base = if (caracter.isLowerCase()) 'a' else 'A'
                val nuevaLetra = ((caracter - base + desplazamiento) % 26 + base.code).toChar()
                resultado.append(nuevaLetra)
            } else {
                resultado.append(caracter) // Mantiene caracteres no alfabéticos
            }
        }
        return resultado.toString()
    }
    private fun descifradoCesar(texto: String, desplazamiento: Int): String {
        val resultado = StringBuilder()

        for (caracter in texto) {
            if (caracter.isLetter()) {
                val base = if (caracter.isLowerCase()) 'a' else 'A'
                val nuevaLetra = ((caracter - base - desplazamiento + 26) % 26 + base.code).toChar()
                resultado.append(nuevaLetra)
            } else {
                resultado.append(caracter) // Mantiene espacios y otros caracteres
            }
        }

        return resultado.toString()
    }
    private fun cifradoParImpar(texto: String): String {
        val chars = texto.toCharArray()

        // Log inicial con el texto original
        Log.d("CifradoParImpar", "Texto original: $texto")

        for (i in chars.indices step 2) {
            if (i + 1 < chars.size) {
                // Log antes del intercambio
                Log.d("CifradoParImpar", "Intercambiando posición $i (${chars[i]}) con posición ${i + 1} (${chars[i + 1]})")

                // Intercambia caracteres
                val temp = chars[i]
                chars[i] = chars[i + 1]
                chars[i + 1] = temp

                // Log después del intercambio
                Log.d("CifradoParImpar", "Resultado parcial: ${String(chars)}")
            }
        }

        // Log final con el texto cifrado
        Log.d("CifradoParImpar", "Texto cifrado: ${String(chars)}")

        return String(chars)
    }



    private fun initComponent() {
        btn_par_impar = findViewById(R.id.btn_par_impar)
        btn_cesar = findViewById(R.id.btn_cesar)
        btn_desc_cesar = findViewById(R.id.btn_desc_cesar)
        btn_bilateral = findViewById(R.id.btn_bilateral)
        btn_desc_bilateral = findViewById(R.id.btn_desc_bilateral)
        btn_vegenere = findViewById(R.id.btn_vegenere)
        btn_desc_vegenere = findViewById(R.id.btn_desc_vegenere)
        et_frase = findViewById(R.id.et_frase)
        tv_frase = findViewById(R.id.tv_frase)
        btn_frase = findViewById(R.id.btn_frase)
        btn_seven_times = findViewById(R.id.btn_seven_times)

    }
}