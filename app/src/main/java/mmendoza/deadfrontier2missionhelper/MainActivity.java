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

    // Array of town selections, used for both spinners
    String[] towns = {"All Towns", "Albandale Park", "Archbrook",
            "Coopertown", "Dallbow", "Dawnhill", "Duntsville", "Greywood", "Haverbrook", "Lerwillbury",
            "Ravenwall Heights", "Richbow Hunt", "South Moorhurst", "West Moledale"};

    // Database Helper
    private DBHelper db;

    // Private member variables for the Date
    private TextView dateTextView;
    private String date;

    // Private member variable for the spinners
    private Spinner missionTownSpinner;
    private Spinner giverTownSpinner;

    // Private member variables for the listView and adapter
    private ListView missionsListView;
    private MissionListAdapter missionsListAdapter;

    // Array to hold Mission objects for the list view
    private ArrayList<Mission> selectedMissionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the database
        deleteDatabase(DBHelper.DATABASE_NAME); // TODO : DO NOT DELETE DB IN FINAL VERSION, INSTEAD FIGURE WAY TO UPDATE DATA
        db = new DBHelper(this);

        // Start the Jsoup processes
        new Missions().execute();

        // Connect the variables
        dateTextView = findViewById(R.id.dateTextView);
        missionTownSpinner = findViewById(R.id.missionTownSpinner);
        giverTownSpinner = findViewById(R.id.giverTownSpinner);

        // ListView Adapter
        missionsListView = findViewById(R.id.missionsListView);
        missionsListAdapter =
                new MissionListAdapter(this, R.layout.mission_list_item, selectedMissionsList);

        // Mission objective town spinner adapter
        final ArrayAdapter<String> missionTownSpinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, towns);
        missionTownSpinner.setAdapter(missionTownSpinnerAdapter);

        // Giver town spinner adapter
        final ArrayAdapter<String> giverTownSpinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, towns);
        giverTownSpinner.setAdapter(giverTownSpinnerAdapter);

        // Mission town spinner Listener
        AdapterView.OnItemSelectedListener missionTownSpinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
                // Get the selected towns
                String selected = String.valueOf(spinner.getItemAtPosition(i));
                String giverTown = String.valueOf(giverTownSpinner.getSelectedItem());
                // Clear the adapter
                missionsListAdapter.clear();
                // Display the selected town's missions
                spinnerItemSelected(selected, giverTown);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        // Giver town spinner Listener
        AdapterView.OnItemSelectedListener giverTownSpinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
                // Get the selected towns
                String missionTown = String.valueOf(missionTownSpinner.getSelectedItem());
                String selected = String.valueOf(spinner.getItemAtPosition(i));
                // Clear the adapter
                missionsListAdapter.clear();
                // Display the selected town's missions
                spinnerItemSelected(missionTown, selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };

        // Set the spinner listeners
        missionTownSpinner.setOnItemSelectedListener(missionTownSpinnerListener);
        giverTownSpinner.setOnItemSelectedListener(giverTownSpinnerListener);

        // Add single tap functionality to list items
        missionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                // The mission the user clicked
                Mission selectedMission = selectedMissionsList.get(pos);

                // Toggle whether a mission is marked as completed or not
                if (selectedMission.isCompleted() == 1) {
                    selectedMission.setCompleted(0);
                    db.updateMissionCompletionStatus(0, selectedMission.getQuestGiver());
                }
                else {
                    selectedMission.setCompleted(1);
                    db.updateMissionCompletionStatus(1, selectedMission.getQuestGiver());
                }

                // Update the list
                missionsListAdapter.notifyDataSetChanged();
            }
        });
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

                // Separate missions by Dallbow, Haverbrook, Greywood, and Bonus categories
                // Dallbow Missions
                Elements dallbowMissions = div.get(0).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Haverbrook Missions
                Elements haverbrookMissions = div.get(1).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Greywood Missions
                Elements greywoodMissions = div.get(2).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Bonus Missions
                Elements bonusMissions = div.get(3).select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Separate each category's missions into individual missions and its individual parts
                // Create lists of mission objects and use them to populate the database
                db.addAllMissions(createMissions(dallbowMissions));
                db.addAllMissions(createMissions(haverbrookMissions));
                db.addAllMissions(createMissions(greywoodMissions));
                db.addAllMissions(createMissions(bonusMissions));

            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Update the textView with the date
            dateTextView.setText(date);

            // Refresh the spinner and list view adapter once the sync task has finished.
            spinnerItemSelected(String.valueOf(missionTownSpinner.getSelectedItem()),
                    String.valueOf(giverTownSpinner.getSelectedItem()));
            missionsListView.setAdapter(missionsListAdapter);
        }
    }

    /**
     * Function used to gather the missions' info from the wikia's categories.
     * Dallbow Police Department, Haverbrook Memorial Hospital, Greywood Star Hotel, Bonus (other)
     *
     * @param missions The data of the missions for a single category.
     * @return categoryMissions
     */
     public ArrayList<Mission> createMissions(Elements missions)
     {
         ArrayList<Mission> categoryMissions = new ArrayList<>();

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
             Mission selectableMission = new Mission(missionLocation.text(), missionTown.text(), missionGoal.text(),
                     missionWalkthrough.text(), questGiver.text(), questGiverTown.text(), questGiverLocation.text(),
                     questGiverWalkthrough.text(), rewardMoney.text(), rewardExp.text(), 0);

             // Add the mission object to the database
             categoryMissions.add(selectableMission);
         }

         return  categoryMissions;
     }


    /**
     * Method used by both spinner listeners to filter the different possible combinations of mission and quest giver towns.
     *
     * @param missionTown The specified mission town(s).
     * @param giverTown The specified quest giver town(s).
     */
    public void spinnerItemSelected(String missionTown, String giverTown)
    {
        // Giver town selected
        if (!missionTown.equals("-- No Town Selected --"))
            // All towns selected for both spinners
            if (missionTown.equals("All Towns") && giverTown.equals("All Towns"))
                missionsListAdapter.addAll(db.getAllMissions());
                // Specific mission town, all giver towns
            else if (!missionTown.equals("All Towns") && giverTown.equals("All Towns"))
                missionsListAdapter.addAll(db.getAllMissionsFromMissionTown(missionTown));
                // All mission towns, specific giver town
            else if (missionTown.equals("All Towns") && !giverTown.equals("All Towns"))
                missionsListAdapter.addAll(db.getAllMissionsFromGiverTown(giverTown));
                // Specific towns selected for both spinners
            else
                missionsListAdapter.addAll(db.getAllMissionsFromBothTowns(missionTown, giverTown));
    }
}
