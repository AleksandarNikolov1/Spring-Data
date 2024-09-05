package exam.model.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class LaptopSeedDto {

    @Expose
    @Size(min = 9)
    private String macAddress;
    @Expose
    @Positive
    private Double cpuSpeed;
    @Expose
    @Min(8)
    @Max(128)
    private Integer ram;
    @Expose
    @Min(128)
    @Max(1024)
    private Integer storage;
    @Expose
    @Size(min = 10)
    private String description;

    @Expose
    @Positive
    private BigDecimal price;
    @Expose
    private String warrantyType;

    @Expose
    @SerializedName("shop")
    private ShopNameDto shopNameDto;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopNameDto getShopNameDto() {
        return shopNameDto;
    }

    public void setShopNameDto(ShopNameDto shopNameDto) {
        this.shopNameDto = shopNameDto;
    }
}
