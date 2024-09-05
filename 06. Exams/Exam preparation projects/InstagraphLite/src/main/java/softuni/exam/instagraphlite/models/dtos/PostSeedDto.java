package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDto {

    @XmlElement(name = "caption")
    @NotBlank
    @Size(min = 21)
    private String caption;

    @XmlElement(name = "user")
    private UserNameDto userNameDto;

    @XmlElement(name = "picture")
    private PicturePathDto picturePathDto;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserNameDto getUserNameDto() {
        return userNameDto;
    }

    public void setUserNameDto(UserNameDto userNameDto) {
        this.userNameDto = userNameDto;
    }

    public PicturePathDto getPicturePathDto() {
        return picturePathDto;
    }

    public void setPicturePathDto(PicturePathDto picturePathDto) {
        this.picturePathDto = picturePathDto;
    }
}


