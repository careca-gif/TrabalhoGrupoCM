package ipca.avaliacao.trabalhogrupo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // variáveis de botões
        val practiceButton = findViewById<Button>(R.id.button_Practice)
        val taticsButton = findViewById<Button>(R.id.button_Tatics)
        val squadButton = findViewById<Button>(R.id.button_Squad)
        val leagueButton = findViewById<Button>(R.id.button_League)

        practiceButton.setOnClickListener {
            val intent = Intent(this, Practice::class.java)
            startActivity(intent)
        }

        squadButton.setOnClickListener {
            val intent = Intent(this, Squad::class.java)
            startActivity(intent)
        }

        taticsButton.setOnClickListener {
            val intent = Intent(this, TaticsActivity::class.java)
            startActivity(intent)
        }

        
        leagueButton.setOnClickListener {
            val intent = Intent(this, LeagueActivity::class.java)
            startActivity(intent)
        }
    }
}