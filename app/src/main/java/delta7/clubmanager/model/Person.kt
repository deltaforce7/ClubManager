package delta7.clubmanager.model

data class Person(
    val name: String,
    val id: String,
    val password: String,
    val joinedClubs: MutableList<JoinedClub> = mutableListOf(),
): Model

data class JoinedClub(
    val roomId: String,
    val roomName: String,
): Model

data class Club(
    val roomId: String,
    val roomName: String,
    val adminId: String,
    val roomNotice: MutableList<String> = mutableListOf(),
    val roomMembers: MutableList<ClubMember> = mutableListOf(),
): Model

data class ClubMember(
    val name: String,
    val id: String,
): Model

interface Model