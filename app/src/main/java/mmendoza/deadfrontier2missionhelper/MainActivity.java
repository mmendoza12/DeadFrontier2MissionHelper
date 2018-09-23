package mmendoza.deadfrontier2missionhelper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    // URL for Dead Frontier 2 Mission Guide Wikia
    private static final String URL = "http://deadfrontier2.wikia.com/wiki/Mission_guides";

    // Array of mission outpost names with bonus, used for spinner
    String[] selections = {"Dallbow Police Department Missions",
            "Haverbrook Memorial Hospital Missions", "Greywood Star Hotel Missions", "Bonus Missions"};

    // Private member variables for the Date
    private TextView dateTextView;
    private String date;

    // Private member variable for the spinner
    private Spinner missionSpinner;

    // Private member variables for the listView and adapter
    private ListView missionsListView;
    private MissionListAdapter missionsListAdapter;

    // Arrays to hold Mission objects for each of the different cities
    private ArrayList<Mission> selectedMissionsList = new ArrayList<>();
    private ArrayList<Mission> dallbowMissionsList = new ArrayList<>();
    private ArrayList<Mission> haverbrookMissionsList = new ArrayList<>();
    private ArrayList<Mission> greywoodMissionsList = new ArrayList<>();
    private ArrayList<Mission> bonusMissionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start the Jsoup processes
        new Missions().execute();

        // Connect the variables
        dateTextView = findViewById(R.id.dateTextView);
        missionSpinner = findViewById(R.id.missionSpinner);

        // ListView Adapter
        missionsListView = findViewById(R.id.missionsListView);
        missionsListAdapter =
                new MissionListAdapter(this, R.layout.mission_list_item, selectedMissionsList);
        missionsListView.setAdapter(missionsListAdapter);

        // Spinner adapter
        final ArrayAdapter<String> missionSpinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selections);
        missionSpinner.setAdapter(missionSpinnerAdapter);

        // Spinner Listener
        AdapterView.OnItemSelectedListener missionSpinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
                // Get the outpost
                String selected = String.valueOf(spinner.getItemAtPosition(i));
                // Clear the adapter
                missionsListAdapter.clear();
                // Display the selected outpost's missions
                if (String.valueOf(selected).equals("Dallbow Police Department Missions"))
                    missionsListAdapter.addAll(dallbowMissionsList);
                else if (String.valueOf(selected).equals("Haverbrook Memorial Hospital Missions"))
                    missionsListAdapter.addAll(haverbrookMissionsList);
                else if (String.valueOf(selected).equals("Greywood Star Hotel Missions"))
                    missionsListAdapter.addAll(greywoodMissionsList);
                else
                    missionsListAdapter.addAll(bonusMissionsList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        // Set the spinner listener
        missionSpinner.setOnItemSelectedListener(missionSpinnerListener);
    }

    /**
     * Fetches the data from the URL
     */
    private class Missions extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            Document doc;

            try {

                // Connect to URL
                doc = Jsoup.connect(URL).get();

                // Get the body of the site
                Element body = doc.body();

                // Div containing the list of all missions
                Elements div = body.select("div[class=mw-collapsible-content]");

                // Get the Date from the wikia, let the user know if the missions need to be updated
                date = body.select("article > div > div div > h2 > span").get(0).text();

                // Separate missions by Dallbow, Haverbrook, Greywood, and Bonus
                // Dallbow Missions
                Elements dallbowMissions = div.get(0).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Haverbrook Missions
                Elements haverbrookMissions = div.get(1).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Greywood Missions
                Elements greywoodMissions = div.get(2).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Bonus Missions
                Elements bonusMissions = div.get(3).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Separate each area's missions into individual missions and its individual parts
                // Create mission objects from dallbowMissions and put them into dallbowMisisonsList
                createMissions(dallbowMissionsList, dallbowMissions);

                // Create mission objects from haverbrookMissions and put them into haverbrookMisisonsList
                createMissions(haverbrookMissionsList, haverbrookMissions);

                // Create mission objects from greywoodMissions and put them into greywoodMisisonsList
                createMissions(greywoodMissionsList, greywoodMissions);

                // Create mission objects from bonusMissions and put them into bonusMissionsList
                createMissions(bonusMissionsList, bonusMissions);

            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Update the textView(s) with the missions' data
            dateTextView.setText(date);
        }
    }

    /**
     * Function used to gather the missions' info from each outpost (Dallbow PD, Greywood Hotel, etc.)
     *
     * @param selectableMissions A list to add the specific outpost's missions to
     * @param missions The data of the missions for the different outposts
     */
     public void createMissions(ArrayList<Mission> selectableMissions, Elements missions)
     {
         for (Element mission : missions) {

             // Mission Location
             Element missionLocation = mission.select("span").get(0);
             // Mission Town
             Element missionTown = mission.select("span").get(1);
             // Mission Goal
             Element missionGoal = mission.select("span").get(2);
             // Mission Chunk 1 (divs embedded within div)
             Element missionChunk1 = mission.select("div").get(3);
             // Mission Walkthrough
             Element missionWalkthrough = missionChunk1.select("p").get(0);
             // Quest Giver
             Element questGiver = missionChunk1.select("span").get(0);
             // Quest Giver Town
             Element questGiverTown = missionChunk1.select("span").get(1);
             // Quest Giver location
             Element questGiverLocation = missionChunk1.select("span").get(2);
             // Mission Chunk 2 (divs further embedded within div)
             Element missionChunk2 = missionChunk1.select("div").get(5);
             // Quest Giver Walkthrough
             Element questGiverWalkthrough = missionChunk2.select("p").get(0);
             // Mission Reward Money
             Element rewardMoney = missionChunk2.select("span").get(0);
             // Mission Reward Exp
             Element rewardExp = missionChunk2.select("span").get(1);

             // Create a Mission object from the data
             Mission selectableMission = new Mission(missionGoal.text(), missionLocation.text(), missionTown.text(), missionGoal.text(),
                     missionWalkthrough.text(), questGiver.text(), questGiverTown.text(), questGiverLocation.text(),
                     questGiverWalkthrough.text(), rewardMoney.text(), rewardExp.text());

             // Add the mission object to the mission list
             selectableMissions.add(selectableMission);
         }
     }
}
