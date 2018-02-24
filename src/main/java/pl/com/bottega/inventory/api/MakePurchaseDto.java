package pl.com.bottega.inventory.api;

public abstract class MakePurchaseDto {


    private Boolean success;

    public MakePurchaseDto(Boolean success) {
        this.success = success;
    }

    public MakePurchaseDto() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
