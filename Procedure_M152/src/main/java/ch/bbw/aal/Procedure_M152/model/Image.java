package ch.bbw.aal.Procedure_M152.model;

import javax.persistence.*;

@Entity
@Table(name = "imageDB")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "picsmall")
    private byte[] picSmall;

    @Lob
    @Column(name = "picWatermarked")
    private byte[] picWatermarked;

    @Lob
    @Column(name = "pic")
    private byte[] pic;

    public Image() {
    }

    public Image(Long imageId, String name, String type, byte[] picSmall, byte[] picWatermarked, byte[] pic) {
        this.imageId = imageId;
        this.name = name;
        this.type = type;
        this.picSmall = picSmall;
        this.picWatermarked = picWatermarked;
        this.pic = pic;
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

    public byte[] getPicSmall() {
        return picSmall;
    }

    public void setPicSmall(byte[] picSmall) {
        this.picSmall = picSmall;
    }

    public byte[] getPicWatermarked() {
        return picWatermarked;
    }

    public void setPicWatermarked(byte[] picWatermarked) {
        this.picWatermarked = picWatermarked;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}