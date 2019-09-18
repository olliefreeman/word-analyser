package freeman.ollie;

import freeman.ollie.exception.WordAnalyserException;
import freeman.ollie.util.Service;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @since 09/09/2019
 */
public class WordAnalyser {

    private static final Logger logger = LoggerFactory.getLogger(WordAnalyser.class);

    private static final CommandLineParser parser = new DefaultParser();

    public static void main(String[] args) throws WordAnalyserException {

        // parse the command line arguments
        try {
            CommandLine line = parser.parse(defineOptions(), args);

            if (line.hasOption('h')) help();
            else if (line.hasOption('v')) System.out.println(fullVersion());
            else if (line.hasOption('f')) {
                Path path = Paths.get(line.getOptionValue('f'));

                Service analyserService = getService(line, path);
                logger.info("Starting Word Analyser service using {}", analyserService.getClass().getSimpleName());
                analyserService.analyse();
                logger.info("Analysis Results:\n{}", analyserService.getAnalysisString());

            } else help();
        } catch (ParseException e) {
            logger.error(e.getMessage());
            help();
        }
    }

    private static Options defineOptions() {

        Options options = new Options();
        OptionGroup mainGroup = new OptionGroup();
        mainGroup.addOption(
            Option.builder("f").longOpt("file")
                .argName("FILE")
                .hasArg().required()
                .desc("The file to be analysed")
                .build());
        mainGroup.addOption(Option.builder("h").longOpt("help").build());
        mainGroup.addOption(Option.builder("v").longOpt("version").build());
        options.addOptionGroup(mainGroup);

        OptionGroup languageGroup = new OptionGroup();
        languageGroup.addOption(
            Option.builder("l").longOpt("language")
                .argName("LANGUAGE")
                .hasArg().required()
                .desc("Programming language to use for analysis. One of 'java' or 'groovy', default is 'java'.").build()
                               );
        languageGroup.addOption(Option.builder("j").longOpt("java").desc("Shorthand option for --language=java").build());
        languageGroup.addOption(Option.builder("g").longOpt("groovy").desc("Shorthand option for --language=groovy").build());
        options.addOptionGroup(languageGroup);

        OptionGroup threadingGroup = new OptionGroup();
        threadingGroup.addOption(
            Option.builder("s").longOpt("single").desc("Use single threaded service").build()
                                );
        threadingGroup.addOption(
            Option.builder("m").longOpt("full-multi")
                .desc("Use full multi threaded service. This will thread the word cleaning as well." +
                      "Default option using Groovy").build()
                                );
        threadingGroup.addOption(
            Option.builder("p").longOpt("partial-multi")
                .desc("Use partial multi threaded service. This will not thread the word cleaning. " +
                      "Default option using Java, not available in Groovy").build()
                                );

        options.addOptionGroup(threadingGroup);
        return options;
    }

    private static String fullVersion() {
        return "word-analyser " + version();
    }

    private static String getLanguage(CommandLine line) {
        if (line.hasOption('g')) return "groovy";
        if (line.hasOption('j')) return "java";
        return line.hasOption('l') ? line.getOptionValue('l') : "java";
    }

    private static Service getService(CommandLine line, Path path) {
        switch (getLanguage(line)) {
            case "groovy":
                if(line.hasOption('s')) return new GroovySingleThreadedAnalyserService(path);
                return new GroovyAnalyserService(path);
            case "java":
            default:
                if(line.hasOption('s')) return new SingleThreadedAnalyserService(path);
                if(line.hasOption('m')) return new AnalyserService(path,true);
                return new AnalyserService(path, false);
        }
    }

    private static void help() {
        HelpFormatter formatter = new HelpFormatter();

        String header = "Analyse word contents of file.\n" +
                        "Read the provided file in and analyse contents at a word level\n\n";
        String footer = "\n" + version() + "\n\nPlease report issues to ollie.freeman@gmail.com \n";

        formatter.printHelp(120,
                            "word-analyser -f <FILE>",
                            header, defineOptions(), footer, false);
    }

    private static String version() {
        String version = WordAnalyser.class.getPackage().getSpecificationVersion();
        if (version == null) version = "1.0-SNAPSHOT";

        return "  Version: \"" + version + "\"\n" +
               "  Java Version: \"" + System.getProperty("java.version") + "\"";
    }

}
