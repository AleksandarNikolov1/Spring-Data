package com.example.football.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto {

    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;

    @XmlElement(name = "email")
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement(name = "position")
    private String position;

    @XmlElement(name = "town")
    private TownNameDto townNameDto;
    @XmlElement(name = "team")
    private TeamNameDto teamNameDto;

    @XmlElement(name = "stat")
    private StatIdDto statIdDto;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TownNameDto getTownNameDto() {
        return townNameDto;
    }

    public void setTownNameDto(TownNameDto townNameDto) {
        this.townNameDto = townNameDto;
    }

    public StatIdDto getStatIdDto() {
        return statIdDto;
    }

    public void setStatIdDto(StatIdDto statIdDto) {
        this.statIdDto = statIdDto;
    }

    public TeamNameDto getTeamNameDto() {
        return teamNameDto;
    }

    public void setTeamNameDto(TeamNameDto teamNameDto) {
        this.teamNameDto = teamNameDto;
    }
}


//<player>
//        <first-name>L</first-name>
//        <last-name>Smallbone</last-name>
//        <email>lsmallbone0@hubpages.com</email>
//        <birth-date>21/02/1979</birth-date>
//        <position>ATT</position>
//        <town>
//            <name>Kazan</name>
//        </town>
//        <team>
//            <name>McGlynn</name>
//        </team>
//        <stat>
//            <id>53</id>
//        </stat>
//    </player>