package algonquin.cst2335.butl0109;

public class WebInfo {

    private String jImageUrl;
    private String jIdInfo;
    private String jTitleInfo;

    public WebInfo(String imageUrl, String titleInfo, String idInfo) {

        jImageUrl = imageUrl;
        jTitleInfo = titleInfo;
        jIdInfo = idInfo;
    }

    public String getImageUrl(){
        return jImageUrl;
    }

    public String getTitle(){
        return jTitleInfo;
    }

    public String getIdInfo(){
        return jIdInfo;
    }
}
