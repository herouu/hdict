package io.github.herouu;

import picocli.CommandLine;

@CommandLine.Command(name = "test", description = "词典命令行工具")
public class CommandTest implements Runnable {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = {"-A", "--all"})
    private boolean allFiles;

    public static void main(String[] args) {
        int execute = new CommandLine(new CommandTest()).execute(args);
    }

    @Override
    public void run() {
        if (allFiles) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }
        spec.commandLine().usage(System.out);
    }
}
