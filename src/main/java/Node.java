public class Node {
    String uri ;
    Color color ;
    Boolean isHead ;

    public Node(String uri){
        this.uri = uri ;
        this.isHead = false ;
        this.color = findColor(uri) ;
    }

    public Node(String uri,Boolean isHead){
        this.uri = uri ;
        this.isHead = isHead ;
        this.color = findColor(uri) ;
    }

    private Color findColor(String str){
        if (str.contains("http")){
            return Color.URI_NODES;
        } else {
            return Color.BLANKNODES;
        }
    }

    public void setHeader(Boolean b){
        this.isHead = b ;
        if(this.isHead){
            color = Color.HEADERS ;
        }
    }

    private String getColor(String str) {
        return color.getNodeColor() ;
    }
}
