package mmendoza.deadfrontier2missionhelper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * List adapter for the missions.
 *
 * @author Moises Mendoza
 */
public class MissionListAdapter extends ArrayAdapter<Mission> {

    // Activity that uses the adapter (MainActivity)
    private Context mContext;
    // Layout file to inflate (R.layout.mission_list_item)
    private int mResource;
    // List of missions
    private List<Mission> mMissionsList;

    public MissionListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Mission> missionsList) {
        super(context, resource, missionsList);
        mContext = context;
        mResource = resource;
        mMissionsList = missionsList;
    }

    /**
     *
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup Parent)
    {
        final Mission selectedMission = mMissionsList.get(pos);

        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, null);

        // Set the mission's title
        TextView missionTitle = view.findViewById(R.id.missionTitletextView);
        missionTitle.setText(selectedMission.getMissionTitle());

        // Set the mission's location (town), and walkthrough
        TextView missionLocation = view.findViewById(R.id.missionLocationTextView);
        missionLocation.setText(selectedMission.getFullMissionLocation());
        TextView missionWalkthrough = view.findViewById(R.id.missionWalkthroughTextView);
        missionWalkthrough.setText(selectedMission.getMissionWalkthrough());

        // Set the mission's quest giver name, location (town), and walkthrough
        TextView questGiver = view.findViewById(R.id.questGiverTextView);
        questGiver.setText(selectedMission.getQuestGiver());
        TextView giverLocation = view.findViewById(R.id.questGiverLocationTextView);
        giverLocation.setText(selectedMission.getFullQuestGiverLocation());
        TextView giverWalkthrough = view.findViewById(R.id.questGiverWalkthroughTextView);
        giverWalkthrough.setText(selectedMission.getQuestGiverWalkthrough());

        // Set the mission's reward money and exp
        TextView rewardMoney = view.findViewById(R.id.rewardMoneyTextView);
        rewardMoney.setText(selectedMission.getMoney());
        TextView rewardExp = view.findViewById(R.id.rewardExpTextView);
        rewardExp.setText(selectedMission.getExp());

        return view;
    }
}
