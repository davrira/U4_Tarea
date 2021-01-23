package mx.tecnm.proyectopivote

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val siPermisoRegistroLlamadas = 1
    val siPermisoLlamar = 2

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG), siPermisoRegistroLlamadas)
        }//if

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), siPermisoLlamar)
        }

        btnRegistroLLamadas.setOnClickListener {
            obtenerLlamadas()
        }//btnRegistro

        btnLlamar.setOnClickListener {
            llamar()
        }//llamar

    }//onCreate

    fun obtenerLlamadas(){

        var llamadas = ""
        var cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null)

        if (cursor!!.moveToFirst()){

            var numero = cursor.getColumnIndex(CallLog.Calls.NUMBER)
            var tipo = cursor.getColumnIndex(CallLog.Calls.TYPE)

            do {

                llamadas+= "Numero: ${cursor.getString(numero)}"+"\n"+"Tipo: ${cursor.getString(tipo)}"+"\n\n"

            }while (cursor.moveToNext())

        }else{
            llamadas = "No hay registro de llamadas"
        }//if-else

        TeViDatos.setText(llamadas)

    }//obtenerLlamadas

    fun llamar(){

        var numero = EdTeNumero.text.toString()
        val callIntent = Intent(Intent.ACTION_CALL)

        callIntent.data = Uri.parse("tel:"+numero)

        startActivity(callIntent)

    }//llamar

}//class