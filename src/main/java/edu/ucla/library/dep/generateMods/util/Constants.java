package edu.ucla.library.dep.generateMods.util;

import org.jdom2.Namespace;

import java.util.regex.Pattern;

public final class Constants {

    public static final Namespace namespace = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
    public static final Namespace namespaceXlink = Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink");
    public static final Namespace namespaceXsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    public static final Namespace namespaceCopyrightMD = Namespace.getNamespace("copyrightMD",
            "https://www.cdlib.org/inside/diglib/copyrightMD");
    public static final String TYPE = "type";
    public static final String DISPLAY_LABEL = "displayLabel";
    public static final String LANG = "lang";
    public static final String AUTHORITY = "authority";
    public static final String ENCODING = "encoding";
    public static final String DELIMIT = "|";
    public static final String delimitDot = ".";
    public static final String regex = "(?<!\\\\)" + Pattern.quote(DELIMIT);
    public static final String regexDot = "(?<!\\\\)" + Pattern.quote(delimitDot);
}
