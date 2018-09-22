package mmendoza.deadfrontier2missionhelper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    // Private member variable for the textView(s)
    private TextView textView;

    // Arrays to hold Mission objects for each of the different cities
    private ArrayList<Mission> dallbowMissionsList = new ArrayList<>();
    private ArrayList<Mission> haverbrookMissionsList = new ArrayList<>();
    private ArrayList<Mission> greywoodMissionsList = new ArrayList<>();
    private ArrayList<Mission> bonusMissionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the textView(s)
        textView = findViewById(R.id.textView);

        // Start the Jsoup processes
        new Missions().execute();
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

                // TODO: Separate missions by Dallbow, Haverbrook, Greywood, and Bonus

                // Dallbow Missions
                Elements dallbowMissions = div.get(0).select("td[style=vertical-align:top;text-align:center;width=450px;]");
                //System.out.println("!*!*!*!* Dallbow Mission Count: " + dallbowMissions.size());

                // Haverbrook Missions
                Elements haverbrookMissions = div.get(1).select("td[style=vertical-align:top;text-align:center;width=450px;]");
                //System.out.println("!*!*!*!* Haverbrook Mission Count: " + haverbrookMissions.size());

                // Greywood Missions
                Elements greywoodMissions = div.get(2).select("td[style=vertical-align:top;text-align:center;width=450px;]");
                //System.out.println("!*!*!*!* Greywood Mission Count: " + greywoodMissions.size());

                // Bonus Missions
                Elements bonusMissions = div.get(3).select("td[style=vertical-align:top;text-align:center;width=450px;]");
                //System.out.println("!*!*!*!* Bonus Mission Count: " + bonusMissions.size());


                // TODO: Separate each area's missions into individual missions and its individual parts

                // Mission 1 of Dallbow Missions
                Element dall1 = dallbowMissions.get(0);
                // Mission Location
                Element mloc = dall1.select("span").get(0);
                // Mission Town
                Element mtown = dall1.select("span").get(1);
                // Mission Goal
                Element mgoal = dall1.select("span").get(2);
                // Mission chunk
                Element mchunk = dall1.select("div").get(3);
                // Mission walk
                Element mwalk = mchunk.select("p").get(0);
                // Mission giver
                Element giver = mchunk.select("span").get(0);
                // Giver town
                Element gtown = mchunk.select("span").get(1);
                // Giver location
                Element gloc = mchunk.select("span").get(2);
                // Chunk 2
                Element chunk2 = mchunk.select("div").get(5);
                // Giver walk
                Element gwalk = chunk2.select("p").get(0);
                // Money
                Element money = chunk2.select("span").get(0);
                // Exp
                Element exp = chunk2.select("span").get(1);
                // Output mission 1's info
                System.out.println("!*!*!*! Dall mission 1: " + "\n" + mloc.text() + "\n" + mtown.text() + "\n" + mgoal.text() + "\n" +
                        mwalk.text() + "\n" + giver.text() + "\n" + gtown.text() + "\n" + gloc.text() + "\n" +
                gwalk.text() + "\n" + money.text() + "\n" + exp.text() + "\nEND DALLBOW MISSION 1 *!*!!*!");


                // TODO: Create mission objects from dallbowMissions and put them into dallbowMisisonsList

                // Get the data of each mission for Dallbow
                for (Element dallbowMission : dallbowMissions)
                {
                    // Mission Location
                    Element missionLocation = dallbowMission.select("span").get(0);
                    // Mission Town
                    Element missionTown = dallbowMission.select("span").get(1);
                    // Mission Goal
                    Element missionGoal = dallbowMission.select("span").get(2);
                    // Mission Chunk 1 (divs embedded within div)
                    Element missionChunk1 = dallbowMission.select("div").get(3);
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
                    Mission mission = new Mission(missionGoal.text(), missionLocation.text(), missionTown.text(), missionGoal.text(),
                            missionWalkthrough.text(), questGiver.text(), questGiverTown.text(), questGiverLocation.text(),
                            questGiverWalkthrough.text(), rewardMoney.text(), rewardExp.text());

                    // Add the mission object to the mission list
                    dallbowMissionsList.add(mission);
                }

                // Test output for dallbowMissionsList
                System.out.println("!*!*!*!* Dallbow MISSIONS *!!*!**!*!*!");
                for (Mission mission : dallbowMissionsList)
                {
                    System.out.println(mission.getMissionTitle());
                }

                // TODO: Create mission objects from haverbrookMissions and put them into haverbrookMisisonsList

                // Get the data of each mission for Haverbrook
                for (Element haverbrookMission : haverbrookMissions)
                {
                    // Mission Location
                    Element missionLocation = haverbrookMission.select("div").get(0);
                    // Mission Town
                    Element missionTown = haverbrookMission.select("div").get(1);
                    // Mission Goal
                    Element missionGoal = haverbrookMission.select("div").get(2);
                    // Mission Chunk 1 (divs embedded within div)
                    Element missionChunk1 = haverbrookMission.select("div").get(3);
                    // Mission Walkthrough
                    Element missionWalkthrough = missionChunk1.select("p").get(0);
                    // Quest Giver
                    Element questGiver = missionChunk1.select("b").get(0);
                    // Quest Giver Town
                    Element questGiverTown = missionChunk1.select("b").get(1);
                    // Quest Giver location
                    Element questGiverLocation = missionChunk1.select("b").get(2);
                    // Mission Chunk 2 (divs further embedded within div)
                    Element missionChunk2 = missionChunk1.select("div").get(5);
                    // Quest Giver Walkthrough
                    Element questGiverWalkthrough = missionChunk2.select("p").get(0);
                    // Mission Reward Money
                    Element rewardMoney = missionChunk2.select("b").get(0);
                    // Mission Reward Exp
                    Element rewardExp = missionChunk2.select("b").get(1);

                    // Create a Mission object from the data
                    Mission mission = new Mission(missionGoal.text(), missionLocation.text(), missionTown.text(), missionGoal.text(),
                            missionWalkthrough.text(), questGiver.text(), questGiverTown.text(), questGiverLocation.text(),
                            questGiverWalkthrough.text(), rewardMoney.text(), rewardExp.text());

                    // Add the mission object to the mission list
                    haverbrookMissionsList.add(mission);
                }

                // Test output for haverbrookMissionsList
                System.out.println("!*!*!*!* Haverbrook MISSIONS *!!*!**!*!*!");
                for (Mission mission : haverbrookMissionsList)
                {
                    System.out.println(mission.getMissionTitle());
                }

                // TODO: Create mission objects from greywoodMissions and put them into greywoodMisisonsList

                // Get the data of each mission for Greywood
                for (Element greywoodMission : greywoodMissions)
                {
                    // Mission Location
                    Element missionLocation = greywoodMission.select("div").get(0);
                    // Mission Town
                    Element missionTown = greywoodMission.select("div").get(1);
                    // Mission Goal
                    Element missionGoal = greywoodMission.select("div").get(2);
                    // Mission Chunk 1 (divs embedded within div)
                    Element missionChunk1 = greywoodMission.select("div").get(3);
                    // Mission Walkthrough
                    Element missionWalkthrough = missionChunk1.select("p").get(0);
                    // Quest Giver
                    Element questGiver = missionChunk1.select("b").get(0);
                    // Quest Giver Town
                    Element questGiverTown = missionChunk1.select("b").get(1);
                    // Quest Giver location
                    Element questGiverLocation = missionChunk1.select("b").get(2);
                    // Mission Chunk 2 (divs further embedded within div)
                    Element missionChunk2 = missionChunk1.select("div").get(5);
                    // Quest Giver Walkthrough
                    Element questGiverWalkthrough = missionChunk2.select("p").get(0);
                    // Mission Reward Money
                    Element rewardMoney = missionChunk2.select("b").get(0);
                    // Mission Reward Exp
                    Element rewardExp = missionChunk2.select("b").get(1);

                    // Create a Mission object from the data
                    Mission mission = new Mission(missionGoal.text(), missionLocation.text(), missionTown.text(), missionGoal.text(),
                            missionWalkthrough.text(), questGiver.text(), questGiverTown.text(), questGiverLocation.text(),
                            questGiverWalkthrough.text(), rewardMoney.text(), rewardExp.text());

                    // Add the mission object to the mission list
                    greywoodMissionsList.add(mission);
                }

                // Test output for greywoodMissionsList
                System.out.println("!*!*!*!* Greywood MISSIONS *!!*!**!*!*!");
                for (Mission mission : greywoodMissionsList)
                {
                    System.out.println(mission.getMissionTitle());
                }

                // TODO: Create mission objects from bonusMissions and put them into bonusMissionsList

                // Get the data of each mission for Bonus Missions
                for (Element bonusMission : bonusMissions)
                {
                    // Mission Location
                    Element missionLocation = bonusMission.select("div").get(0);
                    // Mission Town
                    Element missionTown = bonusMission.select("div").get(1);
                    // Mission Goal
                    Element missionGoal = bonusMission.select("div").get(2);
                    // Mission Chunk 1 (divs embedded within div)
                    Element missionChunk1 = bonusMission.select("div").get(3);
                    // Mission Walkthrough
                    Element missionWalkthrough = missionChunk1.select("p").get(0);
                    // Quest Giver
                    Element questGiver = missionChunk1.select("b").get(0);
                    // Quest Giver Town
                    Element questGiverTown = missionChunk1.select("b").get(1);
                    // Quest Giver location
                    Element questGiverLocation = missionChunk1.select("b").get(2);
                    // Mission Chunk 2 (divs further embedded within div)
                    Element missionChunk2 = missionChunk1.select("div").get(5);
                    // Quest Giver Walkthrough
                    Element questGiverWalkthrough = missionChunk2.select("p").get(0);
                    // Mission Reward Money
                    Element rewardMoney = missionChunk2.select("b").get(0);
                    // Mission Reward Exp
                    Element rewardExp = missionChunk2.select("b").get(1);

                    // Create a Mission object from the data
                    Mission mission = new Mission(missionGoal.text(), missionLocation.text(), missionTown.text(), missionGoal.text(),
                            missionWalkthrough.text(), questGiver.text(), questGiverTown.text(), questGiverLocation.text(),
                            questGiverWalkthrough.text(), rewardMoney.text(), rewardExp.text());

                    // Add the mission object to the mission list
                    bonusMissionsList.add(mission);
                }

                // Test output for bonusMissionsList
                System.out.println("!*!*!*!* BONUS MISSIONS *!!*!**!*!*!");
                for (Mission mission : bonusMissionsList)
                {
                    System.out.println(mission.getMissionTitle());
                }



                // TODO: Everything


                // Mission Titles (kinda broken, might just mission goals for titles, me thinks)
                Elements th = div.select("tr th");

                // Missions' info
                Elements td = div.select("td[style=vertical-align:top;text-align:center;width=450px;]");

                // Missions count (YES! There are 57 missions! It output 57!)
                System.out.println("*!*!*!* Mission Count: " + td.size());

                // Mission 2
                Element mission2 = td.get(1);
                System.out.println(mission2.select("div").get(2).text());

                // Mission Location
                Element missionLocation = td.select("div").get(0);
                System.out.println("*!!*!*!*! MLOCATION TEXT: " + missionLocation.text());

                // Mission Town
                Element missionTown = td.select("div").get(1);
                System.out.println("*!!*!*!*! MTOWN TEXT: " + missionTown.text());

                // Mission Goal
                Element missionGoal = td.select("div").get(2);
                System.out.println("*!!*!*!*! MGOAL TEXT: " + missionGoal.text());

                // Rest of the mission's info in one... gotta separate them
                Element x = td.select("div").get(3);
                System.out.println("*!!*!*!*! X TEXT: " + x.text());

                //System.out.println("BODY TEXT:" + body.text());

                //System.out.println("!!!!!DIV TEXT:" + div.text());

                // Mission titles
                //System.out.println("!!!!!TH TEXT:" + th.text());

                //System.out.println("!!!!!TD TEXT:" + td.text());

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
            //textView.setText(textView.getText());
        }
    }
}
