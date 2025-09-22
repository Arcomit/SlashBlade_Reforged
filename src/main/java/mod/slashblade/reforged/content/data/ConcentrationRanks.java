package mod.slashblade.reforged.content.data;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;

public enum ConcentrationRanks {
    NONE(0, Range.lessThan(1.0f)),
    D(1, Range.closedOpen(1.0f, 2.0f)),
    C(2, Range.closedOpen(2.0f, 3.0f)),
    B(3, Range.closedOpen(3.0f, 4.0f)),
    A(4, Range.closedOpen(4.0f, 5.0f)),
    S(5, Range.closedOpen(5.0f, 5.25f)),
    SS(6, Range.closedOpen(5.25f, 5.5f)),
    SSS(7, Range.atLeast(5.5f));

    public static final float MAX_LEVEL = 6.0f;

    public final Range<Float> pointRange;
    public final int level;

    ConcentrationRanks(int level, Range<Float> pointRange) {
        this.pointRange = pointRange;
        this.level = level;
    }

    public static ConcentrationRanks getRankFromLevel(float point) {
        return concentrationRanksMap.get(point);
    }

    private static final RangeMap<Float, ConcentrationRanks> concentrationRanksMap = ImmutableRangeMap.<Float, ConcentrationRanks>builder()
            .put(ConcentrationRanks.NONE.pointRange, ConcentrationRanks.NONE)
            .put(ConcentrationRanks.D.pointRange, ConcentrationRanks.D)
            .put(ConcentrationRanks.C.pointRange, ConcentrationRanks.C)
            .put(ConcentrationRanks.B.pointRange, ConcentrationRanks.B)
            .put(ConcentrationRanks.A.pointRange, ConcentrationRanks.A)
            .put(ConcentrationRanks.S.pointRange, ConcentrationRanks.S)
            .put(ConcentrationRanks.SS.pointRange, ConcentrationRanks.SS)
            .put(ConcentrationRanks.SSS.pointRange, ConcentrationRanks.SSS)
            .build();
}