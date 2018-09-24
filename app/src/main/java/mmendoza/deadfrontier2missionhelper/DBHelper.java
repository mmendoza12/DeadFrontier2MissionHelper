package mmendoza.deadfrontier2missionhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    // Database Name and Version
    static final String DATABASE_NAME = "DF2 Mission Helper DB";
    private static final int DATABASE_VERSION = 1;

    // Database Table "Missions" and its fields
    private static final String MISSIONS_TABLE = "Missions";
    private static final String FIELD_MISSION_LOCATION = "mission_location";
    private static final String FIELD_MISSION_TOWN = "mission_town";
    private static final String FIELD_MISSION_GOAL = "mission_goal";
    private static final String FIELD_MISSION_WALKTHROUGH = "mission_walkthrough";
    private static final String FIELD_QUEST_GIVER = "quest_giver";
    private static final String FIELD_QUEST_GIVER_LOCATION = "quest_giver_location";
    private static final String FIELD_QUEST_GIVER_TOWN = "quest_giver_town";
    private static final String FIELD_QUEST_GIVER_WALKTHROUGH = "quest_giver_walkthrough";
    private static final String FIELD_REWARD_MONEY = "reward_money";
    public static final String FIELD_REWARD_EXP = "reward_exp";
    public static final String FIELD_COMPLETED = "completed";

    /**
     * Constructs the database.
     *
     * @param context
     */
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * Creates the table.
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE " + MISSIONS_TABLE + "("
                + FIELD_MISSION_LOCATION + " TEXT, "
                + FIELD_MISSION_TOWN + " TEXT, "
                + FIELD_MISSION_GOAL + " TEXT, "
                + FIELD_MISSION_WALKTHROUGH + " TEXT, "
                + FIELD_QUEST_GIVER + " TEXT, "
                + FIELD_QUEST_GIVER_TOWN + " TEXT, "
                + FIELD_QUEST_GIVER_LOCATION + " TEXT, "
                + FIELD_QUEST_GIVER_WALKTHROUGH + " TEXT, "
                + FIELD_REWARD_MONEY + " TEXT, "
                + FIELD_REWARD_EXP + " TEXT, "
                + FIELD_COMPLETED + " INTEGER" + ")";
        sqLiteDatabase.execSQL(createQuery);
    }

    /**
     *
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // TODO: Upgrade
    }

    /**
     * Add a single mission into the db table.
     *
     * @param mission The mission to be added.
     */
    public void addMission(Mission mission)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_MISSION_LOCATION, mission.getMissionLocation());
        values.put(FIELD_MISSION_TOWN, mission.getMissionTown());
        values.put(FIELD_MISSION_GOAL, mission.getMissionGoal());
        values.put(FIELD_MISSION_WALKTHROUGH, mission.getMissionWalkthrough());
        values.put(FIELD_QUEST_GIVER, mission.getQuestGiver());
        values.put(FIELD_QUEST_GIVER_TOWN, mission.getQuestGiverTown());
        values.put(FIELD_QUEST_GIVER_LOCATION, mission.getQuestGiverLocation());
        values.put(FIELD_QUEST_GIVER_WALKTHROUGH, mission.getQuestGiverWalkthrough());
        values.put(FIELD_REWARD_MONEY, mission.getMoney());
        values.put(FIELD_REWARD_EXP, mission.getExp());
        values.put(FIELD_COMPLETED, mission.isCompleted());

        db.insert(MISSIONS_TABLE, null, values);
        db.close();
    }

    /**
     * Add all missions to the db table.
     *
     * @param missionsList The list of missions to add.
     */
    public void addAllMissions(ArrayList<Mission> missionsList)
    {
        for (Mission mission : missionsList)
            addMission(mission);
    }

    /**
     *
     */
    public void deleteMission(String questGiver)
    {
        // TODO: Delete mission if quest giver is no longer on the wikia, this should work i think
    }

    /**
     * Get all the missions of the db table as a list.
     */
    public ArrayList<Mission> getAllMissions()
    {
        ArrayList<Mission> missionsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                MISSIONS_TABLE,
                new String[] {FIELD_MISSION_LOCATION, FIELD_MISSION_TOWN, FIELD_MISSION_GOAL, FIELD_MISSION_WALKTHROUGH,
                FIELD_QUEST_GIVER, FIELD_QUEST_GIVER_TOWN, FIELD_QUEST_GIVER_LOCATION, FIELD_QUEST_GIVER_WALKTHROUGH,
                FIELD_REWARD_MONEY, FIELD_REWARD_EXP, FIELD_COMPLETED},
                null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                Mission mission =
                        new Mission(cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9));
                missionsList.add(mission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return missionsList;
    }

    /**
     * Returns a list of missions only where the mission objective is within the specified town
     */
    public ArrayList<Mission> getAllMissionsFromMissionTown(String missionObjectiveTown)
    {
        ArrayList<Mission> missionsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                MISSIONS_TABLE,
                new String[] {FIELD_MISSION_LOCATION, FIELD_MISSION_TOWN, FIELD_MISSION_GOAL, FIELD_MISSION_WALKTHROUGH,
                        FIELD_QUEST_GIVER, FIELD_QUEST_GIVER_TOWN, FIELD_QUEST_GIVER_LOCATION, FIELD_QUEST_GIVER_WALKTHROUGH,
                        FIELD_REWARD_MONEY, FIELD_REWARD_EXP, FIELD_COMPLETED},
                null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                Mission mission =
                        new Mission(cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9));
                if (String.valueOf(cursor.getString(1)).equals(missionObjectiveTown))
                    missionsList.add(mission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return missionsList;
    }

    /**
     * Returns a list of missions with specified mission and quest giver towns
     */
    public ArrayList<Mission> getAllMissionsFromBothTowns(String missionObjectiveTown, String questGiverTown)
    {
        ArrayList<Mission> missionsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                MISSIONS_TABLE,
                new String[] {FIELD_MISSION_LOCATION, FIELD_MISSION_TOWN, FIELD_MISSION_GOAL, FIELD_MISSION_WALKTHROUGH,
                        FIELD_QUEST_GIVER, FIELD_QUEST_GIVER_TOWN, FIELD_QUEST_GIVER_LOCATION, FIELD_QUEST_GIVER_WALKTHROUGH,
                        FIELD_REWARD_MONEY, FIELD_REWARD_EXP, FIELD_COMPLETED},
                null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                Mission mission =
                        new Mission(cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9));
                if (String.valueOf(cursor.getString(1)).equals(missionObjectiveTown) &&
                        String.valueOf(cursor.getString(5)).equals(questGiverTown))
                    missionsList.add(mission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return missionsList;
    }

    /**
     * Returns a list of missions only where the quest giver resides within the specified town
     */
    public ArrayList<Mission> getAllMissionsFromGiverTown(String questGiverTown)
    {
        ArrayList<Mission> missionsList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                MISSIONS_TABLE,
                new String[] {FIELD_MISSION_LOCATION, FIELD_MISSION_TOWN, FIELD_MISSION_GOAL, FIELD_MISSION_WALKTHROUGH,
                        FIELD_QUEST_GIVER, FIELD_QUEST_GIVER_TOWN, FIELD_QUEST_GIVER_LOCATION, FIELD_QUEST_GIVER_WALKTHROUGH,
                        FIELD_REWARD_MONEY, FIELD_REWARD_EXP, FIELD_COMPLETED},
                null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                Mission mission =
                        new Mission(cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9));
                if (String.valueOf(cursor.getString(5)).equals(questGiverTown))
                    missionsList.add(mission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return missionsList;
    }

    /**
     * Update a mission's completion status.
     */
    public void updateMissionCompletionStatus(int isCompleted, String questGiver)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_COMPLETED, isCompleted);

        db.update(MISSIONS_TABLE, values, FIELD_QUEST_GIVER + " = ?",
                new String[] {questGiver});

        db.close();
    }
}