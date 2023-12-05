package algonquin.cst2335.butl0109;

public class WebInfo {

    /** stores url for image to be displayed */
    private String jImageUrl;

    /** stores id read from JSON data */
    private String jIdInfo;

    /** stores title read from JSON data */
    private String jTitleInfo;

    public WebInfo(String imageUrl, String titleInfo, String idInfo) { /** constructor used for populating adapter */

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
