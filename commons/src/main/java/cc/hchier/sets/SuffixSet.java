package cc.hchier.sets;

import java.util.HashSet;
import java.util.Set;

/**
 * @author by Hchier
 * @Date 2023/4/13 10:06
 */
public class SuffixSet {
    private static final Set<String> PIC_SET = new HashSet<String>() {{
        add("bmp");
        add("jpg");
        add("jpeg");
        add("png");
        add("gif");
    }};

    private static final Set<String> VIDEO_SET = new HashSet<String>() {{
        add("avi");
        add("wmv");
        add("mpg");
        add("mpeg");
        add("mov");
        add("rm");
        add("ram");
        add("swf");
        add("flv");
        add("mp4");
    }};

    public static boolean isPic(String suffix) {
        return PIC_SET.contains(suffix);
    }

    public static boolean isVideo(String suffix) {
        return VIDEO_SET.contains(suffix);
    }
}
