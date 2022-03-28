package ch.bbw.aal.Procedure_M152.model;

import java.util.List;

public class ImageInfoResponse {
    List<ImageInfo> imageInfoList;

    public ImageInfoResponse(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }
}
