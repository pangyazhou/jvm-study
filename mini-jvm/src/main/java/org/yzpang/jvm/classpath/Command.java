package org.yzpang.jvm.classpath;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 程序执行命令
 * Date: 2025/3/24 下午2:49
 **/
@Data
public class Command {
    private boolean helpFlag;
    private boolean versionFlag;
    private String cpOption;
    private String jreOption;
    private String clazz;
    private String[] args;

    public static Command parse(String[] args) {
        Command command = new Command();
        Options options = new Options();
        options.addOption("h", "help", false, "显示帮助信息");
        options.addOption("v", "version", false, "显示版本信息");
        options.addOption("cp", "classpath", true, "执行类路径");
        options.addOption("Xjre", "JRE路径");

        DefaultParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String[] cmdArgs = cmd.getArgs();
            if (cmd.hasOption("v")) {
                command.setVersionFlag(true);
            }
            if (cmd.hasOption("h")) {
                command.setHelpFlag(true);
            }
            if (cmd.hasOption("cp")) {
                command.setCpOption(cmd.getOptionValue("cp"));
            }
            if (cmd.hasOption("Xjre")) {
                command.setJreOption(cmd.getOptionValue("Xjre"));
            }
            if (cmdArgs.length > 0) {
                command.setClazz(cmdArgs[0]);
                args = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length );
                command.setArgs(args);
            }
        } catch (ParseException e) {
            System.out.println("解析异常: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return command;
    }
}
