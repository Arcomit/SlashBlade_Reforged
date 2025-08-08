package mod.slashblade.reforged.core.obj;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-05 16:49
 * @Description: 模型解析错误异常
 */
@OnlyIn(Dist.CLIENT)
public class ModelParseException extends Exception {

    private final int lineNumber;

    public ModelParseException(String message, int lineNumber) {
        super(message + " [行: " + lineNumber + "]");
        this.lineNumber = lineNumber;
    }

    public ModelParseException(String message, int lineNumber, Throwable cause) {
        super(message + " [行: " + lineNumber + "]", cause);
        this.lineNumber = lineNumber;
    }

}