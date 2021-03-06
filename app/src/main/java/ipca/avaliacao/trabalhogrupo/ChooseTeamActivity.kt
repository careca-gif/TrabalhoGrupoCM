package ipca.avaliacao.trabalhogrupo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class ChooseTeamActivity : AppCompatActivity() {

    var teams : MutableList<Team> = ArrayList()
    lateinit var teamsAdapter : TeamsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_team)

        val listViewTeams = findViewById<ListView>(R.id.listView_Teams)
        teamsAdapter = TeamsAdapter()
        listViewTeams.adapter = teamsAdapter



         GlobalScope.async{
            var result = Beckend.getTeams()
            if (result == "Sem internet!") {
                runOnUiThread {
                    Toast.makeText(this@ChooseTeamActivity, "Sem internet!", Toast.LENGTH_LONG).show()
                }
            } else {
                val jsonObject = JSONObject(result)

                val jsonObjectResult : JSONObject = jsonObject.get("api") as JSONObject
                if (jsonObject.getJSONObject("api").getInt("results")!= 0) {
                    var jsonArray: JSONArray = jsonObjectResult.getJSONArray("teams")
                    for (index in 0 until jsonArray.length()) {
                        val jsonTeam = jsonArray.getJSONObject(index)
                        val team = Team.fromJson(jsonTeam)
                        teams.add(team)
                    }
                    runOnUiThread{
                        teamsAdapter.notifyDataSetChanged()
                    }
                }
            }

        }
    }

    inner class TeamsAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return teams.size
        }

        override fun getItem(position: Int): Any {
            return teams[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.roll_teams, parent,false)

            val textViewTeamName = rowView.findViewById<TextView>(R.id.textViewTeamName)
            val textViewCountry = rowView.findViewById<TextView>(R.id.textViewCountry)
            val textViewCity = rowView.findViewById<TextView>(R.id.textViewCity)
            val textViewStadium = rowView.findViewById<TextView>(R.id.textViewStadium)

            textViewTeamName.text = teams[position].nome
            textViewCountry.text = teams[position].pais
            textViewCity.text = teams[position].cidade
            textViewStadium.text = teams[position].nomeEstadio


            rowView.isClickable = true
            rowView.setOnClickListener{
                // guardar definições do utilizador para a equipa
                val preferencesHelper = PreferencesHelper(this@ChooseTeamActivity)
                preferencesHelper.prefChooseTeam
                preferencesHelper.prefTeam = teams[position].nome
                preferencesHelper.prefCodigo = teams[position].codigo
                preferencesHelper.savePreferences()
                //passar para o main menu
                val intent = Intent(this@ChooseTeamActivity, MainMenu::class.java)
                startActivity(intent)
            }

            return rowView
        }
    }
}