package mmendoza.deadfrontier2missionhelper;

/**
 * The Mission class represents a single mission of Dead Frontier 2.
 *
 * @author Moises Mendoza
 */
public class Mission {

    // Mission information
    private String mMissionTitle;
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

    /**
     *
     *
     * @param missionTitle
     * @param missionLocation
     * @param missionTown
     * @param missionGoal
     * @param missionWalkthrough
     * @param questGiver
     * @param questGiverTown
     * @param questGiverLocation
     * @param questGiverWalkthrough
     * @param money
     * @param exp
     */
    public Mission(String missionTitle, String missionLocation, String missionTown, String missionGoal,
                   String missionWalkthrough, String questGiver, String questGiverTown,
                   String questGiverLocation, String questGiverWalkthrough, String money, String exp)
    {
        mMissionTitle = missionTitle;
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
    }

    /**
     *
     * @return
     */
    public String getMissionTitle() {
        return mMissionTitle;
    }

    /**
     *
     * @param missionTitle
     */
    public void setMissionTitle(String missionTitle) {
        mMissionTitle = missionTitle;
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
     *
     */
    public void setExp(String exp) {
        mExp = exp;
    }

}
