package freeman.ollie;

import freeman.ollie.exception.WordAnalyserException;
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

                logger.info("Starting Word Analyser service");

                AnalyserService analyserService = new AnalyserService(path);
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
        return options;
    }

    private static String fullVersion() {
        return "word-analyser " + version();
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
