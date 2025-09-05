package mod.slashblade.reforged.utils;

/**
 * @author til
 */
public class Util {
    /***
     * 强行转换
     * @return 转换后
     */
    public static <E> E forcedConversion(Object obj) {
        //noinspection unchecked
        return (E) obj;
    }
}
