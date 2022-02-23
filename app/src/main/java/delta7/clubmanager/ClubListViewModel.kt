package delta7.clubmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import delta7.clubmanager.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClubListViewModel: ViewModel() {

    private val repository = Repository()
    val clubLiveData = MutableLiveData<Club>()
    val joinClub = MutableLiveData<ViewState>()
    val createClub = MutableLiveData<ViewState>()
    val login = MutableLiveData<ViewState>()
    val signUp = MutableLiveData<ViewState>()

    fun getClub(clubId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getClub(clubId)) {
                is Response.Success<Club> -> {
                    val data = response.data!!
                    clubLiveData.postValue(data)
                }
                else -> {}
            }
        }
    }

    fun joinClub(clubId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getClub(clubId)) {
                is Response.Success<Club> -> {
                    val data = response.data!!
                    data.roomMembers.add(ClubMember(Session.person.name, Session.person.id))
                    Session.person.joinedClubs.add(JoinedClub(data.roomId, data.roomName))

                    repository.updateClub(data)
                    repository.updatePerson(Session.person)

                    clubLiveData.postValue(data)
                    joinClub.postValue(ViewState.SUCCESS)
                }
                is Response.NotExist -> joinClub.postValue(ViewState.NOT_EXIST)
                else -> joinClub.postValue(ViewState.FAILURE)
            }
        }
    }

    fun createClub(club: Club) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.createClub(club)) {
                is Response.Success<Club> -> {
                    val data = response.data!!
                    data.roomMembers.add(ClubMember(Session.person.name, Session.person.id))
                    Session.person.joinedClubs.add(JoinedClub(data.roomId, data.roomName))

                    repository.updateClub(data)
                    repository.updatePerson(Session.person)

                    clubLiveData.postValue(data)
                    createClub.postValue(ViewState.SUCCESS)
                }
                is Response.AlreadyExist -> createClub.postValue(ViewState.ALREADY_EXIST)
                else -> createClub.postValue(ViewState.FAILURE)
            }
        }
    }

    fun updateClubAnnouncement(clubId: String, announcement: Announcement) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.updateAnnouncement(clubId, announcement)) {
                is Response.Success<Club> -> {
                    val data = response.data!!
                    clubLiveData.postValue(data)
                }
                else -> createClub.postValue(ViewState.FAILURE)
            }
        }
    }

    fun kickMember(clubId: String, memberId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getClub(clubId)) {
                is Response.Success<Club> -> {
                    val data = response.data!!
                    val member = data.roomMembers.find {
                        it.id == memberId
                    }

                    data.roomMembers.remove(member)
                    repository.updateClub(data)

                    when (val memberResponse = repository.getPerson(memberId)) {
                        is Response.Success<Person> -> {
                            val personData = memberResponse.data!!
                            val joinedClub = personData.joinedClubs.find {
                                it.roomId == clubId
                            }
                            personData.joinedClubs.remove(joinedClub)
                            repository.updatePerson(personData)
                            clubLiveData.postValue(data)
                        }
                        else -> {

                        }
                    }
                }
                else -> createClub.postValue(ViewState.FAILURE)
            }
        }
    }

    fun login(id: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getPerson(id)) {
                is Response.Success<Person> -> {
                    val data = response.data!!
                    if (data.password == password) {
                        Session.person = data
                        login.postValue(ViewState.SUCCESS)
                    }
                    else login.postValue(ViewState.WRONG_PASSWORD)
                }
                is Response.NotExist -> login.postValue(ViewState.NOT_EXIST)
                else -> login.postValue(ViewState.FAILURE)
            }
        }
    }

    fun signUp(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.createPerson(person)) {
                is Response.Success<Person> -> signUp.postValue(ViewState.SUCCESS )
                is Response.AlreadyExist -> signUp.postValue(ViewState.ALREADY_EXIST)
                else -> signUp.postValue(ViewState.FAILURE)
            }
        }
    }
}

enum class ViewState {
    SUCCESS,
    FAILURE,
    NOT_EXIST,
    ALREADY_EXIST,
    WRONG_PASSWORD,
}