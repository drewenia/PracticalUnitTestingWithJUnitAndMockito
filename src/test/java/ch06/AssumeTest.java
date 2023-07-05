package ch06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AssumeTest {
    @Test
    void shouldRunOnlyMacOS(){
        assumeTrue(thisIsAMacOSMachine());
        System.out.println("running on macOS");
    }
    private boolean thisIsAMacOSMachine(){
        return System.getProperty("os.name").startsWith("Mac");
    }

    @Test
    void shouldRunOnlyLinux(){
        assumeTrue(thisIsALinuxMachine());
        System.out.println("running on linux");
    }

    private boolean thisIsALinuxMachine(){
        return System.getProperty("os.name").startsWith("Linux");
    }
}
