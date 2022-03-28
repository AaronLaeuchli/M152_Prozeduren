package ch.bbw.aal.Procedure_M152.model;

public class ImageInfo {
    private Long imageId;
    private String name;
    private String type;

    public ImageInfo(Long imageId, String name, String type) {
        this.imageId = imageId;
        this.name = name;
        this.type = type;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
