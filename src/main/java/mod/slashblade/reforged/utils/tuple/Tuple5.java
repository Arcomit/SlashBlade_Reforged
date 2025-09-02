package mod.slashblade.reforged.utils.tuple;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: til
 * @Description: 五元组封装类，用于存储五个不同类型的值
 * @param <A> 第一个值的类型
 * @param <B> 第二个值的类型
 * @param <C> 第三个值的类型
 * @param <D> 第四个值的类型
 * @param <E> 第五个值的类型
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tuple5<A, B, C, D, E> {
    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;

    /**
     * 创建五元组的静态工厂方法
     * @param a 第一个值
     * @param b 第二个值
     * @param c 第三个值
     * @param d 第四个值
     * @param e 第五个值
     * @param <A> 第一个值的类型
     * @param <B> 第二个值的类型
     * @param <C> 第三个值的类型
     * @param <D> 第四个值的类型
     * @param <E> 第五个值的类型
     * @return 新的五元组实例
     */
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
        return new Tuple5<>(a, b, c, d, e);
    }
}
