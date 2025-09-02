package mod.slashblade.reforged.utils.tuple;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: til
 * @Description: 三元组封装类，用于存储三个不同类型的值
 * @param <A> 第一个值的类型
 * @param <B> 第二个值的类型
 * @param <C> 第三个值的类型
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tuple3<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    /**
     * 创建三元组的静态工厂方法
     * @param a 第一个值
     * @param b 第二个值
     * @param c 第三个值
     * @param <A> 第一个值的类型
     * @param <B> 第二个值的类型
     * @param <C> 第三个值的类型
     * @return 新的三元组实例
     */
    public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c) {
        return new Tuple3<>(a, b, c);
    }
}
