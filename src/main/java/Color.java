public enum Color {
    URI_NODES("#0dab57"),
    BLANKNODES("#eef036"),
    HEADERS("#ec3110");

    private String nodeColor ;

    Color(String color){
        this.nodeColor = color ;
    }

    public String getNodeColor() {
        return nodeColor;
    }
}
