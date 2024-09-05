package softuni.exam.instagraphlite.models.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PictureSeedDto {
    @Expose
    @NotBlank
    private String path;
    @Expose
    @NotNull
    @Min(500)
    @Max(60000)
    private Double size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
