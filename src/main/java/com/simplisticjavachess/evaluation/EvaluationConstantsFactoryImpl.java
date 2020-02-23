package com.simplisticjavachess.evaluation;

public class EvaluationConstantsFactoryImpl implements EvaluationConstantsFactory
{
    private static final EvaluationConstantsFactory instance = new EvaluationConstantsFactoryImpl();

    private static final IntegerEvaluation NONE = new IntegerEvaluation();
    private static final IntegerEvaluation EQUAL_GAME = new IntegerEvaluation(0);
    private static final IntegerEvaluation WHITE_IS_MATE =  new IntegerEvaluation(IntegerEvaluator.WHITE_IS_MATED);
    private static final IntegerEvaluation BLACK_IS_MATE =  new IntegerEvaluation(IntegerEvaluator.BLACK_IS_MATED);

    public static EvaluationConstantsFactory instance()
    {
        return instance;
    }

    @Override
    public Evaluation getNone()
    {
        return NONE;
    }

    @Override
    public Evaluation getEqual()
    {
        return EQUAL_GAME;
    }

    @Override
    public Evaluation getWhiteIsMate()
    {
        return WHITE_IS_MATE;
    }

    @Override
    public Evaluation getBlackIsMate()
    {
        return BLACK_IS_MATE;
    }
}
