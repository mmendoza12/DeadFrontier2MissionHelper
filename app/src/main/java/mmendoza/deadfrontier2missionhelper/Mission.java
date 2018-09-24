package mmendoza.deadfrontier2missionhelper;

/**
 * The Mission class represents a single mission of Dead Frontier 2.
 *
 * @author Moises Mendoza
 */
public class Mission {

    // Mission information
    private String mMissionLocation;
    private String mMissionTown;
    private String mMissionGoal;
    private String mMissionWalkthrough;

    // Quest Giver information
    private String mQuestGiver;
    private String mQuestGiverTown;
    private String mQuestGiverLocation;
    private String mQuestGiverWalkthrough;

    // Reward information
    private String mMoney;
    private String mExp;

    // Personal Completion status
    private int mIsCompleted;

    /**
     * Mission objects to hold the information for each mission.
     *
     * @param missionLocation The specific location of the mission. (Riverview Apartments A)
     * @param missionTown The main town the mission objective is found within. (Archbrook)
     * @param missionGoal The mission objective. (Find Person, Kill Infected, etc)
     * @param missionWalkthrough Detailed steps on how to find/complete the objective.
     * @param questGiver The name of the NPC that provides the quest. (John Doe)
     * @param questGiverTown The town that the quest giver resides in. (Dallbow)
     * @param questGiverLocation The specific location of the quest giver. (Dallbow Police Department)
     * @param questGiverWalkthrough Detailed steps on how to find the quest giver.
     * @param money Number representing the amount of money awarded upon completion. ($100)
     * @param exp Number representing the amount of EXP awarded upon completion. (100XP)
     */
    public Mission(String missionLocation, String missionTown, String missionGoal,
                   String missionWalkthrough, String questGiver, String questGiverTown,
                   String questGiverLocation, String questGiverWalkthrough, String money, String exp)
    {
        mMissionLocation = missionLocation;
        mMissionTown = missionTown;
        mMissionGoal = missionGoal;
        mMissionWalkthrough = missionWalkthrough;
        mQuestGiver = questGiver;
        mQuestGiverTown = questGiverTown;
        mQuestGiverLocation = questGiverLocation;
        mQuestGiverWalkthrough = questGiverWalkthrough;
        mMoney = money;
        mExp = exp;
        mIsCompleted = 0;
    }

    /**
     *
     */
    public String getMissionLocation() {
        return mMissionLocation;
    }

    /**
     *
     */
    public void setMissionLocation(String missionLocation) {
        mMissionLocation = missionLocation;
    }

    /**
     *
     */
    public String getMissionTown() {
        return mMissionTown;
    }

    /**
     *
     */
    public void setMissionTown(String missionTown) {
        mMissionTown = missionTown;
    }

    /**
     *
     */
    public String getMissionGoal() {
        return mMissionGoal;
    }

    /**
     *
     */
    public void setMissionGoal(String missionGoal) {
        mMissionGoal = missionGoal;
    }

    /**
     *
     */
    public String getMissionWalkthrough() {
        return mMissionWalkthrough;
    }

    /**
     *
     */
    public void setMissionWalkthrough(String missionWalkthrough) {
        mMissionWalkthrough = missionWalkthrough;
    }

    /**
     *
     */
    public String getQuestGiver() {
        return mQuestGiver;
    }

    /**
     *
     */
    public void setQuestGiver(String questGiver) {
        mQuestGiver = questGiver;
    }

    /**
     *
     */
    public String getQuestGiverTown() {
        return mQuestGiverTown;
    }

    /**
     *
     */
    public void setQuestGiverTown(String questGiverTown) {
        mQuestGiverTown = questGiverTown;
    }

    /**
     *
     */
    public String getQuestGiverLocation() {
        return mQuestGiverLocation;
    }

    /**
     *
     */
    public void setQuestGiverLocation(String questGiverLocation) {
        mQuestGiverLocation = questGiverLocation;
    }

    /**
     *
     */
    public String getQuestGiverWalkthrough() {
        return mQuestGiverWalkthrough;
    }

    /**
     *
     */
    public void setQuestGiverWalkthrough(String questGiverWalkthrough) {
        mQuestGiverWalkthrough = questGiverWalkthrough;
    }

    /**
     *
     */
    public String getMoney() {
        return mMoney;
    }

    /**
     *
     */
    public void setMoney(String money) {
        mMoney = money;
    }

    /**
     *
     */
    public String getExp() {
        return mExp;
    }

    /**
     * Returns whether a mission is completed for the user or not.
     *
     * @return The specific mission's completion status.
     */
    public int isCompleted() {
        return mIsCompleted;
    }

    /**
     * Used for the user to check whether they have completed the specific mission or not.
     *
     * @param completed The new completion status.
     */
    public void setCompleted(int completed) {
        mIsCompleted = completed;
    }

    /**
     *
     */
    public void setExp(String exp) {
        mExp = exp;
    }

    /**
     * Returns the mission specific location and town in a single string.
     *
     * @return A string containing the mission location and town. Ex: Dallbow Apartments (Dallbow)
     */
    public String getFullMissionLocation()
    {
        return mMissionLocation + " (" + mMissionTown + ")";
    }

    /**
     * Returns the quest giver's specific location and town in a single string.
     *
     * @return A string containing the quest giver's location and town. Ex: Dallbow PD (Dallbow)
     */
    public String getFullQuestGiverLocation()
    {
        return mQuestGiverLocation + " (" + mQuestGiverTown+ ")";
    }


}
