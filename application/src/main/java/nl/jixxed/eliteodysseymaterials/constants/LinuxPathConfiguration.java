/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.constants;

public class LinuxPathConfiguration implements PathConfiguration {
    private static final String CONFIG_DIRECTORY;
    private static final String DEFAULT_WATCHED_FOLDER;
    private static final String OS_SLASH;

    static {
        String userHome = System.getProperty("user.home");
        CONFIG_DIRECTORY = userHome + "/.config/odyssey-materials-helper";
        DEFAULT_WATCHED_FOLDER = userHome + "/.steam/steam/steamapps/compatdata/359320/pfx/drive_c/users/steamuser/Saved Games/Frontier Developments/Elite Dangerous";
        OS_SLASH = "/";
    }


    public String getConfigDirectory() {
        return CONFIG_DIRECTORY;
    }

    public String getDefaultWatchedFolder() {
        return DEFAULT_WATCHED_FOLDER;
    }

    public String getOsSlash() {
        return OS_SLASH;
    }
}
