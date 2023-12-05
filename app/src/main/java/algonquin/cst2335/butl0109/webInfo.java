package algonquin.cst2335.butl0109;

public class webInfo {

    private String jImageUrl;
    private int jIdInfo;
    private String jTitleInfo;

    public webInfo(String imageUrl, String titleInfo, int idInfo) {

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

    public int getIdInfo(){
        return jIdInfo;
    }
}
