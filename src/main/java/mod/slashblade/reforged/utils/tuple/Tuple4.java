package mod.slashblade.reforged.utils.tuple;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: til
 * @Description: 四元组封装类，用于存储四个不同类型的值
 * @param <A> 第一个值的类型
 * @param <B> 第二个值的类型
 * @param <C> 第三个值的类型
 * @param <D> 第四个值的类型
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tuple4<A, B, C, D> {
    private final A a;
    private final B b;
    private final C c;
    private final D d;

    /**
     * 创建四元组的静态工厂方法
     * @param a 第一个值
     * @param b 第二个值
     * @param c 第三个值
     * @param d 第四个值
     * @param <A> 第一个值的类型
     * @param <B> 第二个值的类型
     * @param <C> 第三个值的类型
     * @param <D> 第四个值的类型
     * @return 新的四元组实例
     */
    public static <A, B, C, D> Tuple4<A, B, C, D> of(A a, B b, C c, D d) {
        return new Tuple4<>(a, b, c, d);
    }
}
