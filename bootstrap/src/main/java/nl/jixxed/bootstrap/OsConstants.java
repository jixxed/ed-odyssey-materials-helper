/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.bootstrap;


class OsConstants {
    static String KILL_COMMAND;
    static String START_COMMAND;
    static String UPDATE_FILE_SUFFIX;
    static String VERSION_FILE;

    static {
        switch (OsCheck.getOperatingSystemType()) {
            case MacOS, Other -> throw new IllegalArgumentException("OS not supported.");
            case Linux -> setLinux();
            case Windows -> setWindows();
        }
    }

    private OsConstants() {
    }

    private static void setWindows() {
        UPDATE_FILE_SUFFIX = "portable.zip";
        KILL_COMMAND = "taskkill /F /IM \"Elite Dangerous Odyssey Materials Helper.exe\"";
        START_COMMAND = "cmd /c \"\"%s\\Elite Dangerous Odyssey Materials Helper.exe\"\"";
        VERSION_FILE = "%s\\app\\Elite Dangerous Odyssey Materials Helper.cfg";
    }

    private static void setLinux() {
        UPDATE_FILE_SUFFIX = ".portable.linux.zip";
        KILL_COMMAND = "ps aux | grep 'Elite Dangerous Odyssey Materials Helper' | grep -v 'grep' | awk '{ print $2 }' | xargs kill -SIGTERM";
        START_COMMAND = "%s/lib/runtime/bin/Elite Dangerous Odyssey Materials Helper";
        VERSION_FILE = "%s/lib/app/Elite Dangerous Odyssey Materials Helper.cfg";
    }
}
