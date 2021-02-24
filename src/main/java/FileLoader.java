import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileLoader {
    String filename ;

    public static final ArrayList<String> rdfExtension = new ArrayList<>() {{
        add("n3");
        add("ttl");
        add("jsonld");
    }};


    //public FileLoader(){
    //    filename="unkown";
    //}

    public FileLoader(String filename){
        this.filename = filename;
    }

    public GraphRDF load(String filename){
        this.filename = filename ;
        return this.load() ;
    }

    public GraphRDF load(){
        if(filename.equals("unkown") || !(new File(filename).exists())){
            System.err.println("Le fichier "+filename+" n'existe pas.");
            return new GraphRDF();
        }
        GraphRDF graphRDF = new GraphRDF();
        InputStream ips = null;
        InputStreamReader ipsr = null;
        BufferedReader br = null;
        try {
            ips = new FileInputStream(filename);
            ipsr = new InputStreamReader(ips);
            br = new BufferedReader(ipsr);
            Pattern prefixPattern = Pattern.compile("@prefix .*");
            Pattern headPattern = Pattern.compile("head .*");
            Pattern selectPattern = Pattern.compile("SELECT");
            //Pattern wherePattern = Pattern.compile("WHERE");
            String ligne;
            while ((ligne = br.readLine()) != null) {
                Matcher prefixMatcher = prefixPattern.matcher(ligne);
                Matcher headMatcher = headPattern.matcher(ligne);
                Matcher selectMatcher = selectPattern.matcher(ligne);
                //Matcher whereMatcher = wherePattern.matcher(ligne);
                if (prefixMatcher.find()) {
                    String[] words = ligne.split(" ");
                    if (words.length >= 3) {
                        graphRDF.addPrefix(words[1], words[2]);
                    }
                } else if (headMatcher.find() || selectMatcher.find()) {
                    String[] heads = ligne.split(" ");
                    for (int i = 1; i < heads.length; i++) {
                        if (!heads[i].toLowerCase().equals("distinct")) {
                            graphRDF.addHeaders(heads[i]);
                        }
                    }
                }else{
                    String[] words = ligne.split(" ");
                    if (words.length >= 3){
                        graphRDF.addTriple(words[0],words[1],words[2]);
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (ipsr != null) {
                    ipsr.close();
                }
                if (ips != null) {
                    ips.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return graphRDF;
    }

}
