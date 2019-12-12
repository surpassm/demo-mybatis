package com.liaoin.demo.annotation;


/**
 * @author mc
 * Create date 2019/12/12 12:37
 * Version 1.0
 * Description
 */
public interface  Convert<A, B>{
    /**
     * A转B
     * @param a a
     * @return B
     */
    B doForward(A a);
    /**
     * B转A
     * @param b b
     * @return A
     */
    A doBackward(B b);
}
