public class Triple {
    Node subject ;
    Node predicate ;
    Node object ;

    public Triple(){
        subject = new Node("anonymous") ;
        predicate = new Node("anonymous") ;
        object = new Node("anonymous") ;
    }

    public Triple(String subject,String predicate,String object){
        this.subject = new Node(subject) ;
        this.predicate = new Node(predicate) ;
        this.object = new Node(object) ;
    }

    public Triple(Node s,Node p, Node o){
        this.subject = s ;
        this.predicate = p ;
        this.object = o ;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "subject='" + subject + '\'' +
                ", predicate='" + predicate + '\'' +
                ", object='" + object + '\'' +
                '}';
    }

    public void injectPrefix(String pfx, String uri){
        Node temp ;
        for (int i =0 ; i<3; i++){
            switch (i){
                case 0:
                    temp = subject ;break;
                case 1:
                    temp = predicate ;break;
                case 2:
                    temp =object ;break;
                default:
                    temp = null ;break;
            }
            String[] tab = temp.uri.split(":");
            if (tab[0].equals(pfx) && !tab[0].equals("_")){
                switch (i){
                    case 0:
                        subject.uri = "<" + uri+tab[1]+">" ;break;
                    case 1:
                        predicate.uri ="<" + uri+tab[1]+">" ;break;
                    case 2:
                        object.uri = "<" + uri+tab[1]+">" ;break;
                    default:
                        break;
                }
            }
        }
    }

    public String[] prefixesUsed(){
        String[] pfxs = new String[3] ;
        pfxs[0] = getPrefix(subject.uri) ;
        pfxs[1] = getPrefix(predicate.uri) ;
        pfxs[2] = getPrefix(object.uri) ;
        return pfxs ;
    }

    public String getPrefix(String uri){
        String[] tab = uri.split(":");
        if (tab.length == 2 && uri.charAt(0) != '<' && !uri.endsWith(">")) {
            return tab[0] ;
        }
        return "none" ;
    }
}
