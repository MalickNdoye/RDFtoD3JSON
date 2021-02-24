import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
        String[] outputFiles = new String[0];
        String[] files = new String[0];
        String outputDirectory ;

        //PARSER
        CommandLine commandLine ;
        Option option_f = Option.builder("f").argName("input").hasArgs().valueSeparator(' ')
                .desc("Fichier à convertir").optionalArg(true).build();
        Option option_o = Option.builder("o").argName("output").hasArg()
                .desc("Répertoire de sortie").optionalArg(true).build();
        Option option_h = Option.builder("h").argName("help").longOpt("help")
                .desc("Aide").build();


        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption(option_f);
        options.addOption(option_o);
        options.addOption(option_h);

        try{
            if (args.length==0){
                System.out.println("Erreur : pas assez d'argument.");
                System.exit(0);
            }

            commandLine = parser.parse(options, args);
            if (commandLine.hasOption("help")) {
                formatter.printHelp("CLIsample", options, true);
                System.exit(0);
            }
            if (commandLine.hasOption('o')){
                outputDirectory = commandLine.getOptionValue('o');
                if (!outputDirectory.endsWith("/")){
                    outputDirectory = outputDirectory + "/" ;
                }
            } else {
                outputDirectory = "./" ; //local directory of execution
            }
            if (commandLine.hasOption('f')){
                files = commandLine.getOptionValues("f");
                if (files.length >= 1){
                    outputFiles = new String[files.length] ;
                    for(int i=0; i<files.length;i++){
                        outputFiles[i] = outputDirectory + files[i].substring(files[i].lastIndexOf('/')+1,
                                files[i].lastIndexOf('.')) + ".json" ;
                    }
                }
            }else{
                throw new ParseException("L'option f est obligatoire et requiert un argument.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for(int i=0;i<files.length;i++){
            FileLoader fl = new FileLoader(files[i]);
            JSONConverter jsonConverter = new JSONConverter(outputFiles[i]);
            GraphRDF graphRDF = fl.load(files[i]);
            jsonConverter.completedURI = true ;
            jsonConverter.convert(graphRDF);
            jsonConverter.writeJON();

        }
    }
}
