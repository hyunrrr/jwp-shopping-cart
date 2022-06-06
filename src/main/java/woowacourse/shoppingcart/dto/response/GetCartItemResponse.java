package woowacourse.shoppingcart.dto.response;

public class GetCartItemResponse {

    private Long id;
    private String name;
    private Integer price;
    private String thumbnail;

    public GetCartItemResponse() {
    }

    public GetCartItemResponse(Long id, String name, Integer price, String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
