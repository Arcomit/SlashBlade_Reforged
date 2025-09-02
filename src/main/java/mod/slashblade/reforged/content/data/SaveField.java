package mod.slashblade.reforged.content.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SaveField {

    /***
     * 自定义 StreamCodec 获取方法名
     * 如果不为空，将通过反射调用指定的静态方法来获取 StreamCodec
     * 方法签名应为: public static StreamCodec<ByteBuf, ?> methodName()
     */
    String customCodecMethod() default "";

    /***
     * 表示参数是否可以为空，将使用CanBeNullStreamCodec封装
     */
    boolean canBeNull() default false;

}
