//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.langmy.jFinal.common.utils.ext.matcher;


import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class AntPathMatcher implements PatternMatcher {
    public static final String DEFAULT_PATH_SEPARATOR = "/";
    private String pathSeparator = "/";

    public AntPathMatcher() {
    }

    public void setPathSeparator(String pathSeparator) {
        this.pathSeparator = pathSeparator != null?pathSeparator:"/";
    }

    public boolean isPattern(String path) {
        return path.indexOf(42) != -1 || path.indexOf(63) != -1;
    }

    public boolean matches(String pattern, String source) {
        return this.match(pattern, source);
    }

    public boolean match(String pattern, String path) {
        return this.doMatch(pattern, path, true);
    }

    public boolean matchStart(String pattern, String path) {
        return this.doMatch(pattern, path, false);
    }

    protected boolean doMatch(String pattern, String path, boolean fullMatch) {
        if(path.startsWith(this.pathSeparator) != pattern.startsWith(this.pathSeparator)) {
            return false;
        } else {
            String[] pattDirs = tokenizeToStringArray(pattern, this.pathSeparator);
            String[] pathDirs = tokenizeToStringArray(path, this.pathSeparator);
            int pattIdxStart = 0;
            int pattIdxEnd = pattDirs.length - 1;
            int pathIdxStart = 0;

            int pathIdxEnd;
            String i;
            for(pathIdxEnd = pathDirs.length - 1; pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd; ++pathIdxStart) {
                i = pattDirs[pattIdxStart];
                if("**".equals(i)) {
                    break;
                }

                if(!this.matchStrings(i, pathDirs[pathIdxStart])) {
                    return false;
                }

                ++pattIdxStart;
            }

            int var18;
            if(pathIdxStart > pathIdxEnd) {
                if(pattIdxStart > pattIdxEnd) {
                    return pattern.endsWith(this.pathSeparator)?path.endsWith(this.pathSeparator):!path.endsWith(this.pathSeparator);
                } else if(!fullMatch) {
                    return true;
                } else if(pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(this.pathSeparator)) {
                    return true;
                } else {
                    for(var18 = pattIdxStart; var18 <= pattIdxEnd; ++var18) {
                        if(!pattDirs[var18].equals("**")) {
                            return false;
                        }
                    }

                    return true;
                }
            } else if(pattIdxStart > pattIdxEnd) {
                return false;
            } else if(!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
                return true;
            } else {
                while(pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
                    i = pattDirs[pattIdxEnd];
                    if(i.equals("**")) {
                        break;
                    }

                    if(!this.matchStrings(i, pathDirs[pathIdxEnd])) {
                        return false;
                    }

                    --pattIdxEnd;
                    --pathIdxEnd;
                }

                if(pathIdxStart > pathIdxEnd) {
                    for(var18 = pattIdxStart; var18 <= pattIdxEnd; ++var18) {
                        if(!pattDirs[var18].equals("**")) {
                            return false;
                        }
                    }

                    return true;
                } else {
                    while(pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
                        var18 = -1;

                        int patLength;
                        for(patLength = pattIdxStart + 1; patLength <= pattIdxEnd; ++patLength) {
                            if(pattDirs[patLength].equals("**")) {
                                var18 = patLength;
                                break;
                            }
                        }

                        if(var18 == pattIdxStart + 1) {
                            ++pattIdxStart;
                        } else {
                            patLength = var18 - pattIdxStart - 1;
                            int strLength = pathIdxEnd - pathIdxStart + 1;
                            int foundIdx = -1;
                            int i1 = 0;

                            label140:
                            while(i1 <= strLength - patLength) {
                                for(int j = 0; j < patLength; ++j) {
                                    String subPat = pattDirs[pattIdxStart + j + 1];
                                    String subStr = pathDirs[pathIdxStart + i1 + j];
                                    if(!this.matchStrings(subPat, subStr)) {
                                        ++i1;
                                        continue label140;
                                    }
                                }

                                foundIdx = pathIdxStart + i1;
                                break;
                            }

                            if(foundIdx == -1) {
                                return false;
                            }

                            pattIdxStart = var18;
                            pathIdxStart = foundIdx + patLength;
                        }
                    }

                    for(var18 = pattIdxStart; var18 <= pattIdxEnd; ++var18) {
                        if(!pattDirs[var18].equals("**")) {
                            return false;
                        }
                    }

                    return true;
                }
            }
        }
    }

    private boolean matchStrings(String pattern, String str) {
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        boolean containsStar = false;
        char[] i = patArr;
        int patLength = patArr.length;

        int strLength;
        for(strLength = 0; strLength < patLength; ++strLength) {
            char foundIdx = i[strLength];
            if(foundIdx == 42) {
                containsStar = true;
                break;
            }
        }

        char ch;
        int var17;
        if(!containsStar) {
            if(patIdxEnd != strIdxEnd) {
                return false;
            } else {
                for(var17 = 0; var17 <= patIdxEnd; ++var17) {
                    ch = patArr[var17];
                    if(ch != 63 && ch != strArr[var17]) {
                        return false;
                    }
                }

                return true;
            }
        } else if(patIdxEnd == 0) {
            return true;
        } else {
            while((ch = patArr[patIdxStart]) != 42 && strIdxStart <= strIdxEnd) {
                if(ch != 63 && ch != strArr[strIdxStart]) {
                    return false;
                }

                ++patIdxStart;
                ++strIdxStart;
            }

            if(strIdxStart > strIdxEnd) {
                for(var17 = patIdxStart; var17 <= patIdxEnd; ++var17) {
                    if(patArr[var17] != 42) {
                        return false;
                    }
                }

                return true;
            } else {
                while((ch = patArr[patIdxEnd]) != 42 && strIdxStart <= strIdxEnd) {
                    if(ch != 63 && ch != strArr[strIdxEnd]) {
                        return false;
                    }

                    --patIdxEnd;
                    --strIdxEnd;
                }

                if(strIdxStart > strIdxEnd) {
                    for(var17 = patIdxStart; var17 <= patIdxEnd; ++var17) {
                        if(patArr[var17] != 42) {
                            return false;
                        }
                    }

                    return true;
                } else {
                    while(patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                        var17 = -1;

                        for(patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                            if(patArr[patLength] == 42) {
                                var17 = patLength;
                                break;
                            }
                        }

                        if(var17 == patIdxStart + 1) {
                            ++patIdxStart;
                        } else {
                            patLength = var17 - patIdxStart - 1;
                            strLength = strIdxEnd - strIdxStart + 1;
                            int var18 = -1;
                            int i1 = 0;

                            label132:
                            while(i1 <= strLength - patLength) {
                                for(int j = 0; j < patLength; ++j) {
                                    ch = patArr[patIdxStart + j + 1];
                                    if(ch != 63 && ch != strArr[strIdxStart + i1 + j]) {
                                        ++i1;
                                        continue label132;
                                    }
                                }

                                var18 = strIdxStart + i1;
                                break;
                            }

                            if(var18 == -1) {
                                return false;
                            }

                            patIdxStart = var17;
                            strIdxStart = var18 + patLength;
                        }
                    }

                    for(var17 = patIdxStart; var17 <= patIdxEnd; ++var17) {
                        if(patArr[var17] != 42) {
                            return false;
                        }
                    }

                    return true;
                }
            }
        }
    }

    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public String extractPathWithinPattern(String pattern, String path) {
        String[] patternParts = tokenizeToStringArray(pattern, this.pathSeparator);
        String[] pathParts = tokenizeToStringArray(path, this.pathSeparator);
        StringBuilder buffer = new StringBuilder();
        int puts = 0;

        int i;
        for(i = 0; i < patternParts.length; ++i) {
            String patternPart = patternParts[i];
            if((patternPart.indexOf(42) > -1 || patternPart.indexOf(63) > -1) && pathParts.length >= i + 1) {
                if(puts > 0 || i == 0 && !pattern.startsWith(this.pathSeparator)) {
                    buffer.append(this.pathSeparator);
                }

                buffer.append(pathParts[i]);
                ++puts;
            }
        }

        for(i = patternParts.length; i < pathParts.length; ++i) {
            if(puts > 0 || i > 0) {
                buffer.append(this.pathSeparator);
            }

            buffer.append(pathParts[i]);
        }

        return buffer.toString();
    }

    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if(str == null) {
            return null;
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            ArrayList tokens = new ArrayList();

            while(true) {
                String token;
                do {
                    if(!st.hasMoreTokens()) {
                        return toStringArray(tokens);
                    }

                    token = st.nextToken();
                    if(trimTokens) {
                        token = token.trim();
                    }
                } while(ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }

    public static String[] toStringArray(Collection collection) {
        return collection == null?null:(String[])((String[])collection.toArray(new String[collection.size()]));
    }
}
