/**
 * 
 */
package com.ideyatech.core.web;

import jargs.gnu.CmdLineParser;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * @author allantan
 *
 */
public class LowTide {

    public static void main(String args[]) {

        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option verboseOpt = parser.addBooleanOption('v', "verbose");
        CmdLineParser.Option nomungeOpt = parser.addBooleanOption("nomunge");
        CmdLineParser.Option linebreakOpt = parser.addStringOption("line-break");
        CmdLineParser.Option preserveSemiOpt = parser.addBooleanOption("preserve-semi");
        CmdLineParser.Option disableOptimizationsOpt = parser.addBooleanOption("disable-optimizations");
        CmdLineParser.Option helpOpt = parser.addBooleanOption('h', "help");
        CmdLineParser.Option charsetOpt = parser.addStringOption("charset");
        CmdLineParser.Option outputFilenameOpt = parser.addStringOption('o', "output");

        Reader in = null;
        Writer out = null;

        try {

            parser.parse(args);

            Boolean help = (Boolean) parser.getOptionValue(helpOpt);
            if (help != null && help.booleanValue()) {
                usage();
                System.exit(0);
            }

            boolean verbose = parser.getOptionValue(verboseOpt) != null;

            // Check the JVM vendor
            if (verbose) {
                String jvmVendor = System.getProperty("java.vendor");
                if (!jvmVendor.equalsIgnoreCase("Sun Microsystems Inc.")) {
                    System.err.println("\n[INFO] It is recommended to use Sun Microsystems' JVM [java.vendor = " + jvmVendor + "]");
                }
            }

            String charset = (String) parser.getOptionValue(charsetOpt);
            if (charset == null || !Charset.isSupported(charset)) {
                charset = System.getProperty("file.encoding");
                if (charset == null) {
                    charset = "UTF-8";
                }
                if (verbose) {
                    System.err.println("\n[INFO] Using charset " + charset);
                }
            }
            
            boolean munge = parser.getOptionValue(nomungeOpt) == null;
            
            boolean preserveAllSemiColons = parser.getOptionValue(preserveSemiOpt) != null;
            
            boolean disableOptimizations = parser.getOptionValue(disableOptimizationsOpt) != null;
            
            String[] fileArgs = parser.getRemainingArgs();
            String inputFilename = "";
            
            if (fileArgs.length == 0) {
                usage();
                System.exit(1);
            } else {
                inputFilename = fileArgs[0];
            }

            int linebreakpos = -1;
            String linebreakstr = (String) parser.getOptionValue(linebreakOpt);
            if (linebreakstr != null) {
                try {
                    linebreakpos = Integer.parseInt(linebreakstr, 10);
                } catch (NumberFormatException e) {
                    usage();
                    System.exit(1);
                }
            }

            String outputFilename = (String) parser.getOptionValue(outputFilenameOpt);
            WebOptimizer wo = new WebOptimizer();
            wo.setCharset(charset);
            wo.setDisableOptimizations(disableOptimizations);
            wo.setLinebreakpos(linebreakpos);
            wo.setMunge(munge);
            wo.setPreserveAllSemiColons(preserveAllSemiColons);
            wo.setVerbose(verbose);
            wo.processHTML(inputFilename, outputFilename);

        } catch (CmdLineParser.OptionException e) {
            usage();
            System.exit(1);
        } catch (Exception e) {
			e.printStackTrace();
            usage();
            System.exit(1);
		} 
    }

    private static void usage() {
        System.out.println(
                "\nUsage: java -jar low-tide.jar [options] [input file]\n\n"

                        + "Global Options\n"
                        + "  -h, --help                Displays this information\n"
                        + "  --charset <charset>       Read the input file using <charset>\n"
                        + "  --line-break <column>     Insert a line break after the specified column number\n"
                        + "  -v, --verbose             Display informational messages and warnings\n"
                        + "  -o <file>                 Place the output into <file>. Defaults to stdout.\n\n"

                        + "JavaScript Options\n"
                        + "  --nomunge                 Minify only, do not obfuscate\n"
                        + "  --preserve-semi           Preserve all semicolons\n"
                        + "  --disable-optimizations   Disable all micro optimizations\n\n"

                        + "If no input file is specified, it defaults to stdin. In this case, the 'type'\n"
                        + "option is required. Otherwise, the 'type' option is required only if the input\n"
                        + "file extension is neither 'js' nor 'css'.\n\n"
                        + "NOTE: This utility is an extension of YUI Compressor.");
    }

}
