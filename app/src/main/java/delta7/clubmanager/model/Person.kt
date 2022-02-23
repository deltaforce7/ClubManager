package delta7.clubmanager.model

data class Person(
    val name: String,
    val id: String,
    val password: String,
    val joinedClubs: ArrayList<JoinedClub> = ArrayList(),
): Model

data class JoinedClub(
    val roomId: String,
    val roomName: String,
): Model

data class Club(
    val roomId: String,
    val roomName: String,
    val adminId: String,
    val announcements: ArrayList<Announcement> = ArrayList(),
    val roomMembers: ArrayList<ClubMember> = ArrayList(),
): Model

data class ClubMember(
    val name: String,
    val id: String,
): Model

interface Model