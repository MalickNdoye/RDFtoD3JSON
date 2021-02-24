import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONConverter {
    String filename ;
    HashMap<String,Integer> nodes ;
    HashMap<String,Color> nodesColors ;
    ArrayList<Link> links ;
    Boolean completedURI ;

    public JSONConverter(String filename){
        this.filename = filename;
        nodes = new HashMap<>();
        nodesColors = new HashMap<>() ;
        links = new ArrayList<>();
        completedURI = false ;
    }

    public void convert(GraphRDF graphRDF){
        if (completedURI){
            graphRDF.completeAllUri();
        }
        int index = 0 ;
        ArrayList<Triple> triples = graphRDF.triples;
        for (Triple triple : triples){
            Link link = new Link();
            if(!nodes.containsKey(triple.subject.uri)){
                nodes.put(triple.subject.uri, index) ;
                nodesColors.put(triple.subject.uri, triple.subject.color);
                link.source = triple.subject.uri;
                index++;
            }else{
                link.source = triple.subject.uri;
            }
            if(!nodes.containsKey(triple.object.uri)){
                nodes.put(triple.object.uri, index) ;
                nodesColors.put(triple.object.uri, triple.object.color);
                link.target = triple.object.uri;
                index++;
            }else{
                link.target = triple.object.uri;
            }
            link.property = triple.predicate.uri ;
            links.add(link) ;
        }
    }

    public void writeJON(){
        try{
            PrintWriter writer = new PrintWriter(filename) ;
            writer.write("{\n\t\"nodes\" : [\n");
            int i = 0 ;
            int j = 0 ;
            for(String str : nodes.keySet()){
        //{"id": "node0", "color": "#69b3a2", "name": "http://dbpedia.org/resource/Airbus"},
                writer.write(String.format("\t\t{\"id\": \"node%d\", ", nodes.get(str)));
                writer.write(String.format("\"color\": \"%s\", ", nodesColors.get(str).getNodeColor()));
                writer.write(String.format("\"name\": \"%s\"}", this.format(str)));
                i++ ;
                if(i < nodes.keySet().size()){
                    writer.write(",\n");
                }else{
                    writer.write("\n");
                }
            }
            writer.write("\t],\n\t\"links\" : [\n");
            for(Link link : links){
        //{"source": "node0", "target" : "node1", "property": "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"},
                writer.write(String.format("\t\t{\"source\": \"node%d\", ", nodes.get(link.source)));
                writer.write(String.format("\"target\": \"node%d\", ", nodes.get(link.target)));
                writer.write(String.format("\"property\": \"%s\"}", link.property));
                j++ ;
                if(j < links.size()){
                    writer.write(",\n");
                }else{
                    writer.write("\n");
                }
            }
            writer.write("\t]\n}");
            writer.flush();
            writer.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String format(String str) {
        String temp = str;
        if(str.startsWith("\"")){
            temp = str.substring(1);
        }
        if (str.endsWith("\"")){
            temp = temp.substring(0,temp.lastIndexOf('\"')) ;
        }
        return temp ;
    }


}
