import java.util.ArrayList;
import java.util.HashMap;

public class GraphRDF {
    HashMap<String,String> prefixes ;
    ArrayList<Triple> triples ;
    ArrayList<String> headers ;

    public GraphRDF(){
        prefixes = new HashMap<>();
        triples = new ArrayList<>();
        headers = new ArrayList<>();
    }

    public void addPrefix(String prefix,String uri){
        String pfx ;
        String url ;
        if(prefix.endsWith(":")){
            pfx = prefix.substring(0,prefix.lastIndexOf(':'));
        }else{
            pfx = prefix ;
        }
        if(uri.contains("<") && uri.contains(">")){
            url = uri.substring(uri.lastIndexOf('<')+1,uri.lastIndexOf('>'));
        }else{
            url = uri ;
        }
        prefixes.put(pfx,url) ;
    }

    public void addTriple(String s,String p,String o){
        Node subject = new Node(s) ;
        Node predicate = new Node(p) ;
        Node object = new Node(o);
        if (headers.contains(s)){
            subject.setHeader(true);
        }
        if (headers.contains(o)){
            object.setHeader(true);
        }
        triples.add(new Triple(subject,predicate,object)) ;
    }

    @Override
    public String toString() {
        return "Converter{" +
                "prefixes=" + prefixes +
                ",\n triples=" + triples +
                '}';
    }

    public void completeAllUri(){
        if(!prefixes.isEmpty()) {
            for (Triple triple : triples) {
                String[] tab = triple.prefixesUsed();
                for (String str : tab) {
                    if (!str.equals("none")) {
                        triple.injectPrefix(str, prefixes.get(str));
                    }
                }
            }
        }
    }

    public void addHeaders(String head){
        headers.add(head) ;
    }

}
